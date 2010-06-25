package org.projectusus.core.statistics;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.projectusus.core.basis.Hotspot;
import org.projectusus.core.basis.IHotspot;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;

public abstract class DefaultStatistic extends DefaultMetricsResultVisitor {

    private int violations;
    private int violationSum;
    private int violationLimit;
    private IFile currentFile;
    private List<IHotspot> hotspots;

    public DefaultStatistic( int violationLimit ) {
        init( violationLimit );
    }

    public DefaultStatistic( JavaModelPath path, int violationLimit ) {
        super( path );
        init( violationLimit );
    }

    private void init( int limit ) {
        violations = 0;
        violationSum = 0;
        this.violationLimit = limit;
        hotspots = new ArrayList<IHotspot>();
    }

    protected void addViolation( SourceCodeLocation location, int count ) {
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

    public List<IHotspot> getHotspots() {
        return hotspots;
    }

    @Override
    public DefaultStatistic visit() {
        super.visit();
        return this;
    }
}
