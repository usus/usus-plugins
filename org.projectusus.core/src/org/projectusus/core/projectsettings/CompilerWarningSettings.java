// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.projectsettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CompilerWarningSettings {

    private final List<CompilerWarningSetting> settings = new ArrayList<CompilerWarningSetting>();

    public CompilerWarningSettings() {
        initList();
    }

    public List<CompilerWarningSetting> getSettings() {
        return settings;
    }

    private void initList() {
        for( CompilerWarningSettingCode code : CompilerWarningSettingCode.values() ) {
            settings.add( new CompilerWarningSetting( code ) );
        }
    }

    public void loadValuesFromProperties( Properties properties ) {
        for( Object key : properties.keySet() ) {
            String codeString = String.valueOf( key );
            if( CompilerWarningSettingCode.hasCode( codeString ) ) {
                CompilerWarningSetting setting = getSetting( codeString );
                String valueString = String.valueOf( properties.get( codeString ) );
                setting.setValue( valueString );
            }

        }
    }

    private CompilerWarningSetting getSetting( String codeString ) {
        CompilerWarningSettingCode code = CompilerWarningSettingCode.fromString( codeString );
        return getSetting( code );
    }

    public CompilerWarningSetting getSetting( CompilerWarningSettingCode code ) {
        for( CompilerWarningSetting setting : settings ) {
            if( setting.hasCode( code ) ) {
                return setting;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        for( CompilerWarningSetting setting : settings ) {
            buffer.append( setting );
            buffer.append( "\n" );
        }
        return buffer.toString();
    }
}
