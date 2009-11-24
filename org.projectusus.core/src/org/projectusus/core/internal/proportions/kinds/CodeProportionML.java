package org.projectusus.core.internal.proportions.kinds;

import static org.projectusus.core.internal.util.CoreTexts.isisMetrics_ml;

import org.projectusus.core.internal.proportions.sqi.MethodRawData;
import org.projectusus.core.internal.proportions.sqi.MetricsLimits;

public class CodeProportionML extends CodeProportionForMethod {

    public CodeProportionML() {
        super( isisMetrics_ml );
    }

    @Override
    public boolean isViolatedBy( MethodRawData methodResults ) {
        return methodResults.getMLValue() > MetricsLimits.ML_LIMIT;
    }

    @Override
    public double getCalibration() {
        return 25.0;
    }

    @Override
    public int getValueFor( MethodRawData methodResults ) {
        return methodResults.getMLValue();
    }
}
