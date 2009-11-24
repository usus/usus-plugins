package org.projectusus.core.internal.proportions.kinds;

import static org.projectusus.core.internal.util.CoreTexts.isisMetrics_kg;

import org.projectusus.core.internal.proportions.sqi.ClassRawData;
import org.projectusus.core.internal.proportions.sqi.MetricsLimits;

public class CodeProportionKG extends CodeProportionForClass {

    public CodeProportionKG() {
        super( isisMetrics_kg );
    }

    @Override
    public boolean isViolatedBy( ClassRawData classResult ) {
        return classResult.getNumberOfMethods() > MetricsLimits.KG_LIMIT;
    }

    @Override
    public double getCalibration() {
        return 25.0;
    }
}
