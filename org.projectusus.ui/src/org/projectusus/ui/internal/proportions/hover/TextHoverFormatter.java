// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.hover;

import static org.eclipse.jdt.core.IJavaElement.METHOD;
import static org.eclipse.jdt.core.IJavaElement.TYPE;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.projectusus.core.internal.project.IUSUSProject;
import org.projectusus.core.internal.proportions.sqi.ClassResults;
import org.projectusus.core.internal.proportions.sqi.FileResults;
import org.projectusus.core.internal.proportions.sqi.MethodResults;

enum TextHoverFormatter {

    NULL {
        @Override
        public String format( IJavaElement element ) {
            // indicate no hover should be shown at all
            return null;
        }
    },
    METHOD_BASED {
        @Override
        public String format( IJavaElement element ) {
            return formatMethod( (IMethod)element );
        }
    },
    TYPE_BASED {
        @Override
        public String format( IJavaElement element ) {
            // TODO lf how about KG, avg ML, CC?
            return element.getElementName();
        }
    };

    abstract String format( IJavaElement element );

    static TextHoverFormatter forJavaElement( IJavaElement element ) {
        switch( element.getElementType() ) {
        case METHOD:
            return METHOD_BASED;
        case TYPE:
            return TYPE_BASED;
        default:
            return NULL;
        }
    }

    private static String formatMethod( IMethod methodElement ) {
        String result = null;
        try {
            IResource resource = methodElement.getUnderlyingResource();
            FileResults fileResults = findFileInfo( resource );
            ClassResults classResults = fileResults.getResults( methodElement.getDeclaringType() );
            MethodResults methodResults = classResults.getResults( methodElement );
            result = new MethodFormatter( methodElement, methodResults, classResults ).format();
        } catch( JavaModelException jamox ) {
            // ignore
        }
        return result;
    }

    private static FileResults findFileInfo( IResource resource ) {
        FileResults result = null;
        IUSUSProject ususProject = getUsusProject( resource );
        if( ususProject != null && resource instanceof IFile ) {
            result = ususProject.getProjectResults().getFileResults( (IFile)resource );
        }
        return result;
    }

    private static IUSUSProject getUsusProject( IResource resource ) {
        return (IUSUSProject)resource.getProject().getAdapter( IUSUSProject.class );
    }
}
