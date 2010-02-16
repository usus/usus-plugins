// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.nasty.ui.internal.preferences;

import static org.eclipse.ui.plugin.AbstractUIPlugin.imageDescriptorFromPlugin;
import static org.projectusus.nasty.ui.internal.NastyUsus.getPluginId;
import static org.projectusus.nasty.ui.internal.NastyUsusPreferenceKey.NASTY_MODE;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class NiceNastyFieldEditor extends BooleanFieldEditor {

    private Image imgNice;
    private Image imgNasty;

    public NiceNastyFieldEditor( Composite parent ) {
        super( NASTY_MODE.name(), "Nice or nasty", SEPARATE_LABEL, parent );
        loadImages();
    }

    @Override
    protected void doLoad() {
        super.doLoad();
        updateUI();
    }
    
    @Override
    protected void doLoadDefault() {
        super.doLoadDefault();
        updateUI();
    }
    @Override
    protected void valueChanged( boolean oldValue, boolean newValue ) {
        super.valueChanged( oldValue, newValue );
        updateUI();
    }
    
    @Override
    public void dispose() {
        if( imgNice != null ) {
            imgNice.dispose();
        }
        if( imgNasty != null ) {
            imgNasty.dispose();
        }
        super.dispose();
    }

    private void updateUI() {
        if( getBooleanValue() ) {
            updateUI( imgNasty, "Nasty" );
        } else {
            updateUI( imgNice, "Nice" );
        }
    }

    private void updateUI( Image image, String text ) {
        getLabelControl().setImage( image );
        Composite parent = getLabelControl().getParent();
        Button changeControl = getChangeControl( parent );
        changeControl.setText( text );
        parent.layout();
    }

    private void loadImages() {
        imgNasty = loadImage( "nasty.png" );
        imgNice = loadImage( "nice.png" );
    }

    private Image loadImage( String name ) {
        String imageFilePath = "icons/prefban/" + name;
        ImageDescriptor desc = imageDescriptorFromPlugin( getPluginId(), imageFilePath );
        return desc.createImage();
    }
}
