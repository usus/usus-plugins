// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdapterFactory;
import org.projectusus.core.internal.UsusCorePlugin;

public class RawDataFactory implements IAdapterFactory {

    // raw type in interface we implement - no chance
    @SuppressWarnings( "unchecked" )
    public Object getAdapter( Object adaptableObject, Class adapterType ) {
        Object result = null;
        if( adapterType == IProjectRawData.class && adaptableObject instanceof IProject ) {
            IProject project = (IProject)adaptableObject;
            result = UsusCorePlugin.getUsusModel().getProjectRawData( project );
        }
        return result;
    }

    public Class<?>[] getAdapterList() {
        return new Class[] { IProjectRawData.class };
    }
}
