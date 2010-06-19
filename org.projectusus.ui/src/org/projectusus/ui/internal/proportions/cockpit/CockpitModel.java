package org.projectusus.ui.internal.proportions.cockpit;

import org.projectusus.core.IUsusModel;

public class CockpitModel {

    private final IUsusModel ususModel;

    public CockpitModel( IUsusModel ususModel ) {
        super();
        this.ususModel = ususModel;
    }

    public CockpitCategory[] getCategories() {
        return new CockpitCategory[] { new MetricStatistics( ususModel.getCodeProportions() ) };
    }

}
