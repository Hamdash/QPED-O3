package eu.qped.framework.feedback.severity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Severity {

    private String severity;

    private SeverityType severityType;


}
