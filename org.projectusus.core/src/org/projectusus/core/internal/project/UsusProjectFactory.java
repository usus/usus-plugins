// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.project;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdapterFactory;

public class UsusProjectFactory implements IAdapterFactory {

    // raw type in interface we implement - no chance
    @SuppressWarnings( "unchecked" )
    public Object getAdapter( Object adaptableObject, Class adapterType ) {
        Object result = null;
        if( adapterType == IUSUSProject.class && adaptableObject instanceof IProject ) {
            result = new UsusProject( (IProject)adaptableObject );
        }
        return result;
    }

    public Class<?>[] getAdapterList() {
        return new Class[] { IUSUSProject.class };
    }
}
