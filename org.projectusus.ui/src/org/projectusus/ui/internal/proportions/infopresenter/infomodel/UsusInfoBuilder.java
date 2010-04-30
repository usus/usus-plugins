// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.infopresenter.infomodel;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

public class UsusInfoBuilder {

    public static IUsusInfo of( IJavaElement element ) {
        try {
            IJavaElement currentElement = element;
            while( currentElement != null ) {
                if( currentElement.getElementType() == IJavaElement.METHOD ) {
                    return new UsusInfoForMethod( (IMethod)currentElement );
                }
                if( currentElement.getElementType() == IJavaElement.TYPE ) {
                    return new UsusInfoForClass( (IType)currentElement );
                }
                if( currentElement.getElementType() == IJavaElement.COMPILATION_UNIT ) {
                    return new UsusInfoForFile( currentElement.getUnderlyingResource() );
                }
                currentElement = currentElement.getParent();
            }
        } catch( JavaModelException e ) {
            // do nothing
        }
        return new UnavailableUsusInfo( element );
    }

    private UsusInfoBuilder() {
        super();
    }
}
