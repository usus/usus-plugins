package org.projectusus.core.internal.proportions.kinds;

import static org.projectusus.core.internal.util.CoreTexts.isisMetrics_acd;

import org.projectusus.core.internal.proportions.sqi.ClassRawData;
import org.projectusus.core.internal.proportions.sqi.IsisMetrics;
import org.projectusus.core.internal.proportions.sqi.WorkspaceRawData;

public class CodeProportionACD extends CodeProportionForClass {

    public CodeProportionACD() {
        super( isisMetrics_acd );
    }

    @Override
    public boolean isViolatedBy( ClassRawData classResult ) {
        int classCount = WorkspaceRawData.getInstance().getViolationBasis( IsisMetrics.KG );
        double log_5_classCount = Math.log( classCount ) / Math.log( 5 );
        double factor = 1.5 / Math.pow( 2, log_5_classCount );
        double limit = factor * classCount;
        return classResult.getCCDResult() > limit;
    }
}
