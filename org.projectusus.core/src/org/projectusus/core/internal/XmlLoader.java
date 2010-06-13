// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public abstract class XmlLoader<T> {

    protected void safeClose( Reader reader ) {
        if( reader != null ) {
            try {
                reader.close();
            } catch( IOException ioex ) {
                // we can't help it then
            }
        }
    }

    public List<T> load() {
        List<T> result = new ArrayList<T>();
        Reader reader = null;
        try {
            if( new File( getFileName() ).exists() ) {
                reader = loadElements( result );
            }
        } catch( Exception ex ) {
            result.clear();
            // not directly displayed to the user, but into the error log
            String msg = "Unable to load file."; //$NON-NLS-1$
            UsusCorePlugin.log( msg, ex );
        } finally {
            safeClose( reader );
        }
        return result;
    }

    protected Reader loadElements( List<T> result ) throws Exception {
        Reader reader = new InputStreamReader( new FileInputStream( getFileName() ) );
        DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Element rootElement = parser.parse( new InputSource( reader ) ).getDocumentElement();
        read( result, rootElement );
        return reader;
    }

    protected abstract void read( List<T> result, Element rootElement );

    protected abstract String getFileName();

}
