// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import java.util.Collection;
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

public class ClassRawData extends RawData<Integer, MethodRawData> implements IClassRawData {

    private static AcdModel acdModel = new AcdModel();

    private final int startPosition;
    private final int lineNumber;
    private final String className;
    private AdjacencyNode adjacencyNode;

    public ClassRawData( String name, int startPosition, int line ) {
        this.className = name;
        this.startPosition = startPosition;
        this.lineNumber = line;
        this.adjacencyNode = new AdjacencyNode( this );
        acdModel.add( this.adjacencyNode );
    }

    // for tests...
    public static void resetAcdModel() {
        acdModel = new AcdModel();
    }

    public static AcdModel getAcdModel() {
        return acdModel;
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
        Integer startObject = new Integer( start );
        MethodRawData rawData = super.getRawData( startObject );
        if( rawData == null ) {
            rawData = new MethodRawData( start, lineNumber, className, methodName );
            super.addRawData( startObject, rawData );
        }
        return rawData;
    }

    public IMethodRawData getMethodRawData( IMethod method ) {
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
    public int getViolationBasis( CodeProportionKind metric ) {
        if( metric.isMethodKind() ) {
            return super.getViolationBasis( metric );
        }
        return 1;
    }

    @Override
    public int getViolationCount( CodeProportionKind metric ) {
        if( metric.isMethodKind() ) {
            return super.getViolationCount( metric );
        }
        return metric.isViolatedBy( this ) ? 1 : 0;
    }

    @Override
    public void addToHotspots( CodeProportionKind metric, List<IHotspot> hotspots ) {
        if( metric.isMethodKind() ) {
            super.addToHotspots( metric, hotspots );
            return;
        }

        if( metric.isViolatedBy( this ) ) {
            if( metric.equals( CodeProportionKind.KG ) ) {
                hotspots.add( new MetricKGHotspot( getClassName(), this.getNumberOfMethods(), getSourcePosition(), getLineNumber() ) );
            }
            if( metric.equals( CodeProportionKind.ACD ) ) {
                hotspots.add( new MetricACDHotspot( getClassName(), getCCDResult(), getSourcePosition(), getLineNumber() ) );
            }
        }
    }

    public int getNumberOfMethods() {
        return getRawDataElementCount();
    }

    public int getCCDResult() {
        return adjacencyNode.getCCD();
    }

    public void addReferencedType( ClassRawData referencedRawData ) {
        if( referencedRawData == null || this == referencedRawData ) {
            return;
        }
        this.adjacencyNode.addChild( referencedRawData );
        referencedRawData.adjacencyNode.addParent( this );
    }

    @Override
    public void resetRawData() {
        adjacencyNode.removeNode( this );
        acdModel.remove( adjacencyNode );
        super.resetRawData();
    }

    void removeParent( ClassRawData classRawData ) {
        adjacencyNode.removeParent( this );
    }

    void removeChild( ClassRawData classRawData ) {
        adjacencyNode.removeChild( this );
    }

    public Collection<? extends ClassRawData> getAllKnownClasses() {
        return adjacencyNode.getAllKnownClasses();
    }

    public void invalidateAcd() {
        adjacencyNode.invalidate();
    }
}
