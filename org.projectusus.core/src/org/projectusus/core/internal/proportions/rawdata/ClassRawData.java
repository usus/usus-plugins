// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import static org.projectusus.core.internal.proportions.rawdata.JDTSupport.getCompilationUnit;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.core.IMetricsResultVisitor;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.basis.SourceCodeLocation;
import org.projectusus.core.filerelations.model.ASTNodeHelper;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.Classname;
import org.projectusus.core.filerelations.model.WrappedTypeBinding;

public class ClassRawData extends RawData<Integer, MethodRawData> {

    private final SourceCodeLocation location;
    private MetricsResults data;

    private ClassDescriptor descriptor;
    private ASTNodeHelper nodeHelper;

    public ClassRawData( WrappedTypeBinding binding, String name, ASTNodeHelper nodeHelper, int startPosition, int line ) {
        super();
        this.nodeHelper = nodeHelper;
        location = new SourceCodeLocation( name, startPosition, line );
        data = new MetricsResults();
        this.descriptor = ClassDescriptor.of( binding );
    }

    // for debugging:
    @Override
    public String toString() {
        return "Class " + location.getName() + " in line " + location.getLineNumber() + " with " + getRawDataElementCount() + " methods."; //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$ //$NON-NLS-4$
    }

    void putData( MethodDeclaration node, String dataKey, int value ) {
        MethodRawData methodRawData = getOrCreateMethodRawData( node );
        if( methodRawData != null ) {
            methodRawData.putData( dataKey, value );
        }
    }

    void putData( Initializer node, String dataKey, int value ) {
        MethodRawData methodRawData = getOrCreateMethodRawData( node );
        if( methodRawData != null ) {
            methodRawData.putData( dataKey, value );
        }
    }

    private MethodRawData getOrCreateMethodRawData( MethodDeclaration node ) {
        return getOrCreateMethodRawData( nodeHelper.getStartPositionFor( node ), nodeHelper.calcLineNumberFor( node ), node.getName().toString() );
    }

    private MethodRawData getOrCreateMethodRawData( Initializer node ) {
        return getOrCreateMethodRawData( nodeHelper.getStartPositionFor( node ), nodeHelper.calcLineNumberFor( node ), "initializer" ); //$NON-NLS-1$
    }

    private MethodRawData getOrCreateMethodRawData( IMethod method ) {
        ICompilationUnit compilationUnit = getCompilationUnit( method );
        if( compilationUnit == null ) {
            return null;
        }
        try {
            for( Integer start : getAllKeys() ) {
                IJavaElement foundElement = compilationUnit.getElementAt( start.intValue() );
                if( method.equals( foundElement ) ) {
                    return getOrCreateMethodRawData( start.intValue(), 0, "" ); //$NON-NLS-1$
                }
            }
            return null;
        } catch( JavaModelException e ) {
            return null;
        }
    }

    private MethodRawData getOrCreateMethodRawData( int start, int lineNr, String methodName ) {
        Integer startObject = new Integer( start );
        MethodRawData rawData = super.getRawData( startObject );
        if( rawData == null ) {
            rawData = new MethodRawData( start, lineNr, location.getName(), methodName );
            super.addRawData( startObject, rawData );
        }
        return rawData;
    }

    public void dropRawData() {
        if( descriptor != null ) {
            descriptor.prepareRemoval();
        }
    }

    public void acceptAndGuide( IMetricsResultVisitor visitor ) {
        updateData();
        visitor.inspectClass( location, data );
        JavaModelPath path = visitor.getPath();
        if( path.isRestrictedToMethod() ) {
            this.getOrCreateMethodRawData( path.getMethod() ).acceptAndGuide( visitor );
        } else {
            for( MethodRawData rawData : getAllRawDataElements() ) {
                rawData.acceptAndGuide( visitor );
            }
        }
    }

    private void updateData() {
        putData( MetricsResults.METHODS, getRawDataElementCount() );
        putData( MetricsResults.CCD, descriptor.getCCD() );
    }

    public void putData( String dataKey, int value ) {
        data.put( dataKey, value );
    }

    public boolean isCalled( Classname classname ) {
        return location.getName().equals( classname.toString() );
    }
}
