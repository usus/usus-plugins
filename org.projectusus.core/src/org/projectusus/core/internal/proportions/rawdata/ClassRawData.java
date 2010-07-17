// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import static org.projectusus.core.internal.proportions.rawdata.JDTSupport.getCompilationUnit;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.filerelations.model.BoundType;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.Classname;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.statistics.IMetricsResultVisitor;

public class ClassRawData extends RawData<Integer, MethodRawData> {

    private final SourceCodeLocation location;
    private MetricsResults data;

    private ClassDescriptor descriptor;

    private ClassRawData( String name, int startPosition, int line ) {
        super();
        location = new SourceCodeLocation( name, startPosition, line );
        data = new MetricsResults();
    }

    public ClassRawData( BoundType binding, String name, int startPosition, int line ) {
        this( name, startPosition, line );
        this.descriptor = ClassDescriptor.of( binding );
    }

    public ClassRawData( IFile file, String packageName, String name, int startPosition, int line ) {
        this( name, startPosition, line );
        this.descriptor = ClassDescriptor.of( file, new Classname( name ), Packagename.of( packageName ) );
    }

    // for debugging:
    @Override
    public String toString() {
        return "Class " + location.getName() + " in line " + location.getLineNumber() + " with " + getRawDataElementCount() + " methods."; //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$ //$NON-NLS-4$
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

    private MethodRawData getRawData( int start, int lineNr, String methodName ) {
        Integer startObject = new Integer( start );
        MethodRawData rawData = super.getRawData( startObject );
        if( rawData == null ) {
            rawData = new MethodRawData( start, lineNr, location.getName(), methodName );
            super.addRawData( startObject, rawData );
        }
        return rawData;
    }

    public MethodRawData getMethodRawData( IMethod method ) {
        ICompilationUnit compilationUnit = getCompilationUnit( method );
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
            return null;
        } catch( JavaModelException e ) {
            return null;
        }
    }

    public void dropRawData() {
        if( descriptor != null ) {
            descriptor.prepareRemoval();
        } else {
            System.out.println( "Could not remove class " + location.getName() + ", descriptor == null" ); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    public void acceptAndGuide( IMetricsResultVisitor visitor ) {
        updateData();
        visitor.inspectClass( location, data );
        JavaModelPath path = visitor.getPath();
        if( path.isRestrictedToMethod() ) {
            this.getMethodRawData( path.getMethod() ).acceptAndGuide( visitor );
        } else {
            for( MethodRawData rawData : getAllRawDataElements() ) {
                rawData.acceptAndGuide( visitor );
            }
        }
    }

    private void updateData() {
        data.add( MetricsResults.METHODS, new Integer( getRawDataElementCount() ) );
        data.add( MetricsResults.CCD, new Integer( descriptor.getCCD() ) );
    }

    public boolean isCalled( Classname classname ) {
        return location.getName().equals( classname.toString() );
    }
}
