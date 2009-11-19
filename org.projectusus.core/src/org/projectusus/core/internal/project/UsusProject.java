// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.project;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jdt.core.IMethod;
import org.osgi.service.prefs.BackingStoreException;
import org.projectusus.core.internal.UsusCorePlugin;
import org.projectusus.core.internal.bugreport.Bug;
import org.projectusus.core.internal.bugreport.BugFileReader;
import org.projectusus.core.internal.bugreport.BugList;
import org.projectusus.core.internal.bugreport.MethodLocation;
import org.projectusus.core.internal.bugreport.SaveBugsJob;
import org.projectusus.core.internal.bugreport.SourceCodeLocation;
import org.projectusus.core.internal.proportions.sqi.ProjectRawData;
import org.projectusus.core.internal.proportions.sqi.WorkspaceRawData;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

class UsusProject implements IUSUSProject {

    private static final String BUGS_FILENAME = "usus_bugs.xml"; //$NON-NLS-1$
    private static final String ATT_USUS_PROJECT = "ususProject"; //$NON-NLS-1$
    private final IProject project;

    UsusProject( IProject project ) {
        this.project = project;
    }

    public void setUsusProject( boolean ususProject ) {
        try {
            IEclipsePreferences node = getProjectSettings();
            node.putBoolean( ATT_USUS_PROJECT, ususProject );
            node.flush();
        } catch( BackingStoreException basex ) {
            UsusCorePlugin.log( basex );
        }
    }

    public boolean isUsusProject() {
        return getProjectSettings().getBoolean( ATT_USUS_PROJECT, false );
    }

    // internal
    // /////////

    private IEclipsePreferences getProjectSettings() {
        ProjectScope projectScope = new ProjectScope( project );
        return projectScope.getNode( UsusCorePlugin.getPluginId() );
    }

    public void saveBug( Bug bug ) {
        IFile file = project.getFile( BUGS_FILENAME );
        BugList bugs = loadFromFile( file );
        bugs.addBug( bug );
        SaveBugsJob saveBugsJob = new SaveBugsJob( file, bugs );
        saveBugsJob.schedule();
    }

    private BugList loadFromFile( IFile file ) {
        BugList bugList = new BugList();
        if( file.exists() ) {
            try {
                Reader reader = new InputStreamReader( file.getContents() );
                DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Element rootElement = parser.parse( new InputSource( reader ) ).getDocumentElement();
                ArrayList<Bug> bugs = new ArrayList<Bug>();
                new BugFileReader( rootElement ).read( bugs );
                bugList.addBugs( bugs );
            } catch( Exception e ) {
                UsusCorePlugin.log( e );
            }
        }
        return bugList;
    }

    public BugList getBugs() {
        IFile file = project.getFile( BUGS_FILENAME );
        return loadFromFile( file );
    }

    public ProjectRawData getProjectResults() {
        return WorkspaceRawData.getInstance().getProjectResults( project );
    }

    public String getProjectName() {
        return project.getName();
    }

    public BugList getBugsFor( IMethod method ) {
        BugList bugs = getBugs();

        MethodLocation methodLocation = SourceCodeLocation.getMethodLocation( method );
        return bugs.filter( methodLocation );
    }
}
