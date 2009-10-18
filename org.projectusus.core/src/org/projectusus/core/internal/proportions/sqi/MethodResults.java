package org.projectusus.core.internal.proportions.sqi;

public class MethodResults {

    private final String methodName;
    private int ccResult;
    private int mlResult;

    public MethodResults( String methodName ) {
        this.methodName = methodName;
    }

    public void setCCResult( int value ) {
        ccResult = value;
    }

    public int getCCResult() {
        return ccResult;

    }

    public void setMLResult( int value ) {
        mlResult = value;
    }

    public int getMLResult() {
        return mlResult;
    }

    public String getMethodName() {
        return methodName;
    }

    public boolean violates( IsisMetrics metric ) {
        return metric.isViolatedBy( this );
    }

}
