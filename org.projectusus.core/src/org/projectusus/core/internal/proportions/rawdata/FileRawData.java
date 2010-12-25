// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import static org.projectusus.core.internal.proportions.rawdata.JDTSupport.getCompilationUnit;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.core.IMetricsResultVisitor;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.filerelations.model.BoundTypeConverter;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.Classname;
import org.projectusus.core.filerelations.model.WrappedTypeBinding;

public class FileRawData extends RawData<Integer, ClassRawData> {

    private MetricsResults data;
    private final IFile file;

    public FileRawData( IFile file ) {
        super(); // sagt AL ;)
        this.file = file;
        data = new MetricsResults();
    }

    @Override
    public String toString() {
        return "Data for " + file.getFullPath() + ", " + getRawDataElementCount() + " classes"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    public void putData( MethodDeclaration methodDecl, String dataKey, int value ) {
        ClassRawData classRawData = getOrCreateClassRawData( methodDecl );
        if( classRawData != null ) {
            classRawData.putData( methodDecl, dataKey, value );
        }
    }

    public void putData( Initializer initializer, String dataKey, int value ) {
        ClassRawData classRawData = getOrCreateClassRawData( initializer );
        if( classRawData != null ) {
            classRawData.putData( initializer, dataKey, value );
        }
    }

    public void putData( AbstractTypeDeclaration node, String dataKey, int value ) {
        ClassRawData classRawData = getOrCreateClassRawData( node );
        if( classRawData != null ) {
            classRawData.putData( dataKey, value );
        }
    }

    private ClassRawData getOrCreateClassRawData( AbstractTypeDeclaration node ) {
        WrappedTypeBinding boundType = BoundTypeConverter.wrap( node );
        if( boundType == null ) {
            return null;
        }
        return getOrCreateClassRawData( boundType, node.getStartPosition(), JDTSupport.calcLineNumber( node ), node.getName().toString() );
    }

    private ClassRawData getOrCreateClassRawData( WrappedTypeBinding typeBinding, int start, int lineNumber, String name ) {
        Integer startObject = new Integer( start );
        ClassRawData rawData = super.getRawData( startObject );
        if( rawData == null ) {
            rawData = new ClassRawData( typeBinding, name, start, lineNumber );
            super.addRawData( startObject, rawData );
        }
        return rawData;
    }

    private ClassRawData getOrCreateClassRawData( MethodDeclaration node ) {
        return getOrCreateClassRawData( findEnclosingClass( node ) );
    }

    private ClassRawData getOrCreateClassRawData( Initializer node ) {
        return getOrCreateClassRawData( findEnclosingClass( node ) );
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

    private static AbstractTypeDeclaration findEnclosingClass( ASTNode node ) {
        ASTNode enclosingClass = node;
        while( enclosingClass != null && !(enclosingClass instanceof AbstractTypeDeclaration) ) {
            enclosingClass = enclosingClass.getParent();
        }
        return (AbstractTypeDeclaration)enclosingClass;
    }
}
