// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.coverage;

import static org.eclipse.core.runtime.Platform.getExtensionRegistry;
import static org.projectusus.core.internal.UsusCorePlugin.getPluginId;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.projectusus.core.internal.UsusCorePlugin;

class ExtensionLoader {
    private static final String EXT_PT_SELECTORS = "testSuiteSelectors"; //$NON-NLS-1$

    List<? extends ITestSuiteForCoverageSelector> loadSelectors() {
        List<ITestSuiteForCoverageSelector> result = new ArrayList<ITestSuiteForCoverageSelector>();
        for( IConfigurationElement element : getConfigurationElements() ) {
            loadSelector( result, element );
        }
        return result;
    }

    private IConfigurationElement[] getConfigurationElements() {
        return getExtensionRegistry().getConfigurationElementsFor( getPluginId(), EXT_PT_SELECTORS );
    }

    private void loadSelector( List<ITestSuiteForCoverageSelector> result, IConfigurationElement element ) {
        try {
            Object object = element.createExecutableExtension( "class" ); //$NON-NLS-1$
            if( object instanceof ITestSuiteForCoverageSelector ) {
                result.add( (ITestSuiteForCoverageSelector)object );
            }
        } catch( CoreException cex ) {
            UsusCorePlugin.getDefault().getLog().log( cex.getStatus() );
        }
    }
}
