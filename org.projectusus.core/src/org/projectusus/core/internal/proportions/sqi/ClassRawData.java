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
    private final int lineNumber;
    private final String className;
    private final String fullyQualifiedName;

    public ClassRawData( String name, String fullyQualifiedName, int startPosition, int line ) {
        this.className = name;
        this.fullyQualifiedName = fullyQualifiedName;
        this.startPosition = startPosition;
        this.lineNumber = line;
    }

    public void setCCValue( MethodDeclaration node, int value ) {
        getRawData( node ).setCCValue( value );
    }

    public void setCCValue( Initializer node, int value ) {
        getRawData( node ).setCCValue( value );
    }

    public void setMLValue( MethodDeclaration node, int value ) {
        getRawData( node ).setMLValue( value );
    }

    public void setMLValue( Initializer node, int value ) {
        getRawData( node ).setMLValue( value );
    }

    private MethodRawData getRawData( MethodDeclaration node ) {
        return getRawData( node.getStartPosition(), JDTSupport.calcLineNumber( node ), node.getName().toString() );
    }

    private MethodRawData getRawData( Initializer node ) {
        return getRawData( node.getStartPosition(), JDTSupport.calcLineNumber( node ), "initializer" ); //$NON-NLS-1$
    }

    private MethodRawData getRawData( int start, int lineNumber, String methodName ) {
        return getRawData( new Integer( start ), new MethodRawData( start, lineNumber, className, methodName ) );
    }

    public MethodRawData getRawData( IMethod method ) {
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
                    return getRawData( start.intValue(), 0, "" ); //$NON-NLS-1$
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

    public int getLineNumber() {
        return lineNumber;
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
                hotspots.add( new MetricKGHotspot( getClassName(), this.getNumberOfMethods(), getSourcePosition(), getLineNumber() ) );
            }
            if( metric.equals( IsisMetrics.ACD ) ) {
                hotspots.add( new MetricACDHotspot( getClassName(), getCCDResult(), getSourcePosition(), getLineNumber() ) );
            }
        }
    }

    public int getNumberOfMethods() {
        return getRawDataElementCount();
    }

    public int getCCDResult() {
        AcdModel acdModel = WorkspaceRawData.getInstance().getAcdModel();
        return acdModel.getCCD( fullyQualifiedName );
    }
}
