package org.projectusus.ui.internal.proportions.cockpit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.projectusus.core.IUsusModel;
import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.Hotspot;
import org.projectusus.core.internal.proportions.rawdata.UsusModel;

public class AnalysisDisplayModel {

    private static AnalysisDisplayModel instance;

    private final IUsusModel ususModel;

    private List<AnalysisDisplayEntry> snapshot = new ArrayList<AnalysisDisplayEntry>();

    public static AnalysisDisplayModel getInstance() {
        if( instance == null ) {
            instance = new AnalysisDisplayModel( UsusModel.ususModel() );
        }
        return instance;
    }

    private AnalysisDisplayModel( IUsusModel ususModel ) {
        super();
        this.ususModel = ususModel;
    }

    public AnalysisDisplayCategory[] getCategories() {
        return new AnalysisDisplayCategory[] { new MetricStatisticsCategory( createDisplayEntries( ususModel.getCodeProportions() ) ) };
    }

    private List<AnalysisDisplayEntry> createDisplayEntries( List<CodeProportion> codeProportions ) {
        List<AnalysisDisplayEntry> result = new ArrayList<AnalysisDisplayEntry>();
        for( CodeProportion codeProportion : codeProportions ) {
            String label = codeProportion.getMetricLabel();
            double level = codeProportion.getLevel();
            int violations = codeProportion.getViolations();
            String basis = codeProportion.getBasis().toString();
            List<Hotspot> hotspots = codeProportion.getHotspots();
            result.add( new AnalysisDisplayEntry( label, level, violations, basis, codeProportion.hasHotspots(), hotspots, trendValueFor( label ) ) );
        }
        return result;
    }

    public List<AnalysisDisplayEntry> getEntriesOfAllCategories() {
        List<AnalysisDisplayEntry> result = new ArrayList<AnalysisDisplayEntry>();
        for( AnalysisDisplayCategory category : getCategories() ) {
            result.addAll( Arrays.asList( category.getChildren() ) );
        }
        return result;
    }

    public void createSnapshot() {
        snapshot = getEntriesOfAllCategories();
    }

    public List<AnalysisDisplayEntry> getSnapshot() {
        return snapshot;
    }

    private Double trendValueFor( String label ) {
        for( AnalysisDisplayEntry codeProportion : snapshot ) {
            if( codeProportion.isSameKindAs( label ) ) {
                return Double.valueOf( codeProportion.getLevel() );
            }
        }
        return null;
    }

}
