package eu.qped.java.checkers.solutionapproach.feedback;


import eu.qped.framework.feedback.Feedback;
import eu.qped.framework.feedback.FeedbackManager;
import eu.qped.framework.feedback.RelatedLocation;
import eu.qped.framework.feedback.Type;
import eu.qped.framework.feedback.defaultfeedback.DefaultFeedback;
import eu.qped.framework.feedback.defaultfeedback.DefaultFeedbacksStore;
import eu.qped.framework.feedback.fromatter.KeyWordReplacer;
import eu.qped.framework.feedback.fromatter.MarkdownFeedbackFormatter;
import eu.qped.framework.feedback.template.TemplateBuilder;
import eu.qped.java.checkers.solutionapproach.SolutionApproachChecker;
import eu.qped.java.checkers.solutionapproach.configs.SolutionApproachGeneralSettings;
import eu.qped.java.checkers.solutionapproach.configs.SolutionApproachReportItem;
import eu.qped.java.utils.FileExtensions;
import eu.qped.java.utils.SupportedLanguages;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static eu.qped.framework.feedback.defaultfeedback.DefaultFeedbackDirectoryProvider.provideDefaultFeedbackDirectory;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolutionApproachFeedbackGenerator {

    private DefaultSolutionApproachFeedbackParser defaultSolutionApproachFeedbackParser;
    private DefaultSolutionApproachFeedbackMapper defaultSolutionApproachFeedbackMapper;
    private MarkdownFeedbackFormatter markdownFeedbackFormatter;
    private TemplateBuilder templateBuilder;

    private DefaultFeedbacksStore defaultFeedbacksStore;
    private FeedbackManager feedbackManager;


    public List<String> generateFeedbacks(List<SolutionApproachReportItem> reportEntries, SolutionApproachGeneralSettings checkerSetting) {
        List<Feedback> nakedFeedbacks = generateNakedFeedbacks(reportEntries, checkerSetting);
//        List<DefaultSolutionApproachFeedback> filteredFeedbacks = getDefaultSyntaxFeedbacks(reportEntries, checkerSetting);
        // naked feedbacks
//        List<Feedback> feedbacks = mapToFeedbacks(filteredFeedbacks);
        // adapted by check level naked feedbacks
        var adaptedFeedbacks = adaptFeedbackByCheckerSetting(nakedFeedbacks, checkerSetting);
        if (feedbackManager == null) feedbackManager = FeedbackManager.builder().build();
        feedbackManager.setFeedbacks(adaptedFeedbacks);
        return feedbackManager.buildFeedbackInTemplate(checkerSetting.getLanguage());
        // formatted feedbacks
//        var formattedFeedbacks = formatFeedbacks(feedbacks);
//        // build feedback in template and return result
//        return buildFeedbackInTemplate(formattedFeedbacks, checkerSetting);
    }

    private List<Feedback> adaptFeedbackByCheckerSetting(List<Feedback> feedbacks, SolutionApproachGeneralSettings checkerSetting) {
        return feedbacks;
    }

    private List<Feedback> generateNakedFeedbacks(List<SolutionApproachReportItem> reportItems, SolutionApproachGeneralSettings checkerSetting) {
        List<Feedback> result = new ArrayList<>();
        if (defaultFeedbacksStore == null) {
            defaultFeedbacksStore = new DefaultFeedbacksStore(
                    provideDefaultFeedbackDirectory(SolutionApproachChecker.class)
                    , checkerSetting.getLanguage() + FileExtensions.JSON
            );
        }
        var keyWordReplacer = KeyWordReplacer.builder().build();
        for (SolutionApproachReportItem reportItem : reportItems) {
            var defaultFeedback = defaultFeedbacksStore.getRelatedDefaultFeedbackByTechnicalCause(reportItem.getErrorCode());
            if (defaultFeedback != null) {
                Feedback feedback = Feedback.builder().build();
                feedback.setType(Type.CORRECTION);
                feedback.setCheckerName(SolutionApproachChecker.class.getSimpleName());
                feedback.setTechnicalCause(reportItem.getErrorCode());
                feedback.setReadableCause(
                        keyWordReplacer.replace(
                                defaultFeedback.getReadableCause(),
                                reportItem
                        )
                );
                feedback.setHints((defaultFeedback.getHints() != null) ?
                        defaultFeedback.getHints().stream().peek(hint ->
                                hint.setContent(keyWordReplacer.replace(
                                        hint.getContent(),
                                        reportItem
                                ))
                        ).collect(Collectors.toList())
                        : Collections.emptyList()
                );
                feedback.setRelatedLocation(RelatedLocation.builder()
                        .fileName(
                                reportItem.getRelatedSemanticSettingItem().getFilePath().substring(
                                        reportItem.getRelatedSemanticSettingItem().getFilePath().lastIndexOf("/") + 1
                                )
                        )
                        .methodName(reportItem.getRelatedSemanticSettingItem().getMethodName())
                        .build()
                );
                result.add(feedback);
            }

        }
        return result;
    }

    private List<String> buildFeedbackInTemplate(List<Feedback> feedbacks, SolutionApproachGeneralSettings checkerSettings) {
        if (templateBuilder == null) templateBuilder = TemplateBuilder.builder().build();
        if (checkerSettings.getLanguage() == null) checkerSettings.setLanguage(SupportedLanguages.ENGLISH);
        return templateBuilder.buildFeedbacksInTemplate(feedbacks, checkerSettings.getLanguage());
    }

    private List<DefaultSolutionApproachFeedback> getDefaultSyntaxFeedbacks(List<SolutionApproachReportItem> reportItems, SolutionApproachGeneralSettings generalSettings) {
        if (defaultSolutionApproachFeedbackParser == null)
            defaultSolutionApproachFeedbackParser = DefaultSolutionApproachFeedbackParser.builder().build();
        List<DefaultSolutionApproachFeedback> allDefaultSolutionApproachFeedbacks = defaultSolutionApproachFeedbackParser.parse(generalSettings.getLanguage());
        var allDefaultSyntaxFeedbacksByTechnicalCause =
                allDefaultSolutionApproachFeedbacks.stream()
                        .collect(Collectors.groupingBy(
                                DefaultSolutionApproachFeedback -> DefaultSolutionApproachFeedback.getDefaultFeedback().getTechnicalCause()
                        ));
        return filterDefaultFeedbacks(reportItems, allDefaultSyntaxFeedbacksByTechnicalCause);
    }

    private List<Feedback> mapToFeedbacks(List<DefaultSolutionApproachFeedback> filteredFeedbacks) {
        if (defaultSolutionApproachFeedbackMapper == null)
            defaultSolutionApproachFeedbackMapper = DefaultSolutionApproachFeedbackMapper.builder().build();
        return defaultSolutionApproachFeedbackMapper.map(filteredFeedbacks);
    }


    private List<Feedback> formatFeedbacks(@NotNull List<Feedback> feedbacks) {

        if (markdownFeedbackFormatter == null) markdownFeedbackFormatter = new MarkdownFeedbackFormatter();
        return markdownFeedbackFormatter.format(feedbacks);
    }

    private List<DefaultSolutionApproachFeedback> filterDefaultFeedbacks(List<SolutionApproachReportItem> reportItems, Map<String, List<DefaultSolutionApproachFeedback>> allDefaultFeedbacksByTechnicalCause) {
        List<DefaultSolutionApproachFeedback> result = new ArrayList<>();
        var keyWordReplacer = KeyWordReplacer.builder().build();
        for (SolutionApproachReportItem reportItem : reportItems) {
            if (allDefaultFeedbacksByTechnicalCause.containsKey(reportItem.getErrorCode())) {
                allDefaultFeedbacksByTechnicalCause.get(reportItem.getErrorCode()).forEach(defaultSolutionApproachFeedback -> {
                    result.add(DefaultSolutionApproachFeedback.builder()
                            .defaultFeedback(DefaultFeedback.builder()
                                    .technicalCause(reportItem.getErrorCode())
                                    .readableCause(
                                            keyWordReplacer.replace(
                                                    defaultSolutionApproachFeedback.getDefaultFeedback().getReadableCause(),
                                                    reportItem
                                            )
                                    )
                                    .hints((defaultSolutionApproachFeedback.getDefaultFeedback().getHints() != null) ?
                                            defaultSolutionApproachFeedback.getDefaultFeedback().getHints().stream().peek(hint ->
                                                    hint.setContent(keyWordReplacer.replace(
                                                            hint.getContent(),
                                                            reportItem
                                                    ))
                                            ).collect(Collectors.toList())
                                            : Collections.emptyList()
                                    )
                                    .build())
                            .relatedLocation(RelatedLocation.builder()
                                    .fileName(
                                            reportItem.getRelatedSemanticSettingItem().getFilePath().substring(
                                                    reportItem.getRelatedSemanticSettingItem().getFilePath().lastIndexOf("/") + 1
                                            )
                                    )
                                    .methodName(reportItem.getRelatedSemanticSettingItem().getMethodName())
                                    .build()
                            )
                            .build()
                    );
                });
            }
        }
        return result;
    }

    public static void main(String[] args) {

        String s = "fjsdaflökjsdaf /aksdfdsaf";
        System.out.println(StringUtils.substringAfterLast(s, "/"));
        System.out.println(s.substring(s.lastIndexOf("/") + 1));
        System.out.println(StringUtils.removeStart(s, "/"));
    }

}
