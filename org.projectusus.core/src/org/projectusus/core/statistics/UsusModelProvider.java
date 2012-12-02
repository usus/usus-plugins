package org.projectusus.core.statistics;

import net.sourceforge.c4j.ContractReference;

import org.projectusus.core.IMetricsResultVisitor;
import org.projectusus.core.IMetricsWriter;
import org.projectusus.core.IUsusModel;
import org.projectusus.core.IUsusModelForAdapter;

@ContractReference( contractClassName = "UsusModelProviderContract" )
public class UsusModelProvider {

    public static IUsusModel ususModel() {
        return UsusModel.ususModel();
    }

    public static IMetricsWriter getMetricsWriter() {
        return UsusModel.ususModel().getMetricsWriter();
    }

    public static IUsusModelForAdapter ususModelForAdapter() {
        return UsusModel.ususModel();
    }

    public static void acceptAndGuide( IMetricsResultVisitor visitor ) {
        UsusModel.ususModel().acceptAndGuide( visitor );
    }

    public static void clear() {
        UsusModel.clear();
    }

}
