package org.projectusus.metrics.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.core.resources.IFile;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.Classname;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.filerelations.model.WrappedTypeBinding;
import org.projectusus.core.statistics.UsusModelProvider;
import org.projectusus.metrics.ClassRelationsCollector;
import org.projectusus.metrics.util.Setup;

/**
 * Tests the step from the action method of ACDCollector to the structure of the ClassDescriptor lattice with the connections from each node to its direct children.
 * 
 * The next test step would be from this lattice to the CCD for each ClassDescriptor; this is performed in {@link org.projectusus.core.filereleations.test.CCDTest}
 * 
 * @author rauch
 * 
 */
public class ClassRelationsCollectorTest {

    private static final String NAME_1 = "Name1";
    private static final String NAME_2 = "Name2";
    private static final String XYZ = "xyz";
    // static because we need to use the same file for all types for comparability
    private static final IFile file = mock( IFile.class );

    // not static because they need to be fresh every time!
    private final WrappedTypeBinding TYPE_1 = createType( NAME_1 );
    private final WrappedTypeBinding TYPE_2 = createType( NAME_2 );

    private ClassRelationsCollector collector;

    @Before
    public void setup() {
        UsusModelProvider.clear();
        collector = new ClassRelationsCollector();
        Setup.setupCollector( collector );
    }

    @Test
    public void selfConnectionIsIgnored() {

        collector.connectTypes( TYPE_1, TYPE_1 );

        ClassDescriptor classDescriptor1 = findClassDescriptorWithName( NAME_1 );
        ClassDescriptor classDescriptor2 = findClassDescriptorWithName( NAME_2 );

        assertThat( classDescriptor1.getChildren(), is( empty() ) );
        assertThat( classDescriptor2, is( nullValue() ) );
    }

    @Test
    public void unidirectionalConnectionOfTwoTypesYieldsThatOneIsChildOfTheOther() {

        collector.connectTypes( TYPE_1, TYPE_2 );

        ClassDescriptor classDescriptor1 = findClassDescriptorWithName( NAME_1 );
        ClassDescriptor classDescriptor2 = findClassDescriptorWithName( NAME_2 );

        assertThat( classDescriptor1.getChildren(), contains( classDescriptor2 ) );
        assertThat( classDescriptor2.getChildren(), is( empty() ) );
    }

    @Test
    public void bidirectionalConnectionOfTwoTypesYieldsThatTypesAreChildrenOfEachOther() {

        collector.connectTypes( TYPE_1, TYPE_2 );
        collector.connectTypes( TYPE_2, TYPE_1 );

        ClassDescriptor classDescriptor1 = findClassDescriptorWithName( NAME_1 );
        ClassDescriptor classDescriptor2 = findClassDescriptorWithName( NAME_2 );

        assertThat( classDescriptor1.getChildren(), contains( classDescriptor2 ) );
        assertThat( classDescriptor2.getChildren(), contains( classDescriptor1 ) );
    }

    private ClassDescriptor findClassDescriptorWithName( String name ) {
        for( ClassDescriptor classDescriptor : ClassDescriptor.getAll() ) {
            if( classDescriptor.getClassname().toString().equals( name ) ) {
                return classDescriptor;
            }
        }
        return null;
    }

    private static WrappedTypeBinding createType( String classname ) {
        WrappedTypeBinding aType = mock( WrappedTypeBinding.class );
        when( aType.getClassname() ).thenReturn( new Classname( classname ) );
        when( aType.getPackagename() ).thenReturn( Packagename.of( XYZ, null ) );
        when( aType.getUnderlyingResource() ).thenReturn( file );
        return aType;
    }
}
