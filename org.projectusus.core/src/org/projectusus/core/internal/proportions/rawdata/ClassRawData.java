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
import org.projectusus.core.basis.IClassRawData;
import org.projectusus.core.filerelations.model.BoundType;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.Classname;
import org.projectusus.core.filerelations.model.Packagename;

public class ClassRawData extends RawData<Integer, MethodRawData> implements IClassRawData {

    private final int startPosition;
    private final int lineNumber;
    private final String className;
    private ClassDescriptor descriptor;

    private ClassRawData( String name, int startPosition, int line ) {
        super();
        this.className = name;
        this.startPosition = startPosition;
        this.lineNumber = line;
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
        return "Class " + className + " in line " + lineNumber + " with " + getRawDataElementCount() + " methods."; //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$ //$NON-NLS-4$
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
            rawData = new MethodRawData( start, lineNr, className, methodName );
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

    public int getCCDResult() {
        return descriptor.getCCD();
    }

    public String getClassName() {
        return className;
    }

    public void dropRawData() {
        if( descriptor != null ) {
            descriptor.prepareRemoval();
        } else {
            System.out.println( "Could not remove class " + className + ", descriptor == null" ); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    public void acceptAndGuide( MetricsResultVisitor visitor ) {
        visitor.inspect( this );
        JavaModelPath path = visitor.getPath();
        if( path.isRestrictedToMethod() ) {
            this.getMethodRawData( path.getMethod() ).acceptAndGuide( visitor );
        } else {
            for( MethodRawData rawData : getAllRawDataElements() ) {
                rawData.acceptAndGuide( visitor );
            }
        }
    }

    public int getStartPosition() {
        return startPosition;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}
