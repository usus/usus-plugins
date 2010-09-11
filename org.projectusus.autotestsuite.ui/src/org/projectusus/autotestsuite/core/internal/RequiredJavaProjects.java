package org.projectusus.autotestsuite.core.internal;

import static java.text.MessageFormat.format;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaModelException;
import org.projectusus.autotestsuite.ui.internal.AutoTestSuitePlugin;

public class RequiredJavaProjects {

    private final IAllJavaProjects allJavaProjects;

    public RequiredJavaProjects( IAllJavaProjects allJavaProjects ) {
        this.allJavaProjects = allJavaProjects;
    }

    public Collection<IJavaProject> findFor( IJavaProject project ) {
        Set<IJavaProject> projects = new LinkedHashSet<IJavaProject>();
        projects.add( project );
        try {
            for( String name : project.getRequiredProjectNames() ) {
                IJavaProject required = findJavaProject( name );
                // this works since there cannot be cycles in project dependencies
                projects.addAll( findFor( required ) );
            }
        } catch( JavaModelException jamox ) {
            AutoTestSuitePlugin.log( jamox.getStatus() );
        }
        return projects;
    }

    private IJavaProject findJavaProject( String name ) {
        for( IJavaProject project : allJavaProjects.find() ) {
            if( name.equals( project.getElementName() ) ) {
                return project;
            }
        }
        throw new IllegalStateException( formulateComplaint( name ) );
    }

    private String formulateComplaint( String name ) {
        String pattern = "Project claims to have dependency ''{0}'' which is not found in workspace.";
        return format( pattern, name );
    }
}
