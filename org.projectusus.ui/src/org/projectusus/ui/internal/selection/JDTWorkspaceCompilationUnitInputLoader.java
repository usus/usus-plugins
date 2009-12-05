// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.selection;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.ui.IEditorInput;

/**
 * retrieves a compilation unit object for an editor input by using the JavaUI singleton facade.
 */
class JDTWorkspaceCompilationUnitInputLoader implements ICompilationUnitInputLoader {

    public ICompilationUnit loadCompilationUnit( IEditorInput editorInput ) {
        // Don't use JavaUI.getEditorInputTypeRoot because this only available since 3.4
        return (ICompilationUnit)JavaUI.getEditorInputJavaElement( editorInput );
    }
}
