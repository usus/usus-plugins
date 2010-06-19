package org.projectusus.core.internal.proportions.rawdata;


public class CyclomaticComplexityStatistic extends DefaultStatistic {

    private static int CC_LIMIT = 5;

    public CyclomaticComplexityStatistic() {
        super( CC_LIMIT );
    }

    public CyclomaticComplexityStatistic( JavaModelPath path ) {
        super( path, CC_LIMIT );
    }

    @Override
    public void inspect( SourceCodeLocation location, MethodRawData methodRawData ) {
        addViolation( location, methodRawData.getCCValue() );
    }

}
