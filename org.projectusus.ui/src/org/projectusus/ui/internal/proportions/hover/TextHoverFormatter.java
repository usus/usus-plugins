// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.hover;

import static org.eclipse.jdt.core.IJavaElement.METHOD;
import static org.eclipse.jdt.core.IJavaElement.TYPE;

import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.projectusus.core.internal.bugreport.BugList;
import org.projectusus.core.internal.project.IUSUSProject;
import org.projectusus.core.internal.proportions.rawdata.ClassRawData;
import org.projectusus.core.internal.proportions.rawdata.FileRawData;
import org.projectusus.core.internal.proportions.rawdata.MethodRawData;

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
            return formatType( (IType)element );
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

    private static String formatType( IType typeElement ) {
        String result = null;
        try {
            IResource resource = typeElement.getUnderlyingResource();
            ExtractRawData extractor = new ExtractRawData( resource );
            if( extractor.isDataAvailable() ) {
                FileRawData fileData = extractor.getFileRawData();
                ClassRawData classData = fileData.getRawData( typeElement );
                if( classData != null ) {
                    result = new TypeFormatter( typeElement, classData ).format();
                }
            }
        } catch( JavaModelException jamox ) {
            // ignore
        }
        return result;
    }

    private static String formatMethod( IMethod methodElement ) {
        String result = null;
        try {
            IResource resource = methodElement.getUnderlyingResource();
            ExtractRawData extractor = new ExtractRawData( resource );
            if( extractor.isDataAvailable() ) {
                ClassRawData classRawData = extractor.getFileRawData().getRawData( methodElement.getDeclaringType() );
                MethodRawData methodRawData = classRawData.getRawData( methodElement );
                BugList bugs = findBugInfo( methodElement );
                result = new MethodFormatter( methodElement, methodRawData, classRawData, bugs ).format();
            }
        } catch( JavaModelException jamox ) {
            // ignore
        }
        return result;
    }

    private static BugList findBugInfo( IMethod method ) {
        BugList result = new BugList();
        IUSUSProject ususProject = getUsusProject( method.getJavaProject().getProject() );
        if( ususProject != null ) {
            result = ususProject.getBugsFor( method );
        }
        return result;
    }

    private static IUSUSProject getUsusProject( IResource resource ) {
        return (IUSUSProject)resource.getProject().getAdapter( IUSUSProject.class );
    }
}
