package org.projectusus.core.internal.proportions.rawdata;

import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.internal.proportions.model.Hotspot;

public class ACDStatistic extends DefaultStatistic {

    public ACDStatistic( JavaModelPath path ) {
        super( path, calculateCcdLimit() );
    }

    public ACDStatistic() {
        super( calculateCcdLimit() );
    }

    @Override
    public void inspect( ClassRawData classRawData ) {
        addViolation( classRawData.getCCDResult(), new Hotspot( classRawData.getClassName(), classRawData.getCCDResult(), classRawData.getStartPosition(), classRawData
                .getLineNumber() ) );
    }

    public int getCCDSum() {
        return getViolationSum();
    }

    public double getRelativeACD() {
        int numberOfClasses = ClassDescriptor.getAll().size();
        if( numberOfClasses == 0 ) {
            return 0.0;
        }
        return getCCDSum() / (double)(numberOfClasses * numberOfClasses);
    }

    private static int calculateCcdLimit() {
        int classCount = new ClassCountVisitor().getClassCount();
        double log_5_classCount = Math.log( classCount ) / Math.log( 5 );
        double factor = 1.5 / Math.pow( 2, log_5_classCount );
        double limit = factor * classCount;
        return (int)limit;
    }

}
