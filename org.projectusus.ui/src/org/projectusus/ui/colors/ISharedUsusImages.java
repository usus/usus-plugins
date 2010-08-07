// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.colors;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.projectusus.ui.internal.UsusUIPlugin;

public interface ISharedUsusImages {

    // prefix all constants with the plugin id
    String ID = UsusUIPlugin.getPluginId();

    String OBJ_CODE_PROPORTIONS = ID + ".OBJ_CODE_PROPORTIONS"; //$NON-NLS-1$
    String OBJ_INFO = ID + ".OBJ_INFO"; //$NON-NLS-1$
    String OBJ_WARNINGS = ID + ".OBJ_WARNINGS"; //$NON-NLS-1$

    String OBJ_LEVELUP = ID + ".OBJ_LEVELUP"; //$NON-NLS-1$
    String OBJ_LEVELDOWN = ID + ".OBJ_LEVELDOWN"; //$NON-NLS-1$

    String VIEW_WARNING = ID + ".VIEW_WARNING"; //$NON-NLS-1$

    Image getImage( String key );

    ImageDescriptor getDescriptor( String key );

    Image getTrendImage( int level );
}
