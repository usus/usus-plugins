// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.bugreport.core;

import org.eclipse.jdt.core.IMethod;

/**
 * adapter for IProjects (declared in plugin.xml); you can ask an IProject via getAdapter() for an object of this type and then query Usus-related info.
 * 
 * @author leif
 */
public interface IBuggyProject {

    void saveBug( Bug bug );

    BugList getBugs();

    BugList getBugsFor( IMethod method );

}
