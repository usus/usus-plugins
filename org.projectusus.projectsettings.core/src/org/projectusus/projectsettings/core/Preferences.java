// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.projectsettings.core;

import java.util.Properties;

public class Preferences {

    private static final String WARNINGS_PREFIX = "org.eclipse.jdt.core.compiler.problem.";
    private static final String CODE_COMPLETE_PREFIX = "org.eclipse.jdt.core.codeComplete.";
    private static final String FORMATTING_PREFIX = "org.eclipse.jdt.core.formatter.";

    private final String name;
    private Properties prefsAsProperties;

    public Preferences( String name, Properties prefsAsProps ) {
        super();
        this.name = name;
        this.prefsAsProperties = prefsAsProps;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append( "name=" ); //$NON-NLS-1$
        result.append( getName() );
        return result.toString();
    }

    public Properties getCodeCompletePrefs() {
        return getPropertiesForPrefix( CODE_COMPLETE_PREFIX );
    }

    public Properties getCompilerWarningsPrefs() {
        return getPropertiesForPrefix( WARNINGS_PREFIX );
    }

    public Properties getFormattingPrefs() {
        return getPropertiesForPrefix( FORMATTING_PREFIX );
    }

    public void updateFrom( Properties properties ) {
        for( Object keyAsObject : properties.keySet() ) {
            String key = (String)keyAsObject;
            prefsAsProperties.put( key, properties.getProperty( key ) );
        }
    }

    private Properties getPropertiesForPrefix( String prefix ) {
        Properties properties = new Properties();
        for( Object keyAsObject : prefsAsProperties.keySet() ) {
            String key = (String)keyAsObject;
            if( key.startsWith( prefix ) ) {
                properties.put( key, prefsAsProperties.get( key ) );
            }
        }
        return properties;
    }

    public Properties getAll() {
        return prefsAsProperties;
    }
}
