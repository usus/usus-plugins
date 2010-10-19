package org.projectusus.core;

import static org.eclipse.core.runtime.Platform.getExtensionRegistry;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

public class ExtensionCollector<T extends CollectibleExtension> {

    private static final String CLASS = "class"; //$NON-NLS-1$
    private final String extensionPointId;

    public ExtensionCollector( String extensionPointId ) {
        this.extensionPointId = extensionPointId;
    }

    public Set<T> allExtensions() {
        Set<T> extensions = new HashSet<T>();
        for( IConfigurationElement metricsElement : getExtensionRegistry().getConfigurationElementsFor( extensionPointId ) ) {
            T extension = createExecutableExtension( metricsElement );
            if( extension != null ) {
                extensions.add( extension );
            }
        }
        return extensions;
    }

    @SuppressWarnings( "unchecked" )
    private T createExecutableExtension( IConfigurationElement metricsElement ) {
        try {
            return (T)metricsElement.createExecutableExtension( CLASS );
        } catch( CoreException exception ) {
            UsusCorePlugin.log( exception );
            return null;
        } catch( ClassCastException cce ) {
            return null;
        }
    }

}
