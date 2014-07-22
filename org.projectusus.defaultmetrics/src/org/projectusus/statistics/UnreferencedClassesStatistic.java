package org.projectusus.statistics;

import java.util.ArrayList;
import java.util.List;

import org.projectusus.core.basis.CodeProportion;
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
            if( shouldBeIncluded( clazz ) && hasNoParents( clazz ) ) {
                inspectFile( clazz.getFile(), null );
                // nodeHelper.getStartPositionFor( node );
                // nodeHelper.calcLineNumberFor( node );
                SourceCodeLocation location = new SourceCodeLocation( clazz.getClassname().toString(), 1, 1 );
                addResult( location, 1 );
            }
        }

        return super.getCodeProportion();
    }

    private boolean hasNoParents( ClassDescriptor clazz ) {
        return clazz.getParents().isEmpty();
    }

    private boolean shouldBeIncluded( ClassDescriptor clazz ) {
        String name = clazz.getClassname().toString();
        boolean doesNotEndInTest = !name.endsWith( "Test" );
        boolean doesNotEndInContract = !name.endsWith( "Contract" );
        return doesNotEndInTest && doesNotEndInContract;
    }

    @Override
    public int getBasis() {
        return ClassDescriptor.getAll().size();
    }

    @Override
    public String getLabel() {
        return "Unreferenced classes"; //$NON-NLS-1$
    }

    @Override
    protected String getRatingFunction() {
        return "\nRating function: f(value) = value";
    }

    @Override
    protected String getTooltip() {
        return "This statistic examines the number of incoming references to a class.\n" + getDescription(); //$NON-NLS-1$
    }

    @Override
    protected String hotspotsAreUnits() {
        return "that do not have any incoming references.";
    }
}
