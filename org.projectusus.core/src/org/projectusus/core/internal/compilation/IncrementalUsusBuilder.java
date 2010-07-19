package org.projectusus.core.internal.compilation;

import java.util.Map;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.projectusus.core.UsusModelProvider;
import org.projectusus.core.util.Defect;

@SuppressWarnings( { "unused" } )
public class IncrementalUsusBuilder extends IncrementalProjectBuilder {

    private static final String BUILDER_ID = "com.example.builders.mybuilder"; //$NON-NLS-1$

    public IncrementalUsusBuilder() {
        super();
    }

    public static void addBuilderToProject( IProject project ) {
        try {
            if( projectAlreadyContainsBuilder( project ) ) {
                return;
            }
            addNewBuilderToProject( project );
        } catch( CoreException e ) {
            throw new Defect( e );
        }
    }

    private static boolean projectAlreadyContainsBuilder( IProject project ) throws CoreException {
        for( ICommand iCommand : project.getDescription().getBuildSpec() ) {
            if( iCommand.getBuilderName().equals( BUILDER_ID ) ) {
                return true;
            }
        }
        return false;
    }

    private static void addNewBuilderToProject( IProject project ) throws CoreException {
        IProjectDescription desc = project.getDescription();
        ICommand[] commands = desc.getBuildSpec();
        ICommand[] newCommands = new ICommand[commands.length + 1];

        // Add it after other builders.
        System.arraycopy( commands, 0, newCommands, 0, commands.length );
        ICommand command = desc.newCommand();
        command.setBuilderName( BUILDER_ID );
        newCommands[commands.length] = command;
        desc.setBuildSpec( newCommands );
        project.setDescription( desc, null );
    }

    @Override
    protected IProject[] build( int kind, Map args, IProgressMonitor monitor ) throws CoreException {
        if( kind == IncrementalProjectBuilder.FULL_BUILD ) {
            fullBuild( monitor );
        } else {
            IResourceDelta delta = getDelta( getProject() );
            if( delta == null ) {
                fullBuild( monitor );
            } else {
                incrementalBuild( delta, monitor );
            }
        }
        return null;
    }

    private void incrementalBuild( IResourceDelta delta, IProgressMonitor monitor ) {
        // TODO Auto-generated method stub

    }

    private void fullBuild( IProgressMonitor monitor ) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void startupOnInitialize() {
        // add builder init logic here
    }

    @Override
    protected void clean( IProgressMonitor monitor ) {
        UsusModelProvider.ususModelForAdapter().dropRawData( getProject() );
    }
}
