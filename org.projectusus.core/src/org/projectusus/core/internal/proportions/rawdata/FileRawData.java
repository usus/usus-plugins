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
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.core.basis.IFileRawData;
import org.projectusus.core.filerelations.model.BoundType;
import org.projectusus.core.filerelations.model.Classname;
import org.projectusus.core.internal.proportions.rawdata.jdtdriver.ASTSupport;

public class FileRawData extends RawData<Integer, ClassRawData> implements IFileRawData {

    private final IFile fileOfRawData;

    public FileRawData( IFile file ) {
        super(); // sagt AL ;)
        this.fileOfRawData = file;
    }

    public IFile getFileOfRawData() {
        return fileOfRawData;
    }

    @Override
    public String toString() {
        return "Data for " + fileOfRawData.getFullPath() + ", " + getRawDataElementCount() + " classes"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    public void setCCValue( MethodDeclaration methodDecl, int value ) {
        getRawData( methodDecl ).setCCValue( methodDecl, value );
    }

    public void setCCValue( Initializer initializer, int value ) {
        getRawData( initializer ).setCCValue( initializer, value );
    }

    public void setMLValue( MethodDeclaration methodDecl, int value ) {
        getRawData( methodDecl ).setMLValue( methodDecl, value );
    }

    public void setMLValue( Initializer initializer, int value ) {
        getRawData( initializer ).setMLValue( initializer, value );
    }

    public void addClass( AbstractTypeDeclaration node ) {
        getClassRawData( node );
    }

    public ClassRawData getClassRawData( AbstractTypeDeclaration node ) {
        if( node == null ) {
            return null;
        }
        return getRawData( BoundType.of( node ), node.getStartPosition(), JDTSupport.calcLineNumber( node ), node.getName().toString() );
    }

    private ClassRawData getRawData( BoundType typeBinding, int start, int lineNumber, String name ) {
        Integer startObject = new Integer( start );
        ClassRawData rawData = super.getRawData( startObject );
        if( rawData == null ) {
            rawData = new ClassRawData( typeBinding, name, start, lineNumber );
            super.addRawData( startObject, rawData );
        }
        return rawData;
    }

    private ClassRawData getRawData( MethodDeclaration node ) {
        return getClassRawData( ASTSupport.findEnclosingClass( node ) );
    }

    private ClassRawData getRawData( Initializer node ) {
        return getClassRawData( ASTSupport.findEnclosingClass( node ) );
    }

    public ClassRawData getRawData( IJavaElement element ) {
        // TODO nr Duplicate code from getOrCreateRawData
        ICompilationUnit compilationUnit = getCompilationUnit( element );
        if( compilationUnit == null ) {
            return null;
        }

        try {
            return getClassRawData( element, compilationUnit );
        } catch( JavaModelException e ) {
            return null;
        }
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

    public ClassRawData findClass( Classname classname ) {
        for( ClassRawData classRD : getAllRawDataElements() ) {
            if( classRD.getClassName().equals( classname.toString() ) ) {
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

    public void acceptAndGuide( MetricsResultVisitor visitor ) {
        visitor.inspect( this );
        JavaModelPath path = visitor.getPath();
        if( path.isRestrictedToType() ) {
            this.getRawData( path.getType() ).acceptAndGuide( visitor );
        } else {
            for( ClassRawData classRD : getAllRawDataElements() ) {
                classRD.acceptAndGuide( visitor );
            }
        }
    }
}
