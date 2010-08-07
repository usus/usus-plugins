package org.projectusus.core.internal.proportions.rawdata.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.internal.proportions.rawdata.ClassRepresenter;

public class ClassRepresenterTest {

    @Test
    public void childrenChangedInUnderlyingClassDescriptor() {
        ClassDescriptor child1 = mock( ClassDescriptor.class );
        ClassDescriptor child2 = mock( ClassDescriptor.class );
        Set<ClassDescriptor> children = new HashSet<ClassDescriptor>();
        children.add( child1 );
        children.add( child2 );

        ClassDescriptor clazz = mock( ClassDescriptor.class );
        ClassRepresenter representer = new ClassRepresenter( clazz );
        when( clazz.getChildren() ).thenReturn( children );

        assertEquals( 2, clazz.getChildren().size() );
        assertEquals( 2, representer.getChildren().size() );
        children.remove( child2 );
        assertEquals( 1, clazz.getChildren().size() );
        assertEquals( 1, representer.getChildren().size() );
    }
}
