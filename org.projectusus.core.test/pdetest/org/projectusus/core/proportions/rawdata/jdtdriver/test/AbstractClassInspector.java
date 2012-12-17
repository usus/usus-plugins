package org.projectusus.core.proportions.rawdata.jdtdriver.test;

import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.projectusus.metrics.AbstractClassCollector;

public class AbstractClassInspector extends AbstractClassCollector {

    private int concreteCount = 0;
    private int abstractCount = 0;

    protected boolean markAsAbstract( TypeDeclaration node ) {
        abstractCount++;
        return true;
    }

    protected boolean markAsConcrete( AbstractTypeDeclaration node ) {
        concreteCount++;
        return true;
    }

    public int getConcreteCount() {
        return concreteCount;
    }

    public int getAbstractCount() {
        return abstractCount;
    }

}
