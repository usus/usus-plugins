package org.projectusus.core.statistics;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.projectusus.core.CollectibleExtension;
import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.CodeStatistic;
import org.projectusus.core.basis.FileHotspot;
import org.projectusus.core.basis.Histogram;
import org.projectusus.core.basis.Hotspot;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SinglePackageHotspot;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.filerelations.model.Packagename;

/**
 * Implementors of extensions for the <code>org.projectusus.core.statistics</code> extension point must use this implementation as a basis for their own implementations. It
 * provides a default mechanism to create a CodeProportion object. Implementors need to invoke the {@link CockpitExtension#addResult(SourceCodeLocation, int)} method in order for
 * this mechanism to work. Examples can be found in the <code>org.projectusus.defaultmetrics</code> project.
 * <p>
 * The default implementation visits the whole raw data tree. Technically, it would be possible to restrict the analysis to a subtree, but it does not really make sense to do so
 * because the <code>JavaModelPath</code> object pointing to the subtree needs to be statically integrated into the visitor and thus cannot be adapted to the code currently being
 * analyzed.
 * 
 */
public abstract class CockpitExtension extends DefaultMetricsResultVisitor implements CollectibleExtension {

    public final static String EXTENSION_POINT_ID = "org.projectusus.core.statistics"; //$NON-NLS-1$

    private final String unit;
    private final int violationLimit;
    private int basis;
    private int violations;
    private int violationSum;
    private IFile currentFile;
    private List<Hotspot> hotspots;
    private Histogram histogram;

    public CockpitExtension( String unit, int violationLimit ) {
        super();
        this.unit = unit;
        this.violationLimit = violationLimit;
        reset();
    }

    private void reset() {
        basis = 0;
        violations = 0;
        violationSum = 0;
        hotspots = new ArrayList<Hotspot>();
        histogram = new Histogram();
    }

    protected void addResult( SourceCodeLocation location, int count ) {
        incrementState( count, new FileHotspot( location, count, currentFile ) );
    }

    protected void addResult( Packagename pkg, int count ) {
        incrementState( count, new SinglePackageHotspot( pkg, count ) );
    }

    private void incrementState( int count, Hotspot hotspot ) {
        basis++;
        histogram.increment( count );
        violationSum += count;
        if( count > violationLimit ) {
            violations++;
            hotspots.add( hotspot );
        }
    }

    @Override
    public void inspectFile( IFile file, @SuppressWarnings( "unused" ) MetricsResults result ) {
        currentFile = file;
    }

    public int getMetricsSum() {
        return violationSum;
    }

    public int getViolations() {
        return violations;
    }

    public int getBasis() {
        return basis;
    }

    public CodeStatistic getBasisStatistic() {
        return new CodeStatistic( unit, basis );
    }

    public double getAverage() {
        return calculateAverage( getViolations(), getBasis() );
    }

    public List<Hotspot> getHotspots() {
        return hotspots;
    }

    public CodeProportion getCodeProportion() {
        return new CodeProportion( getLabel(), getDescription(), getTooltip(), getViolations(), getBasisStatistic(), getAverage(), getHotspots(), getHistogram() );
    }

    protected String getDescription() {
        return ""; //$NON-NLS-1$
    }

    protected String getTooltip() {
        return getDescription();
    }

    public static double calculateAverage( double numberOfViolations, double numberOfBaseElems ) {
        if( numberOfBaseElems == 0 ) {
            return 0.0;
        }
        return 100 * numberOfViolations / numberOfBaseElems;
    }

    protected Histogram getHistogram() {
        return histogram;
    }

}
