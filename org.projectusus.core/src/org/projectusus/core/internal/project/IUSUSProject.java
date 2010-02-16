// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.project;

import org.eclipse.jdt.core.IMethod;
import org.projectusus.core.internal.bugreport.Bug;
import org.projectusus.core.internal.bugreport.BugList;

/**
 * adapter for IProjects (declared in plugin.xml); you can ask an IProject via getAdapter() for an object of this type and then query Usus-related info.
 * 
 * @author leif
 */
public interface IUSUSProject {

    boolean isUsusProject();

    void setUsusProject( boolean ususProject );

    void saveBug( Bug bug );

    BugList getBugs();

    String getProjectName();

    BugList getBugsFor( IMethod method );

}
