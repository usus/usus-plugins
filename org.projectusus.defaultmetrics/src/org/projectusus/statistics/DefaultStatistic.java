package org.projectusus.statistics;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.Hotspot;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.statistics.DefaultMetricsResultVisitor;

public abstract class DefaultStatistic extends DefaultMetricsResultVisitor {

    private int violations;
    private int violationSum;
    private int violationLimit;
    private IFile currentFile;
    private List<Hotspot> hotspots;

    public DefaultStatistic( String label, int violationLimit ) {
        super( label );
        init( violationLimit );
    }

    public DefaultStatistic( String label, JavaModelPath path, int violationLimit ) {
        super( label, path );
        init( violationLimit );
    }

    private void init( int limit ) {
        violations = 0;
        violationSum = 0;
        this.violationLimit = limit;
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
        return new CodeProportion( getLabel(), getViolations(), getBasis(), getHotspots(), true );
    }
}
