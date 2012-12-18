package org.projectusus.statistics;

import java.util.ArrayList;
import java.util.List;

import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.CodeStatistic;
import org.projectusus.core.basis.FileHotspot;
import org.projectusus.core.basis.Hotspot;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.statistics.CockpitExtension;

public class UnreferencedClassesStatistic extends CockpitExtension {

    private int violations = 0;
    private List<Hotspot> hotspots = new ArrayList<Hotspot>();

    public UnreferencedClassesStatistic() {
        super( codeProportionUnit_CLASS_label, 0 ); //$NON-NLS-1$
    }

    @Override
    public CodeProportion getCodeProportion() {
        for( ClassDescriptor clazz : ClassDescriptor.getAll() ) {
            if( clazz.getParents().isEmpty() ) {
                violations++;
                SourceCodeLocation location = new SourceCodeLocation( clazz.getClassname().toString(), 1, 1 );
                hotspots.add( new FileHotspot( location, 0, clazz.getFile() ) );
            }
        }
        double level = calculateAverage( violations, getBasis() );

        return new CodeProportion( getLabel(), getDescription(), getTooltip(), violations, getBasisStatistic(), level, hotspots, getHistogram(), getLocationType() );
    }

    @Override
    public CodeStatistic getBasisStatistic() {
        return new CodeStatistic( codeProportionUnit_CLASS_label, getBasis() );
    }

    @Override
    public int getBasis() {
        return ClassDescriptor.getAll().size();
    }

    @Override
    public String getLabel() {
        return "Unreferenced Classes"; //$NON-NLS-1$
    }

    @Override
    protected String getRatingFunction() {
        return "\nRating function: f(value) = value";
    }

    @Override
    protected String getTooltip() {
        return "The underlying metric determines the relationships between classes.\n" //$NON-NLS-1$
                + "The statistic reduces these class relationships to relationships between the packages involved and examines the result with regard to cyclic dependencies.\n" + //$NON-NLS-1$
                "Such dependencies appear when classes are located in the wrong packages. It indicates problems in the design and the structuring of the code.\n"
                + getDescription(); //$NON-NLS-1$
    }

    @Override
    protected String hotspotsAreUnits() {
        return "that are part of a cycle.";
    }
}
