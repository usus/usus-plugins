package org.projectusus.core.statistics;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.CodeStatistic;
import org.projectusus.core.basis.Hotspot;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;

/**
 * Default implementation of <code>ICockpitExtension</code>.
 * <p>
 * Implementors of extensions for the <code>org.projectusus.core.statistics</code> extension point can use this implementation as a basis for their own implementations. It provides
 * a default mechanism to create a CodeProportion object. Implementors need to invoke the <code>addViolation()</code> method in order for this mechanism to work. Examples can be
 * found in the <code>org.projectusus.defaultmetrics</code> project.
 * <p>
 * The default implementation visits the whole raw data tree. Technically, it would be possible to restrict the analysis to a subtree, but it does not really make sense to do so
 * because the <code>JavaModelPath</code> object pointing to the subtree needs to be statically integrated into the visitor and thus cannot be adapted to the code currently being
 * analyzed.
 * 
 * @author Nicole Rauch
 * 
 */
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

    public int getMetricsSum() {
        return violationSum;
    }

    @Override
    public String getLabel() {
        return label;
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

    public double getLevel() {
        return calculateLevel( getViolations(), getBasis() );
    }

    public List<Hotspot> getHotspots() {
        return hotspots;
    }

    public CodeProportion getCodeProportion() {
        return new CodeProportion( getLabel(), getViolations(), getBasisStatistic(), getLevel(), getHotspots() );
    }

    public static double calculateLevel( double numberOfViolations, double numberOfBaseElems ) {
        if( numberOfBaseElems == 0 ) {
            return 0.0;
        }
        return 100.0 - max( 0, min( 100 * numberOfViolations / numberOfBaseElems, 100 ) );
    }

}
