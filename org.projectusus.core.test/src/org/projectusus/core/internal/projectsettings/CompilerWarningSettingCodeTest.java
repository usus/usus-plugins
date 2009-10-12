package org.projectusus.core.internal.projectsettings;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.projectusus.core.projectsettings.CompilerWarningSettingCode;


public class CompilerWarningSettingCodeTest  {

    @Test
    public void testFromString() {
        CompilerWarningSettingCode code = CompilerWarningSettingCode.fromString("org.eclipse.jdt.core.compiler.problem.annotationSuperInterface");
        assertEquals(CompilerWarningSettingCode.annotationSuperInterface, code);
        
    }
    
    @Test
    public void testFromStringWithUnknownCode() throws Exception {
    	assertNull(CompilerWarningSettingCode.fromString("unbekannt"));
    }
    
    @Test
    public void testHasCode() throws Exception {
        assertTrue(CompilerWarningSettingCode.hasCode("org.eclipse.jdt.core.compiler.problem.annotationSuperInterface"));
        assertFalse(CompilerWarningSettingCode.hasCode("org.eclipse.jdt.core.compiler.problem.unknown"));
        assertFalse(CompilerWarningSettingCode.hasCode("unknown"));
    }

}
