// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata.jdtdriver;

import static org.projectusus.core.internal.util.CoreTexts.jdtDriver_computing;
import static org.projectusus.core.internal.util.TracingOption.SQI;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.projectusus.core.internal.UsusCorePlugin;
import org.projectusus.core.internal.proportions.FileSupport;
import org.projectusus.core.internal.proportions.IUsusModelWriteAccess;
import org.projectusus.core.internal.proportions.modelcomputation.ICodeProportionComputationTarget;

public class JDTDriver {

    private final IUsusModelWriteAccess model;
    private final ICodeProportionComputationTarget target;

    public JDTDriver( ICodeProportionComputationTarget target ) {
        model = UsusCorePlugin.getUsusModelWriteAccess();
        this.target = target;
    }

    public void run( IProgressMonitor monitor ) throws CoreException {
        for( IProject removedProject : target.getRemovedProjects() ) {
            System.out.println( "Removed project " + removedProject.getName() );
            model.dropRawData( removedProject );
        }
        monitor.beginTask( jdtDriver_computing, countTicks( target.getProjects() ) );
        for( IProject project : target.getProjects() ) {
            monitor.subTask( project.getName() );
            for( IFile removedFile : target.getRemovedFiles( project ) ) {
                System.out.println( "Removed file " + removedFile.getName() );
                model.dropRawData( removedFile );
            }
            computeChangedFiles( project, monitor );
        }
        monitor.done();
    }

    private int countTicks( Collection<IProject> projects ) throws CoreException {
        int result = 0;
        for( IProject project : projects ) {
            result += target.getFiles( project ).size();
        }
        return result;
    }

    private void computeChangedFiles( IProject project, IProgressMonitor monitor ) throws CoreException {
        Collection<IFile> files = target.getFiles( project );
        if( !files.isEmpty() ) {
            StatusCollector statusCollector = new StatusCollector();
            runDriver( project, files, statusCollector, monitor );
            statusCollector.finish();
        }
    }

    private void runDriver( IProject project, Collection<IFile> files, StatusCollector statusCollector, IProgressMonitor monitor ) {
        computationStarted( project );
        new ProjectDriver( project ).compute();
        for( IFile file : files ) {
            System.out.println( "Analyze file " + file.getName() );
            fileStarted( file );
            runDriverOnFile( file, statusCollector, monitor );
        }
    }

    private void runDriverOnFile( IFile file, StatusCollector statusCollector, IProgressMonitor monitor ) {
        try {
            driverForFile( file ).compute();
        } catch( Exception ex ) {
            statusCollector.add( ex );
        } finally {
            monitor.worked( 1 );
        }
    }

    private FileDriver driverForFile( IFile file ) {
        return FileSupport.isJavaFile( file ) ? new JavaFileDriver( file ) : new FileDriver( file );
    }

    private void computationStarted( IProject project ) {
        SQI.trace( "Computation started: " + project.toString() ); //$NON-NLS-1$
    }

    private void fileStarted( IFile file ) {
        SQI.trace( "File started: " + file.getFullPath() ); //$NON-NLS-1$
        model.dropRawData( file );
    }
}
