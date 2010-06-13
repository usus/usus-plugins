package org.projectusus.core.internal.proportions.rawdata;


public class CCDSumVisitor extends DefaultMetricsResultVisitor {

    private int ccdCount = 0;

    public CCDSumVisitor( JavaModelPath path ) {
        super( path );
    }

    @Override
    public void inspect( ClassRawData classRawData ) {
        ccdCount = ccdCount + classRawData.getCCDResult();
    }

    public int getCCDSum() {
        visit();
        return ccdCount;
    }
}
