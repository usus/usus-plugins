// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal;

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.LibraryLocation;
import org.projectusus.core.project2.IUSUSProject;
import org.projectusus.core.statistics.UsusModelProvider;

public class TestProjectCreator {

    private static final String OUTPUT_FOLDER = "bin";

    public static final String SOURCE_FOLDER = "src";

    private final IProject project;

    public TestProjectCreator( String name ) throws CoreException {
        super();
        project = createNewProject( name );
        addJavaNature( project );
        makeUsusProject();
    }

    public IProject getProject() {
        return project;
    }

    private IProject createNewProject( String name ) throws CoreException {
        IProject regularProject = getWorkspace().getRoot().getProject( name );
        regularProject.create( null );
        regularProject.open( null );
        return regularProject;
    }

    private void addJavaNature( IProject project ) throws CoreException {
        IProjectDescription description = project.getDescription();
        description.setNatureIds( new String[] { JavaCore.NATURE_ID } );
        project.setDescription( description, null );
        IJavaProject javaProject = JavaCore.create( project );
        addBinFolder( javaProject );
        addJDKToClasspath( javaProject );
        addSourceFolder( javaProject );
    }

    private void addJDKToClasspath( IJavaProject javaProject ) throws JavaModelException {
        List<IClasspathEntry> entries = new ArrayList<IClasspathEntry>();
        IVMInstall vmInstall = JavaRuntime.getDefaultVMInstall();
        LibraryLocation[] locations = JavaRuntime.getLibraryLocations( vmInstall );
        for( LibraryLocation element : locations ) {
            entries.add( JavaCore.newLibraryEntry( element.getSystemLibraryPath(), null, null ) );
        }
        javaProject.setRawClasspath( entries.toArray( new IClasspathEntry[entries.size()] ), null );
    }

    private void addBinFolder( IJavaProject javaProject ) throws CoreException {
        IFolder binFolder = javaProject.getProject().getFolder( OUTPUT_FOLDER );
        javaProject.setOutputLocation( binFolder.getFullPath(), null );
    }

    private void addSourceFolder( IJavaProject javaProject ) throws CoreException {
        IFolder sourceFolder = javaProject.getProject().getFolder( SOURCE_FOLDER );
        sourceFolder.create( false, true, null );

        IPackageFragmentRoot root = javaProject.getPackageFragmentRoot( sourceFolder );
        IClasspathEntry[] oldEntries = javaProject.getRawClasspath();
        IClasspathEntry[] newEntries = new IClasspathEntry[oldEntries.length + 1];
        System.arraycopy( oldEntries, 0, newEntries, 0, oldEntries.length );
        newEntries[oldEntries.length] = JavaCore.newSourceEntry( root.getPath() );
        javaProject.setRawClasspath( newEntries, null );
    }

    private void makeUsusProject() throws CoreException {
        makeUsusProject( true, project );
        UsusModelProvider.ususModelForAdapter().dropRawData( project );
    }

    static void makeUsusProject( boolean makeUsusProject, IProject project ) throws CoreException {
        IUSUSProject ususProject = (IUSUSProject)project.getAdapter( IUSUSProject.class );
        ususProject.setUsusProject( makeUsusProject );
    }

}
