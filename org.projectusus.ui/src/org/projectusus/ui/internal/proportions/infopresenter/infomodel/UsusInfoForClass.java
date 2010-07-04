package org.projectusus.ui.internal.proportions.infopresenter.infomodel;

import java.util.List;

import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.statistics.ACDStatistic;
import org.projectusus.core.statistics.ClassSizeStatistic;
import org.projectusus.core.statistics.MethodCountVisitor;
import org.projectusus.core.statistics.MetricsResultVisitor;

public class UsusInfoForClass extends UsusInfoForFile {

    private final JavaModelPath path;

    UsusInfoForClass( IType clazz ) throws JavaModelException {
        super( clazz.getUnderlyingResource() );
        path = new JavaModelPath( clazz );
    }

    @Override
    protected void addFormattedProportion( List<String> result ) throws JavaModelException {
        super.addFormattedProportion( result );
        MetricsResultVisitor statistic = new ClassSizeStatistic( path ).visit();
        result.add( UsusModelElementFormatter.format( statistic.getLabel(), new MethodCountVisitor( path ).visit().getMethodCount() ) );
        result.add( UsusModelElementFormatter.format( "Cumulative Component Dependency (of class)", new ACDStatistic( path ).visit().getMetricsSum() ) );
    }

    @Override
    public String formatTitle() {
        return path.getType().getElementName();
    }
}
