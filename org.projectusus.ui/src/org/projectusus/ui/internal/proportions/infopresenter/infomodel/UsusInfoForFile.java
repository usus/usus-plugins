// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.infopresenter.infomodel;

import static org.projectusus.core.basis.CodeProportionKind.CW;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.JavaModelException;
import org.projectusus.core.internal.UsusCorePlugin;

class UsusInfoForFile implements IUsusInfo {

    private final IFile file;

    UsusInfoForFile( IResource resource ) {
        super();
        this.file = (IFile)resource;
    }

    public String[] getCodeProportionInfos() {
        try {
            List<String> result = new ArrayList<String>();
            addFormattedProportion( result );
            return result.toArray( new String[0] );
        } catch( JavaModelException jmox ) {
            return new String[] { "Error in calculating metrics values." };
        }
    }

    @SuppressWarnings( "unused" )
    protected void addFormattedProportion( List<String> result ) throws JavaModelException {
        // dummy for subclasses
    }

    public String[] getTestCoverageInfos() {
        return new String[] { "Not yet available" };
    }

    public String[] getWarningInfos() {
        try {
            List<String> result = new ArrayList<String>();
            StringBuilder sb = new StringBuilder();
            sb.append( CW.getLabel() );
            sb.append( " (in file) : " );
            sb.append( String.valueOf( UsusCorePlugin.getUsusModel().getNumberOfWarnings( file ) ) );
            result.add( sb.toString() );
            return result.toArray( new String[0] );
        } catch( JavaModelException jmox ) {
            return new String[] { "Error retrieving information." };
        }
    }

    public String formatTitle() {
        return file.getName();
    }
}
