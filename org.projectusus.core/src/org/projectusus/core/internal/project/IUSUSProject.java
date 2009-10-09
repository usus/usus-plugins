// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.project;

import org.projectusus.core.internal.coverage.TestCoverage;

/**
 * adapter for IProjects (declared in plugin.xml); you can ask an IProject via getAdapter() for an object of this type and then query USUS-related info.
 * 
 * @author leif
 */
public interface IUSUSProject {

    boolean isUsusProject();

    void setUsusProject( boolean ususProject );

    void setTestCoverage( TestCoverage testCoverage );

    TestCoverage getTestCoverage();
}
