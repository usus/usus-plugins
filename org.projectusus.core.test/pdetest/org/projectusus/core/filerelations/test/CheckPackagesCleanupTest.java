package org.projectusus.core.filerelations.test;

import static org.junit.Assert.assertEquals;

import org.eclipse.core.runtime.CoreException;
import org.junit.Test;
import org.projectusus.adapter.ForcedRecompute;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.internal.proportions.rawdata.PDETestForMetricsComputation;

public class CheckPackagesCleanupTest extends PDETestForMetricsComputation {

    private void createWSFolders() throws CoreException {
        createWSFolder( "mine", project1 );
        createWSFolder( "yours", project2 );
    }

    @Test
    public void twoFilesInTwoProjects() throws Exception {
        createWSFolders();
        createWSFile( "mine/Acd_GameStateAI.java", loadContent( "Acd_GameStateAI.test" ), project1 );
        buildFullyAndWait();
        assertEquals( 1, Packagename.getAll().size() );
        createWSFile( "yours/Acd_LRUCache.java", loadContent( "Acd_LRUCache.test" ), project2 );
        buildIncrementallyAndWait();
        new ForcedRecompute().schedule();
        Thread.sleep( 1000 );
        assertEquals( 2, Packagename.getAll().size() );
        makeUsusProject( false, project2 );
        buildFullyAndWait();
        assertEquals( 1, Packagename.getAll().size() );
    }

}
