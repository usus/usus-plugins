// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import static org.projectusus.core.internal.proportions.rawdata.JDTSupport.getCompilationUnit;
import net.sourceforge.c4j.ContractReference;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.core.IMetricsResultVisitor;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.filerelations.model.ASTNodeHelper;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.Classname;
import org.projectusus.core.filerelations.model.WrappedTypeBinding;

@ContractReference( contractClassName = "FileRawDataContract" )
public class FileRawData extends RawData<Integer, ClassRawData> {

    private MetricsResults data;
    private final IFile file;
    private final ASTNodeHelper nodeHelper;

    public FileRawData( IFile file, ASTNodeHelper nodeHelper ) {
        super(); // sagt AL ;)
        this.file = file;
        this.nodeHelper = nodeHelper;
        data = new MetricsResults();
    }

    @Override
    public String toString() {
        return "Data for " + file.getFullPath() + ", " + getRawDataElementCount() + " classes"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    public void putData( WrappedTypeBinding boundType, MethodDeclaration methodDecl, String dataKey, int value ) {
        ClassRawData classRawData = getOrCreateClassRawData( boundType, methodDecl );
        if( classRawData != null ) {
            classRawData.putData( boundType, methodDecl, dataKey, value );
        }
    }

    public void putData( WrappedTypeBinding boundType, Initializer initializer, String dataKey, int value ) {
        ClassRawData classRawData = getOrCreateClassRawData( boundType, initializer );
        if( classRawData != null ) {
            classRawData.putData( initializer, dataKey, value );
        }
    }

    public void putData( WrappedTypeBinding boundType, AbstractTypeDeclaration node, String dataKey, int value ) {
        ClassRawData classRawData = getOrCreateClassRawData( boundType, node );
        if( classRawData != null ) {
            classRawData.putData( dataKey, value );
        }
    }

    private ClassRawData getOrCreateClassRawData( WrappedTypeBinding boundType, AbstractTypeDeclaration node ) {
        return getOrCreateClassRawData( boundType, nodeHelper.getStartPositionFor( node ), nodeHelper.calcLineNumberFor( node ), node.getName().toString() );
    }

    private ClassRawData getOrCreateClassRawData( WrappedTypeBinding typeBinding, int start, int lineNumber, String name ) {
        Integer startObject = Integer.valueOf( start );
        ClassRawData rawData = super.getRawData( startObject );
        if( rawData == null ) {
            rawData = new ClassRawData( typeBinding, name, nodeHelper, start, lineNumber );
            super.addRawData( startObject, rawData );
        }
        return rawData;
    }

    private ClassRawData getOrCreateClassRawData( WrappedTypeBinding boundType, MethodDeclaration node ) {
        return getOrCreateClassRawData( boundType, nodeHelper.findEnclosingClassOf( node ) );
    }

    private ClassRawData getOrCreateClassRawData( WrappedTypeBinding boundType, Initializer node ) {
        return getOrCreateClassRawData( boundType, nodeHelper.findEnclosingClassOf( node ) );
    }

    private ClassRawData getClassRawData( IJavaElement element ) {
        ICompilationUnit compilationUnit = getCompilationUnit( element );
        if( compilationUnit != null ) {
            try {
                return getClassRawData( element, compilationUnit );
            } catch( JavaModelException e ) {
                // catch silently
            }
        }
        return null;
    }

    private ClassRawData getClassRawData( IJavaElement element, ICompilationUnit compilationUnit ) throws JavaModelException {
        for( Integer startPosition : getAllKeys() ) {
            IJavaElement foundElement = compilationUnit.getElementAt( startPosition.intValue() );
            if( element.equals( foundElement ) ) {
                return super.getRawData( startPosition );
            }
        }
        return null;
    }

    private ClassRawData findClass( Classname classname ) {
        for( ClassRawData classRD : getAllRawDataElements() ) {
            if( classRD.isCalled( classname ) ) {
                return classRD;
            }
        }
        return null;
    }

    public void dropRawData() {
        for( ClassRawData classRD : getAllRawDataElements() ) {
            classRD.dropRawData();
        }
        removeAll();
    }

    public void acceptAndGuide( IMetricsResultVisitor visitor ) {
        visitor.inspectFile( file, data );
        JavaModelPath path = visitor.getPath();
        if( path.isRestrictedToType() ) {
            this.getClassRawData( path.getType() ).acceptAndGuide( visitor );
        } else {
            for( ClassRawData classRD : getAllRawDataElements() ) {
                classRD.acceptAndGuide( visitor );
            }
        }
    }

    public void removeRelationIfTargetIsGone( ClassDescriptor descriptor ) {
        ClassRawData classRawData = findClass( descriptor.getClassname() );
        if( classRawData == null ) {
            descriptor.removeFromPool();
        }
    }
}
