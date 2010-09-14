package org.projectusus.core.metrics;

import static org.eclipse.core.runtime.Platform.getExtensionRegistry;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.projectusus.core.UsusCorePlugin;

public class RegisteredMetricsExtensions {

    private static final String METRICS_ID = "org.projectusus.core.metrics"; //$NON-NLS-1$
    private static final String CLASS = "class"; //$NON-NLS-1$

    public static Set<MetricsCollector> allExtensions() {
        Set<MetricsCollector> extensions = new HashSet<MetricsCollector>();
        for( IConfigurationElement metricsElement : getExtensionRegistry().getConfigurationElementsFor( METRICS_ID ) ) {
            Object extension = createExecutableExtension( metricsElement );
            if( extension instanceof MetricsCollector ) {
                extensions.add( (MetricsCollector)extension );
            }
        }
        return extensions;
    }

    private static Object createExecutableExtension( IConfigurationElement metricsElement ) {
        try {
            return metricsElement.createExecutableExtension( CLASS );
        } catch( CoreException exception ) {
            UsusCorePlugin.log( exception );
            return null;
        }
    }

}
