package org.projectusus.core.internal.proportions.rawdata.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.core.resources.IFile;
import org.junit.Test;
import org.projectusus.core.UsusModelProvider;
import org.projectusus.core.filerelations.model.BoundType;
import org.projectusus.core.filerelations.model.Classname;
import org.projectusus.core.filerelations.model.Packagename;

public class MetricsAccessorTest {

    @Test
    public void addFileRelation() {
        BoundType sourceBinding = mock( BoundType.class );
        BoundType targetBinding = mock( BoundType.class );
        when( sourceBinding.getClassname() ).thenReturn( new Classname( "sourceName" ) ); //$NON-NLS-1$
        when( targetBinding.getClassname() ).thenReturn( new Classname( "targetName" ) ); //$NON-NLS-1$
        Packagename packagename = Packagename.of( "package", null ); //$NON-NLS-1$
        when( sourceBinding.getPackagename() ).thenReturn( packagename );
        when( targetBinding.getPackagename() ).thenReturn( packagename );
        IFile file = mock( IFile.class );
        when( sourceBinding.getUnderlyingResource() ).thenReturn( file );
        when( targetBinding.getUnderlyingResource() ).thenReturn( file );

        UsusModelProvider.addClassReference( sourceBinding, targetBinding );

        // TODO und was jetzt?? Was wollen wir hier testen?
    }
}
