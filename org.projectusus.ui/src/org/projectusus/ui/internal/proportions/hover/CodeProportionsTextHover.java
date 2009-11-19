// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.hover;

import static org.projectusus.ui.internal.proportions.hover.TextHoverFormatter.forJavaElement;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.ITypeRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jdt.ui.text.java.hover.IJavaEditorTextHover;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.ui.IEditorPart;

public class CodeProportionsTextHover implements IJavaEditorTextHover {

    private ITypeRoot typeRoot;

    public String getHoverInfo( ITextViewer textViewer, IRegion hoverRegion ) {
        String result = null;
        try {
            result = computeHoverInfo( hoverRegion );
        } catch( JavaModelException jamox ) {
            // ignore
        }
        return result;
    }

    public IRegion getHoverRegion( ITextViewer textViewer, int offset ) {
        IRegion result = null;
        try {
            result = computeHoverRegion( offset );
        } catch( JavaModelException jamox ) {
            // ignore
        }
        return result;
    }

    public void setEditor( IEditorPart editor ) {
        this.typeRoot = locateTopLevelElement( editor );
    }

    // internal methods
    // ////////////////

    private String computeHoverInfo( IRegion hoverRegion ) throws JavaModelException {
        IJavaElement javaElement = typeRoot.getElementAt( hoverRegion.getOffset() );
        return forJavaElement( javaElement ).format( javaElement );
    }

    private IRegion computeHoverRegion( int offset ) throws JavaModelException {
        IRegion result = null;
        IJavaElement element = typeRoot.getElementAt( offset );
        if( element != null ) {
            if( element.getElementType() == IJavaElement.METHOD ) {
                ISourceRange sourceRange = ((IMethod)element).getSourceRange();
                result = new Region( sourceRange.getOffset(), sourceRange.getLength() );
            }
        }
        return result;
    }

    private ITypeRoot locateTopLevelElement( IEditorPart editor ) {
        return JavaUI.getEditorInputTypeRoot( editor.getEditorInput() );
    }
}
