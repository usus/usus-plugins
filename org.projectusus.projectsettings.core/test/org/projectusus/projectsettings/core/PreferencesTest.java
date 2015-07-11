package org.projectusus.projectsettings.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class PreferencesTest {

    private static final String fileName = "org.eclipse.jdt.core.prefs";
    private Properties properties;
    private Preferences prefs;

    @Before
    public void before() throws IOException {
        properties = new Properties();
        properties.load( getClass().getClassLoader().getResourceAsStream( fileName ) );
        prefs = new Preferences( "myname", properties );
    }

    @Test
    public void testGetName() {
        assertEquals( "myname", prefs.getName() );
    }

    @Test
    public void testGetCodeCompletePrefs() {
        Properties all = prefs.getAll();
        Properties codeCompletePrefs = prefs.getCodeCompletePrefs();
        assertFalse( codeCompletePrefs.isEmpty() );
        Enumeration<Object> elements = codeCompletePrefs.keys();
        while( elements.hasMoreElements() ) {
            String element = (String)elements.nextElement();
            assertTrue( element.startsWith( "org.eclipse.jdt.core.codeComplete" ) );
            assertTrue( all.containsKey( element ) );
        }
    }

    @Test
    public void testGetCompilerWarningsPrefs() {
        Properties all = prefs.getAll();
        Properties compilerWarningPrefs = prefs.getCompilerWarningsPrefs();
        assertFalse( compilerWarningPrefs.isEmpty() );
        Enumeration<Object> elements = compilerWarningPrefs.keys();
        while( elements.hasMoreElements() ) {
            String element = (String)elements.nextElement();
            assertTrue( element.startsWith( "org.eclipse.jdt.core.compiler.problem" ) );
            assertTrue( all.containsKey( element ) );
        }
    }

    @Test
    public void testGetFormattingPrefs() {
        Properties all = prefs.getAll();
        Properties fromattingPrefs = prefs.getFormattingPrefs();
        assertFalse( fromattingPrefs.isEmpty() );
        Enumeration<Object> elements = fromattingPrefs.keys();
        while( elements.hasMoreElements() ) {
            String element = (String)elements.nextElement();
            assertTrue( element.startsWith( "org.eclipse.jdt.core.formatter" ) );
            assertTrue( all.containsKey( element ) );
        }
    }

    @Test
    public void testUpdateFrom() {
        Preferences newPrefs = new Preferences( "neu", new Properties() );
        newPrefs.updateFrom( prefs.getCompilerWarningsPrefs() );
        assertFalse( newPrefs.getAll().isEmpty() );
        assertFalse( newPrefs.getCompilerWarningsPrefs().isEmpty() );
        assertTrue( newPrefs.getCodeCompletePrefs().isEmpty() );
        assertTrue( newPrefs.getFormattingPrefs().isEmpty() );
    }

}
