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

    private int violations;
    private int violationSum;
    private int violationLimit;
    private IFile currentFile;
    private List<Hotspot> hotspots;
    private String label;

    public DefaultCockpitExtension( String label, int violationLimit ) {
        super();
        this.label = label;
        this.violationLimit = violationLimit;
        reset();
    }

    private void reset() {
        violations = 0;
        violationSum = 0;
        hotspots = new ArrayList<Hotspot>();
    }

    protected void addViolation( SourceCodeLocation location, int count ) {
        violationSum += count;
        if( count > violationLimit ) {
            violations++;
            hotspots.add( new Hotspot( location, count, currentFile ) );
        }
    }

    @Override
    public void inspectFile( IFile file, MetricsResults result ) {
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
        return new CodeProportion( getLabel(), getViolations(), getBasis(), getHotspots() );
    }

    public String getLabel() {
        return label;
    }

    public abstract CodeStatistic getBasis();
}
