// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.projectusus.core.internal.coverage.TestCoverage;

import com.mountainminds.eclemma.core.analysis.IJavaElementCoverage;

class ProjectRawData extends RawData<IFile, FileRawData> {

    // TODO wird nicht gebraucht:
    private final IProject projectOfRawData;
    private IJavaElementCoverage coverage;

    public ProjectRawData( IProject project ) {
        this.projectOfRawData = project;
    }

    public IProject getProjectOfRawData() {
        return projectOfRawData;
    }

    public FileRawData getFileRawData( IFile file ) {
        FileRawData rawData = super.getRawData( file );
        if( rawData == null ) {
            rawData = new FileRawData( file );
            super.addRawData( file, rawData );
        }
        return rawData;
    }

    public void dropRawData( IFile file ) {
        remove( file );
    }

    public void setInstructionCoverage( IJavaElementCoverage coverage ) {
        this.coverage = coverage;
    }

    public TestCoverage getInstructionCoverage() {
        if( coverage != null ) {
            return new TestCoverage( coverage.getInstructionCounter() );
        }
        return new TestCoverage( 0, 0 );
    }

}
