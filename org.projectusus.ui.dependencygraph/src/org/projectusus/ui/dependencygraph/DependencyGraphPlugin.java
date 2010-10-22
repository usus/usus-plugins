package org.projectusus.ui.dependencygraph;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.projectusus.core.util.Defect;

public class DependencyGraphPlugin extends AbstractUIPlugin {

    public static final String PLUGIN_ID = "org.projectusus.ui.dependencygraph";
    private static DependencyGraphPlugin plugin;

    @Override
    public void start( BundleContext context ) throws Exception {
        super.start( context );
        plugin = this;
    }

    @Override
    public void stop( BundleContext context ) throws Exception {
        plugin = null;
        super.stop( context );
    }

    public ImageDescriptor imageForPath( String path ) {
        ImageRegistry registry = getImageRegistry();
        ImageDescriptor image = registry.getDescriptor( path );
        if( image == null ) {
            URL url = urlForPath( path );
            image = ImageDescriptor.createFromURL( url );
            registry.put( path, image );
        }
        return image;
    }

    private URL urlForPath( String path ) {
        try {
            return new URL( getBundle().getEntry( "/" ), path );
        } catch( MalformedURLException exception ) {
            throw new Defect( exception );
        }
    }

    public static DependencyGraphPlugin plugin() {
        return plugin;
    }

}
