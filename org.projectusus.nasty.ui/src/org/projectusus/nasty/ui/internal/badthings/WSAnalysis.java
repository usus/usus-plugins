// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.nasty.ui.internal.badthings;

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.projectusus.nasty.ui.internal.util.TracingOption.NUCLEAR_STRIKE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

class WSAnalysis {

    private static final Random RND = new Random();

    WSAnalysisResult compute() {
        return buildResult( collectPrivateMethods() );
    }

    private WSAnalysisResult buildResult( Set<IMethod> methods ) {
        WSAnalysisResult result = new WSAnalysisResult( null, null );

        // don't attempt this on too small workspaces, it'd be too easy there
        if( methods.size() > 42 ) {
            IMethod[] indexedMethods = methods.toArray( new IMethod[methods.size()] );
            int attempts = 0;
            IMethod nukeTarget = indexedMethods[RND.nextInt( indexedMethods.length )];
            IMethod nameSwitchTarget = indexedMethods[RND.nextInt( indexedMethods.length )];

            while( !canSwitchNames( nukeTarget, nameSwitchTarget ) && attempts++ < 5 ) {
                nameSwitchTarget = indexedMethods[RND.nextInt( indexedMethods.length )];
            }
            if( canSwitchNames( nukeTarget, nameSwitchTarget ) ) {
                result = new WSAnalysisResult( nukeTarget, nameSwitchTarget );
            }
        }
        return result;
    }

    private boolean canSwitchNames( IMethod nukeTarget, IMethod nameSwitchTarget ) {
        String nukeName = nukeTarget.getElementName();
        String nameSwitchName = nameSwitchTarget.getElementName();
        boolean result = !nukeName.equals( nameSwitchName );
        result &= !hasMember( nukeTarget.getDeclaringType(), nameSwitchName );
        result &= !hasMember( nameSwitchTarget.getDeclaringType(), nukeName );
        return result;
    }

    private boolean hasMember( IType type, String name ) {
        boolean result = false;
        try {
            for( IMethod method : type.getMethods() ) {
                result |= name.equals( method.getElementName() );
            }
        } catch( JavaModelException jamox ) {
            // defensive: assume the method is there, try another combination
            result = true;
            trace( jamox, type.getElementName() );
        }
        return result;
    }

    private Set<IMethod> collectPrivateMethods() {
        Set<IMethod> collector = new HashSet<IMethod>();
        for( IJavaProject javaProject : loadJavaProjects() ) {
            try {
                for( IPackageFragment pf : javaProject.getPackageFragments() ) {
                    collectPrivateMethods( pf, collector );
                }
            } catch( JavaModelException jamox ) {
                trace( jamox, javaProject.getElementName() );
            }
        }
        return collector;
    }

    private void collectPrivateMethods( IPackageFragment packageFragment, Set<IMethod> collector ) {
        if( !packageFragment.isReadOnly() ) {
            try {
                for( ICompilationUnit cu : packageFragment.getCompilationUnits() ) {
                    collectPrivateMethods( cu, collector );
                }
            } catch( JavaModelException jamox ) {
                trace( jamox, packageFragment.getElementName() );
            }
        }
    }

    private void collectPrivateMethods( ICompilationUnit cu, Set<IMethod> collector ) {
        try {
            for( IType type : cu.getAllTypes() ) {
                for( IMethod method : type.getMethods() ) {
                    maybeAddMethod( method, collector );
                }
            }
        } catch( JavaModelException jamox ) {
            trace( jamox, cu.getElementName() );
        }
    }

    private void maybeAddMethod( IMethod method, Set<IMethod> collector ) throws JavaModelException {
        if( isNuclearStrikeTargetable( method ) ) {
            collector.add( method );
        }
    }

    private boolean isNuclearStrikeTargetable( IMethod method ) throws JavaModelException {
        return !method.isBinary() && !method.isReadOnly() && !method.isConstructor() && isPrivate( method );
    }

    private boolean isPrivate( IMethod method ) throws JavaModelException {
        return (method.getFlags() & Flags.AccPrivate) != 0;
    }

    private List<IJavaProject> loadJavaProjects() {
        List<IJavaProject> result = new ArrayList<IJavaProject>();
        for( IProject project : allWSProjects() ) {
            maybeAddJavaProject( project, result );
        }
        return result;
    }

    private IProject[] allWSProjects() {
        return getWorkspace().getRoot().getProjects();
    }

    private void maybeAddJavaProject( IProject project, List<IJavaProject> collector ) {
        try {
            if( project.hasNature( JavaCore.NATURE_ID ) ) {
                collector.add( JavaCore.create( project ) );
            }
        } catch( CoreException cex ) {
            trace( cex, project.getName() );
        }
    }

    private void trace( Exception ex, String elementInfo ) {
        NUCLEAR_STRIKE.trace( "Could not consider in ws analysis: " + elementInfo );
        NUCLEAR_STRIKE.trace( ex );
    }
}
