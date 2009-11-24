package org.projectusus.core.internal.proportions.kinds;

import static org.projectusus.core.internal.util.CoreTexts.isisMetrics_cc;

import org.projectusus.core.internal.proportions.sqi.MethodRawData;
import org.projectusus.core.internal.proportions.sqi.MetricsLimits;

public class CodeProportionCC extends CodeProportionForMethod {

    public CodeProportionCC() {
        super( isisMetrics_cc );
    }

    @Override
    public boolean isViolatedBy( MethodRawData methodResults ) {
        return methodResults.getCCValue() > MetricsLimits.CC_LIMIT;
    }

    @Override
    public double getCalibration() {
        return 100.0;
    }

    @Override
    public int getValueFor( MethodRawData methodResults ) {
        return methodResults.getCCValue();
    }
}
