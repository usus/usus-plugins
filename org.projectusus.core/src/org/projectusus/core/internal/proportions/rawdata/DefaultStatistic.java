package org.projectusus.core.internal.proportions.rawdata;

public class DefaultStatistic extends DefaultMetricsResultVisitor {

    private int violations;
    private int violationSum;
    private int violationLimit;

    public DefaultStatistic( int violationLimit ) {
        initAndRun( violationLimit );
    }

    public DefaultStatistic( JavaModelPath path, int violationLimit ) {
        super( path );
        initAndRun( violationLimit );
    }

    private void initAndRun( int limit ) {
        violations = 0;
        violationSum = 0;
        this.violationLimit = limit;
        visit();
    }

    protected void addViolation( int count ) {
        violationSum += count;
        if( count > violationLimit ) {
            violations++;
        }
    }

    public int getViolations() {
        return violations;
    }

    public int getViolationSum() {
        return violationSum;
    }
}
