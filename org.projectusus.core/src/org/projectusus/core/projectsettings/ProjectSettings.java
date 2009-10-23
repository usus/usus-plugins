// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.projectsettings;

public class ProjectSettings {

    private final CompilerWarningSettings compilerwarningSettings = new CompilerWarningSettings();
    private final String name;

    public ProjectSettings( String name ) {
        super();
        this.name = name;
    }

    public CompilerWarningSettings getCompilerwarningSettings() {
        return compilerwarningSettings;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append( "name=" ); //$NON-NLS-1$
        result.append( getName() );
        result.append( "\nCompilerwarningSettings:\n" ); //$NON-NLS-1$
        result.append( getCompilerwarningSettings() );
        return result.toString();
    }

}
