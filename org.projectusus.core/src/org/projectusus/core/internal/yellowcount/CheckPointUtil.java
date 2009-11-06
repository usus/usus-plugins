// Copyright (c) 2005-2006, 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.yellowcount;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.core.runtime.IPath;
import org.projectusus.core.internal.UsusCorePlugin;

/**
 * <p>
 * provides functionality to manage checkpoints.
 * </p>
 * 
 * @author Leif Frenzel
 */
public class CheckPointUtil {

    private static final String LOG_FILE_NAME = "checkpoints.log"; //$NON-NLS-1$

    public static void addCheckpoint( final String content ) {
        try {
            FileWriter fw = new FileWriter( getCheckpointsLog(), true );
            fw.write( content );
            fw.close();
        } catch( IOException ioex ) {
            UsusCorePlugin.log( ioex );
        }
    }

    public static void deleteLog() {
        getCheckpointsLog().delete();
    }

    public static File getCheckpointsLog() {
        IPath stateLoc = UsusCorePlugin.getDefault().getStateLocation();
        return new File( stateLoc.toOSString() + File.separator + LOG_FILE_NAME );
    }
}
