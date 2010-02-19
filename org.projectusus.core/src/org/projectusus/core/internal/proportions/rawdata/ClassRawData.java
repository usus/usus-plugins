// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.core.internal.proportions.model.IHotspot;
import org.projectusus.core.internal.proportions.model.MetricACDHotspot;
import org.projectusus.core.internal.proportions.model.MetricKGHotspot;

class ClassRawData extends RawData<Integer, MethodRawData> implements IClassRawData {

    private final int startPosition;
    private final int lineNumber;
    private final String className;
    private final ClassRepresenter representer;

    public ClassRawData( String name, int startPosition, int line ) {
        this.className = name;
        this.startPosition = startPosition;
        this.lineNumber = line;
        this.representer = new ClassRepresenter( this );
    }

    // for debugging:
    @Override
    public String toString() {
        return "Class " + className + " in line " + lineNumber + " with " + getNumberOfMethods() + " methods."; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    }

    public ClassRepresenter getRepresenter() {
        return representer;
    }

    void setCCValue( MethodDeclaration node, int value ) {
        getRawData( node ).setCCValue( value );
    }

    void setCCValue( Initializer node, int value ) {
        getRawData( node ).setCCValue( value );
    }

    void setMLValue( MethodDeclaration node, int value ) {
        getRawData( node ).setMLValue( value );
    }

    void setMLValue( Initializer node, int value ) {
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

    public MethodRawData getMethodRawData( IMethod method ) {
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

    @Override
    public int getNumberOf( CodeProportionUnit unit ) {
        if( unit.isMethodKind() ) {
            return super.getNumberOf( unit );
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
                hotspots.add( new MetricKGHotspot( className, getNumberOfMethods(), startPosition, lineNumber ) );
            }
            if( metric.equals( CodeProportionKind.ACD ) ) {
                hotspots.add( new MetricACDHotspot( className, getCCDResult(), startPosition, lineNumber ) );
            }
        }
    }

    public int getNumberOfMethods() {
        return getNumberOf( CodeProportionUnit.METHOD );
    }

    public int getCCDResult() {
        return a_getCCD();
    }

    public void addChild( ClassRawData child ) {
        if( child == null || this == child ) {
            return;
        }
        a_addChild( child );
        child.a_addParent( this );
    }

    @Override
    public void resetRawData() {
        a_removeNode( this );
        super.resetRawData();
    }

    void removeParent( ClassRawData classRawData ) {
        a_removeParent( classRawData );
    }

    void removeChild( ClassRawData classRawData ) {
        a_removeChild( classRawData );
    }

    public Set<ClassRawData> getChildren() {
        return a_getChildren();
    }

    public Set<ClassRawData> getParents() {
        return a_getParents();
    }

    public Set<ClassRawData> getAllChildren() {
        return a_getAllChildren();
    }

    public Set<ClassRawData> getAllParents() {
        return a_getAllParents();
    }

    public void invalidateAcd() {
        a_invalidate();
    }

    public String getClassName() {
        return className;
    }

    // aus AdjacencyNode

    private final Set<ClassRawData> directChildren = new HashSet<ClassRawData>();
    private final SetOfClasses transitiveChildren = new SetOfClasses();
    private final Set<ClassRawData> directParents = new HashSet<ClassRawData>();
    private final SetOfClasses transitiveParents = new SetOfClasses();

    private void a_addChild( ClassRawData child ) {
        transitiveChildren.invalidate();
        directChildren.add( child );
    }

    private void a_addParent( ClassRawData parent ) {
        transitiveParents.invalidate();
        directParents.add( parent );
    }

    public int getChildCount() {
        return directChildren.size();
    }

    private Set<ClassRawData> a_getChildren() {
        return Collections.unmodifiableSet( directChildren );
    }

    private Set<ClassRawData> a_getParents() {
        return Collections.unmodifiableSet( directParents );
    }

    private Set<ClassRawData> a_getAllChildren() {
        a_updateChildren();
        return transitiveChildren.getClasses();
    }

    private Set<ClassRawData> a_getAllParents() {
        a_updateParents();
        return transitiveParents.getClasses();
    }

    private void a_updateChildren() {
        if( transitiveChildren.areUpToDate() ) {
            return;
        }
        transitiveChildren.startInitialization( this );
        for( ClassRawData child : directChildren ) {
            transitiveChildren.addAllClassesDependingOn( child, child.getAllChildren() );
        }
    }

    private void a_updateParents() {
        if( transitiveParents.areUpToDate() ) {
            return;
        }
        transitiveParents.startInitialization( this );
        for( ClassRawData parent : directParents ) {
            transitiveChildren.addAllClassesDependingOn( parent, parent.getAllParents() );
        }
    }

    private void a_removeParent( ClassRawData classRawData ) {
        transitiveParents.invalidate();
        directParents.remove( classRawData );
    }

    private void a_removeChild( ClassRawData classRawData ) {
        transitiveChildren.invalidate();
        directChildren.remove( classRawData );
    }

    private int a_getCCD() {
        a_updateChildren();
        return transitiveChildren.size();
    }

    private void a_invalidate() { // TODO what needs to be done for the parents?
        if( !transitiveChildren.areUpToDate() ) {
            return; // already invalidated
        }
        transitiveChildren.invalidate();
        for( ClassRawData parent : directParents ) {
            parent.invalidateAcd();
        }
    }

    private void a_removeNode( ClassRawData classRawData ) {
        a_invalidate();
        for( ClassRawData parent : getParents() ) {
            parent.removeChild( classRawData );
        }
        for( ClassRawData child : getChildren() ) {
            child.removeParent( classRawData );
        }
    }
}
