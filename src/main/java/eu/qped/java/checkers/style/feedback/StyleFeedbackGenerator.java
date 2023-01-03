package eu.qped.java.checkers.style.feedback;


import eu.qped.framework.feedback.Feedback;
import eu.qped.framework.feedback.RelatedLocation;
import eu.qped.framework.feedback.defaultfeedback.DefaultFeedback;
import eu.qped.framework.feedback.defaultfeedback.DefaultFeedbackDirectoryProvider;
import eu.qped.framework.feedback.defaultfeedback.DefaultFeedbacksStore;
import eu.qped.framework.feedback.fromatter.KeyWordReplacer;
import eu.qped.framework.feedback.fromatter.MarkdownFeedbackFormatter;
import eu.qped.framework.feedback.template.TemplateBuilder;
import eu.qped.java.checkers.style.analyse.StyleAnalyser;
import eu.qped.java.checkers.style.analyse.reportModel.StyleAnalysisReport;
import eu.qped.java.checkers.style.config.StyleSettings;
import eu.qped.java.utils.FileExtensions;
import eu.qped.java.utils.SupportedLanguages;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
@Data
public class StyleFeedbackGenerator {

    private DefaultStyleFeedbackParser defaultStyleFeedbackParser;
    private DefaultStyleFeedbackMapper defaultStyleFeedbackMapper;
    private MarkdownFeedbackFormatter markdownFeedbackFormatter;
    private TemplateBuilder templateBuilder;


    private StyleAnalysisReport report;

    private DefaultFeedbacksStore defaultFeedbacksStore;

    private String language;


    /**
     * @return map of filenames as keys and a collection of feedbacks for key file.
     */
    public List<String> generateFeedbacks(StyleAnalysisReport report, StyleSettings styleSettings) {
        List<DefaultStyleFeedback> filteredFeedbacks = getDefaultSyntaxFeedbacks(report, styleSettings);
        // naked feedbacks
        List<Feedback> feedbacks = mapToFeedbacks(filteredFeedbacks);
        // adapted by check level naked feedbacks
        feedbacks = adaptFeedbackByCheckerSetting(styleSettings, feedbacks);
        // formatted feedbacks
        var formattedFeedbacks = formatFeedbacks(feedbacks);
        // build feedback in template and return result
        return buildFeedbackInTemplate(formattedFeedbacks, styleSettings);
    }

    private List<DefaultStyleFeedback> getDefaultSyntaxFeedbacks(StyleAnalysisReport report, StyleSettings styleSettings) {
        if (defaultStyleFeedbackParser == null)
            defaultStyleFeedbackParser = DefaultStyleFeedbackParser.builder().build();
        List<DefaultStyleFeedback> allDefaultSolutionApproachFeedbacks = defaultStyleFeedbackParser.parse(styleSettings.getLanguage());
        var allDefaultStyleFeedbacksByTechnicalCause =
                allDefaultSolutionApproachFeedbacks.stream()
                        .collect(Collectors.groupingBy(
                                DefaultSolutionApproachFeedback -> DefaultSolutionApproachFeedback.getDefaultFeedback().getTechnicalCause()
                        ));
        return filterDefaultFeedbacks(report, allDefaultStyleFeedbacksByTechnicalCause);
    }

    private List<Feedback> mapToFeedbacks(List<DefaultStyleFeedback> filteredFeedbacks) {
        if (defaultStyleFeedbackMapper == null)
            defaultStyleFeedbackMapper = DefaultStyleFeedbackMapper.builder().build();
        return defaultStyleFeedbackMapper.map(filteredFeedbacks);
    }

    private List<Feedback> adaptFeedbackByCheckerSetting(StyleSettings styleSettings, List<Feedback> feedbacks) {
        return feedbacks;
    }

    private Map<String, List<DefaultFeedback>> getDefaultFeedbacksByTechnicalCause() {
        var dirPath = DefaultFeedbackDirectoryProvider.provideDefaultFeedbackDirectory(StyleAnalyser.class);
        if (defaultFeedbacksStore == null) {
            defaultFeedbacksStore = new DefaultFeedbacksStore(dirPath, language + FileExtensions.JSON);
        }
        if (language == null || language.equals("")) setLanguage(SupportedLanguages.ENGLISH);
        var allDefaultFeedbacks = defaultFeedbacksStore.parse(dirPath, language + FileExtensions.JSON);
        return allDefaultFeedbacks.stream().collect(Collectors.groupingBy(DefaultFeedback::getTechnicalCause));
    }

    private List<Feedback> formatFeedbacks(@NotNull List<Feedback> feedbacks) {
        if (markdownFeedbackFormatter == null) markdownFeedbackFormatter = new MarkdownFeedbackFormatter();
        return markdownFeedbackFormatter.format(feedbacks);
    }

    private List<String> buildFeedbackInTemplate(List<Feedback> feedbacks, StyleSettings styleSettings) {
        if (templateBuilder == null) templateBuilder = TemplateBuilder.builder().build();
        if (styleSettings.getLanguage() == null) styleSettings.setLanguage(styleSettings.getLanguage());
        return templateBuilder.buildFeedbacksInTemplate(feedbacks, styleSettings.getLanguage());
    }

    protected List<DefaultStyleFeedback> filterDefaultFeedbacks(StyleAnalysisReport report, Map<String, List<DefaultStyleFeedback>> allDefaultStyleFeedbacksByTechnicalCause) {
        List<DefaultStyleFeedback> result = new ArrayList<>();
        var keyWordReplacer = KeyWordReplacer.builder().build();
        report.getFileEntries()
                .forEach(
                        reportFileEntry -> reportFileEntry
                                .getViolations()
                                .forEach(
                                        violation -> {
                                            File temp = new File(reportFileEntry.getFileName());
                                            if (allDefaultStyleFeedbacksByTechnicalCause.containsKey(violation.getRule())) {
                                                allDefaultStyleFeedbacksByTechnicalCause.get(violation.getRule()).forEach(defaultStyleFeedback -> {
                                                    result.add(
                                                            DefaultStyleFeedback.builder()
                                                                    .relatedLocation(
                                                                            RelatedLocation.builder()
                                                                                    .fileName(temp.getName())
                                                                                    .startLine((temp.getName().contains("TestClass"))
                                                                                            ? violation.getBeginLine() - 3
                                                                                            : violation.getBeginLine()
                                                                                    )
                                                                                    .endLine((temp.getName().contains("TestClass"))
                                                                                            ? violation.getEndLine() - 3
                                                                                            : violation.getEndLine()
                                                                                    )
                                                                                    .build()
                                                                    )
                                                                    .defaultFeedback(
                                                                            DefaultFeedback.builder()
                                                                                    .technicalCause(violation.getRule())
                                                                                    .readableCause(
                                                                                            defaultStyleFeedback.getDefaultFeedback().getReadableCause()
                                                                                    )
                                                                                    .hints(
                                                                                            defaultStyleFeedback.getDefaultFeedback().getHints()
                                                                                    )
                                                                                    .build()
                                                                    )
                                                                    .build()
                                                    );
                                                });

                                            }

                                        }
                                )
                );
        return result;
    }


}
