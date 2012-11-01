package org.projectusus.core.internal.proportions.rawdata;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.projectusus.c4j.UsusContractBase;
import org.projectusus.core.IMetricsResultVisitor;

public class MetricsAccessorContract extends UsusContractBase<MetricsAccessor> {

    public MetricsAccessorContract( MetricsAccessor target ) {
        super( target );
    }

    public void classInvariant() {
        // TODO no class invariant identified yet
    }

    public void pre_MetricsAccessor() {
        // TODO no pre-condition identified yet
    }

    public void post_MetricsAccessor() {
        // TODO no post-condition identified yet
    }

    public void pre_acceptAndGuide( IMetricsResultVisitor visitor ) {
        // TODO Auto-generated pre-condition
        assertThat( visitor != null, "visitor_not_null" );
    }

    public void post_acceptAndGuide( IMetricsResultVisitor visitor ) {
        // TODO no post-condition identified yet
    }

    public void pre_dropRawData( IProject project ) {
        // TODO Auto-generated pre-condition
        assertThat( project != null, "project_not_null" );
    }

    public void post_dropRawData( IProject project ) {
        // TODO no post-condition identified yet
    }

    public void pre_dropRawData( IFile file ) {
        // TODO Auto-generated pre-condition
        assertThat( file != null, "file_not_null" );
    }

    public void post_dropRawData( IFile file ) {
        // TODO no post-condition identified yet
    }

    public void pre_cleanupRelations( IProgressMonitor monitor ) {
        // TODO Auto-generated pre-condition
        assertThat( monitor != null, "monitor_not_null" );
    }

    public void post_cleanupRelations( IProgressMonitor monitor ) {
        // TODO no post-condition identified yet
    }

}
