package org.projectusus.core.internal.proportions.rawdata;

import org.projectusus.core.internal.proportions.model.Hotspot;

public class CyclomaticComplexityStatistic extends DefaultStatistic {

    private static int CC_LIMIT = 5;

    public CyclomaticComplexityStatistic() {
        super( CC_LIMIT );
    }

    public CyclomaticComplexityStatistic( JavaModelPath path ) {
        super( path, CC_LIMIT );
    }

    @Override
    public void inspect( MethodRawData methodRawData ) {
        addViolation( methodRawData.getCCValue(), new Hotspot( methodRawData.getClassName() + "." + methodRawData.getMethodName(), methodRawData.getCCValue(), methodRawData
                .getSourcePosition(), methodRawData.getLineNumber() ) );
    }

}
