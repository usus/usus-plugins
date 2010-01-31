// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public interface IFileRawData {

    IClassRawData getOrCreateRawData( IJavaElement javaElement );

    void addClassReference( AbstractTypeDeclaration referencingType, IJavaElement referencedElement );

    void setCCValue( MethodDeclaration methodDecl, int value );

    void setCCValue( Initializer initializer, int value );

    void addClass( AbstractTypeDeclaration node );

    void setMLValue( MethodDeclaration methodDecl, int value );

    void setMLValue( Initializer initializer, int value );
}
