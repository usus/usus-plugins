package org.projectusus.core.internal.proportions.rawdata;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.projectusus.core.basis.IHotspot;
import org.projectusus.core.internal.proportions.model.Hotspot;

public abstract class DefaultStatistic extends DefaultMetricsResultVisitor {

    private int violations;
    private int violationSum;
    private int violationLimit;
    private IFile currentFile;
    private List<IHotspot> hotspots;

    public DefaultStatistic( int violationLimit ) {
        initAndRun( violationLimit );
    }

    public DefaultStatistic( JavaModelPath path, int violationLimit ) {
        super( path );
        initAndRun( violationLimit );
    }

    private void initAndRun( int limit ) {
        violations = 0;
        violationSum = 0;
        this.violationLimit = limit;
        hotspots = new ArrayList<IHotspot>();
        visit();
    }

    protected void addViolation( int count, IHotspot hotspot ) {
        violationSum += count;
        if( count > violationLimit ) {
            violations++;
            ((Hotspot)hotspot).setFile( currentFile );
            hotspots.add( hotspot );
        }
    }

    @Override
    public void inspect( FileRawData fileRawData ) {
        currentFile = fileRawData.getFileOfRawData();
    }

    public int getViolations() {
        return violations;
    }

    public int getViolationSum() {
        return violationSum;
    }

    public List<IHotspot> getHotspots() {
        return hotspots;
    }
}