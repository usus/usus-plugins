package org.projectusus.core.internal.proportions.rawdata;

import org.projectusus.core.internal.proportions.model.Hotspot;

public class MethodLengthStatistic extends DefaultStatistic {

    private static int ML_LIMIT = 15;

    public MethodLengthStatistic() {
        super( ML_LIMIT );
    }

    public MethodLengthStatistic( JavaModelPath path ) {
        super( path, ML_LIMIT );
    }

    @Override
    public void inspect( MethodRawData methodRawData ) {
        addViolation( methodRawData.getMLValue(), new Hotspot( methodRawData.getClassName() + "." + methodRawData.getMethodName(), methodRawData.getMLValue(), methodRawData
                .getSourcePosition(), methodRawData.getLineNumber() ) );
    }

}
