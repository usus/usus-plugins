package org.projectusus.core.proportions.rawdata.jdtdriver.abstractness;

import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.projectusus.metrics.AbstractClassCollector;

public class AbstractClassInspector extends AbstractClassCollector {

    private int concreteCount = 0;
    private int abstractCount = 0;

    public void markAsAbstract( TypeDeclaration node ) {
        abstractCount++;
    }

    public void markAsConcrete( AbstractTypeDeclaration node ) {
        concreteCount++;
    }

    public int getConcreteCount() {
        return concreteCount;
    }

    public int getAbstractCount() {
        return abstractCount;
    }

}
