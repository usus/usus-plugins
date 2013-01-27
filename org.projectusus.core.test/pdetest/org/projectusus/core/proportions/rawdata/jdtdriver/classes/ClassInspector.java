package org.projectusus.core.proportions.rawdata.jdtdriver.classes;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.projectusus.core.metrics.MetricsCollector;

public class ClassInspector extends MetricsCollector {

    private List<String> classnames = new ArrayList<String>();

    @Override
    public boolean visit( TypeDeclaration node ) {
        classnames.add( node.getName().toString() );
        return true;
    }

    @Override
    public boolean visit( AnnotationTypeDeclaration node ) {
        classnames.add( node.getName().toString() );
        return true;
    }

    @Override
    public boolean visit( EnumDeclaration node ) {
        classnames.add( node.getName().toString() );
        return true;
    }

    public List<String> getClassnames() {
        return classnames;
    }
}
