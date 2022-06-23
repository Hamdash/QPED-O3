package eu.qped.java.checkers.semantics;

import eu.qped.java.checkers.mass.QFSemSettings;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SemanticConfigurator {

    private String methodName;
    private Boolean recursionAllowed;
    private Integer whileLoop;
    private Integer forLoop;
    private Integer forEachLoop;
    private Integer ifElseStmt;
    private Integer doWhileLoop;
    private String returnType;

    private QFSemSettings qfSemSettings;


    private SemanticConfigurator(QFSemSettings qfSemSettings) {
        this.qfSemSettings = qfSemSettings;
        applySettings();
    }

    public static SemanticConfigurator createSemanticConfigurator(QFSemSettings qfSemSettings) {
        return new SemanticConfigurator(qfSemSettings);
    }

    private void applySettings() {

        setMethodName(qfSemSettings.getMethodName() != null ? qfSemSettings.getMethodName() : "undefined");
        setRecursionAllowed(qfSemSettings.getRecursionAllowed() != null && Boolean.parseBoolean(qfSemSettings.getRecursionAllowed()));
        setWhileLoop(qfSemSettings.getWhileLoop() != null ? Integer.parseInt(qfSemSettings.getWhileLoop()) : -1);
        setForLoop(qfSemSettings.getForLoop() != null ? Integer.parseInt(qfSemSettings.getForLoop()) : -1);
        setForEachLoop(qfSemSettings.getForEachLoop() != null ? Integer.parseInt(qfSemSettings.getForEachLoop()) : -1);
        setIfElseStmt(qfSemSettings.getIfElseStmt() != null ? Integer.parseInt(qfSemSettings.getIfElseStmt()) : -1);
        setDoWhileLoop(qfSemSettings.getDoWhileLoop() != null ? Integer.parseInt(qfSemSettings.getDoWhileLoop()) : -1);
        setReturnType(qfSemSettings.getReturnType() != null ? qfSemSettings.getReturnType() : "undefined");
    }
}
