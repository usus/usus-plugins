package org.projectusus.core.internal.proportions.rawdata;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.core.resources.IFile;
import org.junit.Test;
import org.projectusus.core.filerelations.FileRelationMetrics;
import org.projectusus.core.filerelations.model.BoundType;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.Classname;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.testutil.ReflectionUtil;

public class MetricsAccessorTest {

    @Test
    public void addFileRelation() throws Exception {
        BoundType sourceBinding = mock( BoundType.class );
        BoundType targetBinding = mock( BoundType.class );
        when( sourceBinding.getClassname() ).thenReturn( new Classname( "sourceName" ) ); //$NON-NLS-1$
        when( targetBinding.getClassname() ).thenReturn( new Classname( "targetName" ) ); //$NON-NLS-1$
        Packagename packagename = Packagename.of( "package" ); //$NON-NLS-1$
        when( sourceBinding.getPackagename() ).thenReturn( packagename );
        when( targetBinding.getPackagename() ).thenReturn( packagename );
        IFile file = mock( IFile.class );
        when( sourceBinding.getUnderlyingResource() ).thenReturn( file );
        when( targetBinding.getUnderlyingResource() ).thenReturn( file );
        MetricsAccessor model = new MetricsAccessor();
        FileRelationMetrics relations = mock( FileRelationMetrics.class );
        ReflectionUtil.setValue( model, relations, "fileRelationMetrics" ); //$NON-NLS-1$

        model.addClassReference( sourceBinding, targetBinding );

        verify( relations ).addFileRelation( any( ClassDescriptor.class ), any( ClassDescriptor.class ) );
    }
}
