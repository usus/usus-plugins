package org.projectusus.autotestsuite.core.internal.shortcuts;

import java.util.List;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.StructuredSelection;
import org.projectusus.autotestsuite.core.internal.AllJavaProjectsInWorkspace;
import org.projectusus.autotestsuite.core.internal.CommonDependencyRoot;
import org.projectusus.jfeet.selection.ElementsFrom;

public class MultiProjectJUnitPropertyTester extends PropertyTester {

    public boolean test( Object receiver, String property, Object[] args, Object expectedValue ) {
        StructuredSelection selection = new StructuredSelection( (List<?>)receiver );
        List<IJavaProject> projects = new ElementsFrom( selection ).as( IJavaProject.class );
        return commonDependencyRoot().existsFor( projects );
    }

    private CommonDependencyRoot commonDependencyRoot() {
        return new CommonDependencyRoot( new AllJavaProjectsInWorkspace() );
    }
}
