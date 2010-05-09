//Copyright (c) 2009-2010 by the projectusus.org contributors
//This software is released under the terms and conditions
//of the Eclipse Public License (EPL) 1.0.
//See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.projectsettings.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CompilerWarningSettingCodeTest {

 @Test
 public void testFromString() {
     CompilerWarningSettingCode code = CompilerWarningSettingCode.fromString( "org.eclipse.jdt.core.compiler.problem.annotationSuperInterface" ); //$NON-NLS-1$
     assertEquals( CompilerWarningSettingCode.annotationSuperInterface, code );

 }

 @Test
 public void testFromStringWithUnknownCode() {
     assertNull( CompilerWarningSettingCode.fromString( "unbekannt" ) ); //$NON-NLS-1$
 }

 @Test
 public void testHasCode() {
     assertTrue( CompilerWarningSettingCode.hasCode( "org.eclipse.jdt.core.compiler.problem.annotationSuperInterface" ) ); //$NON-NLS-1$
     assertFalse( CompilerWarningSettingCode.hasCode( "org.eclipse.jdt.core.compiler.problem.unknown" ) ); //$NON-NLS-1$
     assertFalse( CompilerWarningSettingCode.hasCode( "unknown" ) ); //$NON-NLS-1$
 }

}
