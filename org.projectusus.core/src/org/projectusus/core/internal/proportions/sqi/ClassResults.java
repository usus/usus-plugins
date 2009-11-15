// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.sqi;

import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.core.internal.proportions.model.IHotspot;
import org.projectusus.core.internal.proportions.model.MetricKGHotspot;

public class ClassResults extends Results<Integer, MethodResults> {

    // TODO getNumberOfMethods

    private final int startPosition;
    private final String className;

    public ClassResults( String name, int startPosition ) {
        this.className = name;
        this.startPosition = startPosition;
    }

    public void setCCResult( MethodDeclaration node, int value ) {
        getResults( node ).setCCResult( value );
    }

    public void setMLResult( MethodDeclaration node, int value ) {
        getResults( node ).setMLResult( value );
    }

    private MethodResults getResults( MethodDeclaration node ) {
        return getResults( node.getStartPosition(), node.getName().toString() );
    }

    private MethodResults getResults( int start, String methodName ) {
        return getResults( new Integer( start ), new MethodResults( start, className, methodName ) );
    }

    public MethodResults getResults( IMethod method ) {
        if( method == null ) {
            return null;
        }
        ICompilationUnit compilationUnit = JDTSupport.getCompilationUnit( method );
        if( compilationUnit == null ) {
            return null;
        }

        try {
            for( Integer start : getAllKeys() ) {
                IJavaElement foundElement = compilationUnit.getElementAt( start.intValue() );
                if( method.equals( foundElement ) ) {
                    return getResults( start.intValue(), "" ); //$NON-NLS-1$
                }
            }
        } catch( JavaModelException e ) {
            return null;
        }
        return null;
    }

    public String getClassName() {
        return className;
    }

    public int getSourcePosition() {
        return startPosition;
    }

    @Override
    public int getViolationBasis( IsisMetrics metric ) {
        if( metric.isMethodTest() ) {
            return super.getViolationBasis( metric );
        }
        return 1;
    }

    @Override
    public int getViolationCount( IsisMetrics metric ) {
        if( metric.isMethodTest() ) {
            return super.getViolationCount( metric );
        }
        return metric.isViolatedBy( this ) ? 1 : 0;
    }

    @Override
    public void addHotspots( IsisMetrics metric, List<IHotspot> hotspots ) {
        if( metric.isMethodTest() ) {
            super.addHotspots( metric, hotspots );
        } else if( metric.isViolatedBy( this ) ) {
            hotspots.add( new MetricKGHotspot( getClassName(), this.getResultCount(), getSourcePosition() ) );
        }
    }

}
