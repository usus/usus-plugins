package org.projectusus.core.internal.proportions.rawdata;

import org.projectusus.core.internal.proportions.model.Hotspot;

public class ClassSizeStatistic extends DefaultStatistic {

    private static int KG_LIMIT = 20;

    public ClassSizeStatistic() {
        super( KG_LIMIT );
    }

    @Override
    public void inspect( ClassRawData classRawData ) {
        addViolation( classRawData.getNumberOfMethods(), new Hotspot( classRawData.getClassName(), classRawData.getNumberOfMethods(), classRawData.getStartPosition(), classRawData
                .getLineNumber() ) );
    }
}