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
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.core.internal.proportions.model.IHotspot;
import org.projectusus.core.internal.proportions.model.MetricACDHotspot;
import org.projectusus.core.internal.proportions.model.MetricKGHotspot;
import org.projectusus.core.internal.proportions.sqi.acd.AcdModel;

public class ClassRawData extends RawData<Integer, MethodRawData> {

    private final int startPosition;
    private final String className;
    private final String fullyQualifiedName;

    public ClassRawData( String name, String fullyQualifiedName, int startPosition ) {
        this.className = name;
        this.fullyQualifiedName = fullyQualifiedName;
        this.startPosition = startPosition;
    }

    public void setCCResult( MethodDeclaration node, int value ) {
        getResults( node ).setCCResult( value );
    }

    public void setCCResult( Initializer node, int value ) {
        getResults( node ).setCCResult( value );
    }

    public void setMLResult( MethodDeclaration node, int value ) {
        getResults( node ).setMLResult( value );
    }

    public void setMLResult( Initializer node, int value ) {
        getResults( node ).setMLResult( value );
    }

    private MethodRawData getResults( MethodDeclaration node ) {
        return getResults( node.getStartPosition(), node.getName().toString() );
    }

    private MethodRawData getResults( Initializer node ) {
        return getResults( node.getStartPosition(), "initializer" ); //$NON-NLS-1$
    }

    private MethodRawData getResults( int start, String methodName ) {
        return getResults( new Integer( start ), new MethodRawData( start, className, methodName ) );
    }

    public MethodRawData getResults( IMethod method ) {
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
            return;
        }

        if( metric.isViolatedBy( this ) ) {
            if( metric.equals( IsisMetrics.KG ) ) {
                hotspots.add( new MetricKGHotspot( getClassName(), this.getNumberOfMethods(), getSourcePosition() ) );
            }
            if( metric.equals( IsisMetrics.ACD ) ) {
                hotspots.add( new MetricACDHotspot( getClassName(), getCCDResult(), getSourcePosition() ) );
            }
        }
    }

    public int getNumberOfMethods() {
        return getResultCount();
    }

    public int getCCDResult() {
        AcdModel acdModel = WorkspaceRawData.getInstance().getAcdModel();
        return acdModel.getCCD( fullyQualifiedName );
    }
}
