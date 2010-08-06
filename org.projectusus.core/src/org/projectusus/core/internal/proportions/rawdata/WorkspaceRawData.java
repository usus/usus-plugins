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
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.statistics.IMetricsResultVisitor;

class WorkspaceRawData extends RawData<IProject, ProjectRawData> {

    public WorkspaceRawData() {
        super();
    }

    private ProjectRawData getOrCreateProjectRawData( IProject project ) {
        ProjectRawData rawData = getProjectRawData( project );
        if( rawData == null ) {
            rawData = new ProjectRawData();
            super.addRawData( project, rawData );
        }
        return rawData;
    }

    private ProjectRawData getProjectRawData( IProject project ) {
        return super.getRawData( project );
    }

    public void dropRawData( IProject project ) {
        ProjectRawData projectRawData = getProjectRawData( project );
        if( projectRawData != null ) {
            projectRawData.dropRawData();
            remove( project );
        }
    }

    public void dropRawData( IFile file ) {
        ProjectRawData projectRawData = getProjectRawData( file.getProject() );
        if( projectRawData != null ) {
            projectRawData.dropRawData( file );
        }
    }

    public void acceptAndGuide( IMetricsResultVisitor visitor ) {
        JavaModelPath path = visitor.getPath();
        if( path.isRestrictedToProject() ) {
            ProjectRawData projectRawData = this.getProjectRawData( path.getProject() );
            if( projectRawData != null ) {
                projectRawData.acceptAndGuide( visitor );
            }
        } else {
            for( ProjectRawData projectRD : getAllRawDataElements() ) {
                projectRD.acceptAndGuide( visitor );
            }
        }
    }

    public void putData( IFile file, MethodDeclaration methodDecl, String dataKey, int value ) {
        ProjectRawData projectRawData = getOrCreateProjectRawData( file.getProject() );
        if( projectRawData != null ) {
            projectRawData.putData( file, methodDecl, dataKey, value );
        }
    }

    public void putData( IFile file, Initializer initializer, String dataKey, int value ) {
        ProjectRawData projectRawData = getOrCreateProjectRawData( file.getProject() );
        if( projectRawData != null ) {
            projectRawData.putData( file, initializer, dataKey, value );
        }
    }

    public void putData( IFile file, AbstractTypeDeclaration node, String dataKey, int value ) {
        ProjectRawData projectRawData = getOrCreateProjectRawData( file.getProject() );
        if( projectRawData != null ) {
            projectRawData.putData( file, node, dataKey, value );
        }
    }

    public void removeRelationIfTargetIsGone( ClassDescriptor descriptor ) {
        ProjectRawData projectRawData = getProjectRawData( descriptor.getFile().getProject() );
        if( projectRawData != null ) {
            projectRawData.removeRelationIfTargetIsGone( descriptor );
        } else { // project dropped
            descriptor.removeFromPool();
        }
    }

}
