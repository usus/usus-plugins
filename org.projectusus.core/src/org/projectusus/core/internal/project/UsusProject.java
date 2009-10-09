// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.project;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.osgi.service.prefs.BackingStoreException;
import org.projectusus.core.internal.UsusCorePlugin;
import org.projectusus.core.internal.coverage.TestCoverage;


class UsusProject implements IUSUSProject {

    private static final String ATT_USUS_PROJECT = "ususProject";
    private static final String ATT_TEST_COVERAGE_TOTAL = "testCoverage.total";
    private static final String ATT_TEST_COVERAGE_COVERED = "testCoverage.covered";
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
            UsusCorePlugin.getDefault().log( basex );
        }
    }

    public boolean isUsusProject() {
        return getProjectSettings().getBoolean( ATT_USUS_PROJECT, false );
    }

    public TestCoverage getTestCoverage() {
        IEclipsePreferences node = getProjectSettings();
        int total = node.getInt( ATT_TEST_COVERAGE_TOTAL, 0 );
        int covered = node.getInt( ATT_TEST_COVERAGE_COVERED, 0 );
        return new TestCoverage( covered, total );
    }

    public void setTestCoverage( TestCoverage testCoverage ) {
        try {
            IEclipsePreferences node = getProjectSettings();
            node.putInt( ATT_TEST_COVERAGE_TOTAL, testCoverage.getTotalCount() );
            node.putInt( ATT_TEST_COVERAGE_COVERED, testCoverage.getCoveredCount() );
            node.flush();
        } catch( BackingStoreException basex ) {
            UsusCorePlugin.getDefault().log( basex );
        }
    }

    // internal
    // /////////

    private IEclipsePreferences getProjectSettings() {
        ProjectScope projectScope = new ProjectScope( project );
        return projectScope.getNode( UsusCorePlugin.getPluginId() );
    }
}
