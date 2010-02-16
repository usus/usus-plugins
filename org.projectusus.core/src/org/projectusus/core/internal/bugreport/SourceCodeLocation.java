// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.bugreport;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;

public class SourceCodeLocation {

    public static IPackageFragment getPackage( IJavaElement clazz ) {
        return getParent( clazz, IPackageFragment.class );

    }

    public static IType getClazz( IMethod method ) {
        return method.getDeclaringType();
    }

    public static IMethod getMethod( IJavaElement javaElement ) {
        return getParent( javaElement, IMethod.class );
    }

    @SuppressWarnings( "unchecked" )
    private static <T> T getParent( IJavaElement element, Class<T> clazz ) {
        IJavaElement result = element;
        while( result != null && !(clazz.isAssignableFrom( result.getClass() )) ) {
            result = result.getParent();
        }
        return (T)result;
    }

    public static MethodLocation getMethodLocation( IMethod method ) {
        IJavaElement clazz = getClazz( method );
        IPackageFragment packageFragment = getPackage( clazz );

        MethodLocation methodLocation = new MethodLocation();
        methodLocation.setMethodName( method.getElementName() );
        methodLocation.setClassName( clazz.getElementName() );
        methodLocation.setPackageName( packageFragment.getElementName() );

        return methodLocation;
    }
}
