package org.projectusus.ui.dependencygraph.nodes;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.eclipse.jdt.core.IPackageFragment;
import org.junit.Test;
import org.projectusus.core.filerelations.model.Packagename;

public class PackageRepresenterTest {

    @Test
    public void isAdaptableToGraphNode() {
        PackageRepresenter representer = new PackageRepresenter( Packagename.of( "a", mock( IPackageFragment.class ) ) );
        Object adapter = representer.getAdapter( GraphNode.class );
        assertThat( adapter, notNullValue() );
    }
}
