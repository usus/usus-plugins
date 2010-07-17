package org.projectusus.core.statistics;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.CodeStatistic;
import org.projectusus.core.basis.Hotspot;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;

public abstract class DefaultCockpitExtension extends DefaultMetricsResultVisitor implements ICockpitExtension {

    private int basis;
    private int violations;
    private int violationSum;
    private int violationLimit;
    private IFile currentFile;
    private List<Hotspot> hotspots;
    private String label;
    private String unit;

    public DefaultCockpitExtension( String label, String unit, int violationLimit ) {
        super();
        this.label = label;
        this.unit = unit;
        this.violationLimit = violationLimit;
        reset();
    }

    private void reset() {
        basis = 0;
        violations = 0;
        violationSum = 0;
        hotspots = new ArrayList<Hotspot>();
    }

    protected void addViolation( SourceCodeLocation location, int count ) {
        basis++;
        violationSum += count;
        if( count > violationLimit ) {
            violations++;
            hotspots.add( new Hotspot( location, count, currentFile ) );
        }
    }

    @Override
    public void inspectFile( IFile file, @SuppressWarnings( "unused" ) MetricsResults result ) {
        currentFile = file;
    }

    public int getViolations() {
        return violations;
    }

    public int getMetricsSum() {
        return violationSum;
    }

    public List<Hotspot> getHotspots() {
        return hotspots;
    }

    public CodeProportion getCodeProportion() {
        return new CodeProportion( getLabel(), getViolations(), getBasisStatistic(), getHotspots() );
    }

    public String getLabel() {
        return label;
    }

    public CodeStatistic getBasisStatistic() {
        return new CodeStatistic( unit, basis );
    }
}
