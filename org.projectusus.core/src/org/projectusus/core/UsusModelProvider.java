package org.projectusus.core;

import org.projectusus.core.internal.proportions.IMetricsWriter;
import org.projectusus.core.internal.proportions.IUsusModelForAdapter;
import org.projectusus.core.internal.proportions.rawdata.UsusModel;

public class UsusModelProvider {

    public static IUsusModel ususModel() {
        return UsusModel.ususModel();
    }

    public static IMetricsAccessor getMetricsAccessor() {
        return UsusModel.ususModel().getMetricsAccessor();
    }

    public static IMetricsWriter getMetricsWriter() {
        return UsusModel.ususModel().getMetricsWriter();
    }

    public static IUsusModelForAdapter ususModelForAdapter() {
        return UsusModel.ususModel();
    }

}
