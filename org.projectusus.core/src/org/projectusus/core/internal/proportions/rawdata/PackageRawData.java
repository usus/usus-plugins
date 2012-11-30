// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.core.IMetricsResultVisitor;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.filerelations.model.WrappedTypeBinding;

class PackageRawData extends RawData<IFile, FileRawData> {

    MetricsResults data;
    private Packagename pkg;

    public PackageRawData( Packagename pkg ) {
        this.pkg = pkg;
        data = new MetricsResults();
    }

    private FileRawData getOrCreateFileRawData( IFile file ) {
        FileRawData rawData = getFileRawData( file );
        if( rawData == null ) {
            rawData = createFileRawData( file );
        }
        return rawData;
    }

    private FileRawData getFileRawData( IFile file ) {
        return super.getRawData( file );
    }

    private FileRawData createFileRawData( IFile file ) {
        FileRawData rawData = new FileRawData( file );
        super.addRawData( file, rawData );
        return rawData;
    }

    public void dropRawData( IFile file ) {
        FileRawData fileRawData = getFileRawData( file );
        if( fileRawData != null ) {
            fileRawData.dropRawData();
        }
        remove( file ); // TODO nach oben?
    }

    public void dropRawData() {
        for( FileRawData fileRD : getAllRawDataElements() ) {
            fileRD.dropRawData();
        }
        removeAll();
    }

    public void acceptAndGuide( IMetricsResultVisitor visitor ) {
        updateData();
        visitor.inspectPackage( pkg, data );
        JavaModelPath path = visitor.getPath();
        if( path.isRestrictedToFile() ) {
            this.getFileRawData( path.getFile() ).acceptAndGuide( visitor );
        } else {
            for( FileRawData rawData : getAllRawDataElements() ) {
                rawData.acceptAndGuide( visitor );
            }
        }
    }

    private void updateData() {
        data.put( MetricsResults.CLASSES, getRawDataElementCount() );
    }

    public void putData( WrappedTypeBinding boundType, IFile file, MethodDeclaration methodDecl, String dataKey, int value ) {
        FileRawData fileRawData = getOrCreateFileRawData( file );
        if( fileRawData != null ) {
            fileRawData.putData( boundType, methodDecl, dataKey, value );
        }
    }

    public void putData( WrappedTypeBinding boundType, IFile file, Initializer initializer, String dataKey, int value ) {
        FileRawData fileRawData = getOrCreateFileRawData( file );
        if( fileRawData != null ) {
            fileRawData.putData( boundType, initializer, dataKey, value );
        }
    }

    public void putData( WrappedTypeBinding boundType, IFile file, AbstractTypeDeclaration node, String dataKey, int value ) {
        FileRawData fileRawData = getOrCreateFileRawData( file );
        if( fileRawData != null ) {
            fileRawData.putData( boundType, node, dataKey, value );
        }
    }

    public void removeRelationIfTargetIsGone( ClassDescriptor descriptor ) {

        IFile targetFile = descriptor.getFile();
        FileRawData fileRawData = getFileRawData( targetFile );
        if( fileRawData == null ) {
            descriptor.removeFromPool();
        } else {
            fileRawData.removeRelationIfTargetIsGone( descriptor );
        }
    }
}
