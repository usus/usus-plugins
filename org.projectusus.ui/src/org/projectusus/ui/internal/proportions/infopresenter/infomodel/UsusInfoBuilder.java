// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.infopresenter.infomodel;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.projectusus.core.internal.bugreport.BugList;
import org.projectusus.core.internal.project.IUSUSProject;
import org.projectusus.core.internal.proportions.rawdata.IClassRawData;
import org.projectusus.core.internal.proportions.rawdata.IFileRawData;
import org.projectusus.core.internal.proportions.rawdata.IMethodRawData;
import org.projectusus.ui.internal.selection.ExtractFileRawData;

public class UsusInfoBuilder {

    private final IMethod method;

    public UsusInfoBuilder( IMethod method ) {
        this.method = method;
    }

    public IUsusInfo create() {
        return method == null ? null : buildUsusInfo();
    }

    private IUsusInfo buildUsusInfo() {
        IUsusInfo result = new UnavailableUsusInfo( method );
        try {
            IResource resource = method.getUnderlyingResource();
            ExtractFileRawData extractor = new ExtractFileRawData( resource );
            if( extractor.isDataAvailable() ) {
                IFileRawData fileRawData = extractor.getFileRawData();
                if( rawDataAvailable( fileRawData ) ) {
                    IClassRawData classRawData = fileRawData.getRawData( method.getDeclaringType() );
                    IMethodRawData methodRawData = findMethodRawData( classRawData );
                    result = new UsusInfo( method, methodRawData, classRawData, findBugInfo( method ) );
                }
            }
        } catch( JavaModelException jamox ) {
            // ignore
        }
        return result;
    }

    private boolean rawDataAvailable( IFileRawData fileRawData ) {
        IClassRawData classRawData = fileRawData.getRawData( method.getDeclaringType() );
        return classRawData != null && findMethodRawData( classRawData ) != null;
    }

    private IMethodRawData findMethodRawData( IClassRawData classRawData ) {
        return classRawData.getMethodRawData( method );
    }

    private BugList findBugInfo( IMethod method ) throws JavaModelException {
        BugList result = new BugList();
        IProject project = method.getUnderlyingResource().getProject();
        IUSUSProject ususProject = (IUSUSProject)project.getAdapter( IUSUSProject.class );
        if( ususProject != null ) {
            result = ususProject.getBugsFor( method );
        }
        return result;
    }
}
