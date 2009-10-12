package org.projectusus.core.internal.projectsettings;

import static org.junit.Assert.assertEquals;
import static org.projectusus.core.projectsettings.CompilerWarningSettingCode.annotationSuperInterface;

import java.util.Properties;

import org.junit.Test;
import org.projectusus.core.projectsettings.CompilerWarningLevel;
import org.projectusus.core.projectsettings.CompilerWarningSetting;
import org.projectusus.core.projectsettings.CompilerWarningSettings;

public class CompilerWarningSettingsTest {

    
    @Test
    public void testLoadValuesFromProperties() throws Exception {
    	CompilerWarningSettings settings = new CompilerWarningSettings();
	
		Properties properties = new Properties();
		properties.put(annotationSuperInterface.getSetting(), "error");
		properties.put("unknown", "warning");
		settings.loadValuesFromProperties(properties);
	
		CompilerWarningSetting setting = settings.getSetting(annotationSuperInterface);
	
		assertEquals(CompilerWarningLevel.ERROR, setting.getValue());
    }
    
}
