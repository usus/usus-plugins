package org.projectusus.core.internal.proportions.rawdata.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.core.resources.IFile;
import org.junit.Test;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.Classname;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.filerelations.model.WrappedTypeBinding;

public class MetricsAccessorTest {

    @Test
    public void addFileRelation() {
        WrappedTypeBinding sourceBinding = mock( WrappedTypeBinding.class );
        WrappedTypeBinding targetBinding = mock( WrappedTypeBinding.class );
        when( sourceBinding.getClassname() ).thenReturn( new Classname( "sourceName", null ) ); //$NON-NLS-1$
        when( targetBinding.getClassname() ).thenReturn( new Classname( "targetName", null ) ); //$NON-NLS-1$
        Packagename packagename = Packagename.of( "package", null ); //$NON-NLS-1$
        when( sourceBinding.getPackagename() ).thenReturn( packagename );
        when( targetBinding.getPackagename() ).thenReturn( packagename );
        IFile file = mock( IFile.class );
        when( sourceBinding.getUnderlyingResource() ).thenReturn( file );
        when( targetBinding.getUnderlyingResource() ).thenReturn( file );

        ClassDescriptor.of( sourceBinding ).addChild( ClassDescriptor.of( targetBinding ) );

        // TODO und was jetzt?? Was wollen wir hier testen?
    }
}
