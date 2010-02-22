// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.core.internal.proportions.modelupdate.IUsusModelUpdate;

/**
 * provides update facilities to the Usus model.
 * 
 * @author leif
 */
public interface IUsusModelWriteAccess {

    void update( IUsusModelUpdate updateCommand );

    void dropRawData( IFile file );

    void dropRawData( IProject project );

    void resetRawData( IProject project );

    void addClassReference( IFile file, AbstractTypeDeclaration referencingType, IJavaElement referencedElement );

    void setCCValue( IFile file, MethodDeclaration methodDecl, int value );

    void setCCValue( IFile file, Initializer initializer, int value );

    void addClass( IFile file, AbstractTypeDeclaration node );

    void setMLValue( IFile file, MethodDeclaration methodDecl, int value );

    void setMLValue( IFile file, Initializer initializer, int value );
}
