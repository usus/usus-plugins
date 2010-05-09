//Copyright (c) 2009-2010 by the projectusus.org contributors
//This software is released under the terms and conditions
//of the Eclipse Public License (EPL) 1.0.
//See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.projectsettings.core;

import static org.junit.Assert.assertEquals;
import static org.projectusus.projectsettings.core.CompilerWarningSettingCode.annotationSuperInterface;

import java.util.Properties;

import org.junit.Test;

public class CompilerWarningSettingsTest {

 @Test
 public void testLoadValuesFromProperties() {
     CompilerWarningSettings settings = new CompilerWarningSettings();

     Properties properties = new Properties();
     properties.put( annotationSuperInterface.getSetting(), "error" ); //$NON-NLS-1$
     properties.put( "unknown", "warning" ); //$NON-NLS-1$//$NON-NLS-2$
     settings.loadValuesFromProperties( properties );

     CompilerWarningSetting setting = settings.getSetting( annotationSuperInterface );

     assertEquals( CompilerWarningLevel.error, setting.getValue() );
 }

}
