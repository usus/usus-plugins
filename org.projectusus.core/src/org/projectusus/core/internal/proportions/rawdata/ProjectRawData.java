// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.core.IMetricsResultVisitor;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.filerelations.model.ASTNodeHelper;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.filerelations.model.WrappedTypeBinding;

class ProjectRawData extends RawData<Packagename, PackageRawData> {

    MetricsResults data;
    private IProject project;

    public ProjectRawData( IProject project ) {
        this.project = project;
        data = new MetricsResults();
    }

    private PackageRawData getOrCreatePackageRawData( Packagename pkg ) {
        PackageRawData rawData = getPackageRawData( pkg );
        if( rawData == null ) {
            rawData = createPackageRawData( pkg );
        }
        return rawData;
    }

    private PackageRawData getPackageRawData( Packagename pkg ) {
        return super.getRawData( pkg );
    }

    private PackageRawData getPackageRawData( IFile file ) {
        for( PackageRawData pkg : getAllRawDataElements() ) {
            FileRawData fileRawData = pkg.getRawData( file );
            if( fileRawData != null ) {
                return pkg;
            }
        }
        return null;
    }

    private PackageRawData createPackageRawData( Packagename pkg ) {
        PackageRawData rawData = new PackageRawData( pkg );
        super.addRawData( pkg, rawData );
        return rawData;
    }

    public void dropRawData( IFile file ) {
        for( PackageRawData rawData : getAllRawDataElements() ) {
            rawData.dropRawData( file );
        }
    }

    public void dropRawData() {
        for( PackageRawData rawData : getAllRawDataElements() ) {
            rawData.dropRawData();
        }
        removeAll();
    }

    public void acceptAndGuide( IMetricsResultVisitor visitor ) {
        visitor.inspectProject( project, data );
        JavaModelPath path = visitor.getPath();
        if( path.isRestrictedToFile() ) {
            this.getPackageRawData( path.getFile() ).acceptAndGuide( visitor );
        } else {
            for( PackageRawData rawData : getAllRawDataElements() ) {
                rawData.acceptAndGuide( visitor );
            }
        }
    }

    public void putData( WrappedTypeBinding boundType, IFile file, MethodDeclaration methodDecl, ASTNodeHelper nodeHelper, String dataKey, int value ) {
        PackageRawData rawData = getOrCreatePackageRawData( boundType.getPackagename() );
        if( rawData != null ) {
            rawData.putData( boundType, file, methodDecl, nodeHelper, dataKey, value );
        }
    }

    public void putData( WrappedTypeBinding boundType, IFile file, Initializer initializer, ASTNodeHelper nodeHelper, String dataKey, int value ) {
        PackageRawData rawData = getOrCreatePackageRawData( boundType.getPackagename() );
        if( rawData != null ) {
            rawData.putData( boundType, file, initializer, nodeHelper, dataKey, value );
        }
    }

    public void putData( WrappedTypeBinding boundType, IFile file, AbstractTypeDeclaration node, ASTNodeHelper nodeHelper, String dataKey, int value ) {
        PackageRawData rawData = getOrCreatePackageRawData( boundType.getPackagename() );
        if( rawData != null ) {
            rawData.putData( boundType, file, node, nodeHelper, dataKey, value );
        }
    }

    public void removeRelationIfTargetIsGone( ClassDescriptor descriptor ) {

        IFile targetFile = descriptor.getFile();
        PackageRawData rawData = getPackageRawData( targetFile );
        if( rawData == null ) {
            descriptor.removeFromPool();
        } else {
            rawData.removeRelationIfTargetIsGone( descriptor );
        }
    }
}
