package org.projectusus.core.internal.proportions.rawdata;


public class MethodLengthStatistic extends DefaultStatistic {

    private static int ML_LIMIT = 15;

    public MethodLengthStatistic() {
        super( ML_LIMIT );
    }

    public MethodLengthStatistic( JavaModelPath path ) {
        super( path, ML_LIMIT );
    }

    @Override
    public void inspect( SourceCodeLocation location, MethodRawData methodRawData ) {
        addViolation( location, methodRawData.getMLValue() );
    }

}
