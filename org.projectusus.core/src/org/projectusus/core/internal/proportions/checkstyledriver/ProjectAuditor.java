// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.checkstyledriver;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.eclipsecs.core.builder.CheckerFactory;
import net.sf.eclipsecs.core.config.ICheckConfiguration;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.JavaCore;

import com.puppycrawl.tools.checkstyle.Checker;

class ProjectAuditor {

    private final ICheckConfiguration checkConfiguration;

    ProjectAuditor( ICheckConfiguration checkConfiguration ) {
        this.checkConfiguration = checkConfiguration;
    }

    void runAudit( IProject project, Collection<IFile> files, IProgressMonitor monitor ) throws Exception {
        if( !files.isEmpty() ) {
            ProjectAuditListener listener = new ProjectAuditListener( project, files );
            Checker checker = null;
            try {
                checker = CheckerFactory.createChecker( checkConfiguration, project );
                checker.addListener( listener );
                reconfigureSharedClassLoader( project );
                // // Experiment zum Festhalten des aktuellen Files NR
                // for( IFile currentFile : files ) {
                // NewWorkspaceResults.getInstance().setCurrentFile( currentFile );
                // List<File> tempFiles = new ArrayList<File>();
                // tempFiles.add( currentFile.getLocation().toFile() );
                // checker.process( tempFiles );
                // // done in ProjectAuditListener: NewWorkspaceResults.getInstance().setCurrentFile( null );
                //
                // }

                // vorher war es so und soll wieder so werden:
                checker.process( convertToLocations( files ) );

                if( listener.hasErrors() ) {
                    throw new CoreException( listener.getErrors() );
                }
            } finally {
                if( checker != null ) {
                    checker.removeListener( listener );
                }
            }
        }
    }

    private void reconfigureSharedClassLoader( IProject project ) throws CoreException {
        if( project.hasNature( JavaCore.NATURE_ID ) ) {
            CheckerFactory.getSharedClassLoader().intializeWithProject( project );
        }
    }

    private List<File> convertToLocations( Collection<IFile> files ) {
        List<File> result = new ArrayList<File>();
        for( IFile file : files ) {
            result.add( file.getLocation().toFile() );
        }
        return result;
    }
}
