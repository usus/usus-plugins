package org.projectusus.core.internal.proportions.rawdata;


public class ClassSizeStatistic extends DefaultStatistic {

    private static int KG_LIMIT = 20;

    public ClassSizeStatistic() {
        super( KG_LIMIT );
    }

    @Override
    public void inspect( SourceCodeLocation location, ClassRawData classRawData ) {
        addViolation( location, classRawData.getRawDataElementCount() );
    }
}
