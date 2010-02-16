// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.selection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorInput;
import org.junit.Before;
import org.junit.Test;

public class EditorInputAnalysisTest {

    private ICompilationUnitInputLoader loader;
    private IEditorInput editorInput;
    private ICompilationUnit compilationUnit;
    private IJavaElement javaElement;
    private EditorInputAnalysis inputAnalysis;

    @Before
    public void setUp() throws Exception {
        loader = mock( ICompilationUnitInputLoader.class );
        editorInput = mock( IEditorInput.class );
        compilationUnit = mock( ICompilationUnit.class );
        javaElement = mock( IMethod.class );
        when( loader.loadCompilationUnit( editorInput ) ).thenReturn( compilationUnit );
        when( compilationUnit.getElementAt( 1 ) ).thenReturn( javaElement );

        inputAnalysis = new EditorInputAnalysis( loader, editorInput );
    }

    @Test(expected=IllegalArgumentException.class)
    public void dontAcceptMissingLoader() {
        new EditorInputAnalysis( null, null );
    }
    
    @Test
    public void canHandleNull() {
        // we want to make sure that an analysis can be requested with null,
        // so that we don't need defensive null checks all over the code
        EditorInputAnalysis analysis = new EditorInputAnalysis( loader, null );
        assertNull( analysis.getCompilationUnit() );
        assertNull( analysis.getSelectedMethod( null ) );
    }

    @Test
    public void extractCompilationUnitFromInput() {
        assertEquals( compilationUnit, inputAnalysis.getCompilationUnit()  );
    }

    @Test
    public void ignoreNullSelection() {
        assertNull( inputAnalysis.getSelectedMethod( null ) );
    }

    @Test
    public void extractMethodFromTextSelection() {
        ISelection selection = new TextSelection( 1, 1 );
        assertEquals( javaElement, inputAnalysis.getSelectedMethod( selection ) );
    }
}
