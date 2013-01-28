package org.projectusus.core.proportions.rawdata.jdtdriver.classes;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.projectusus.metrics.ClassCollector;

public class ClassInspector extends ClassCollector {

    private List<String> classnames = new ArrayList<String>();

    public void init( AbstractTypeDeclaration node ) {
        classnames.add( node.getName().toString() );
    }

    public List<String> getClassnames() {
        return classnames;
    }
}
