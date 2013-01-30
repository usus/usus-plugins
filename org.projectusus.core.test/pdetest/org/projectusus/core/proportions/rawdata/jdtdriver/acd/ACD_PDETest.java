package org.projectusus.core.proportions.rawdata.jdtdriver.acd;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.eclipse.core.resources.IFile;
import org.junit.Test;
import org.projectusus.core.proportions.rawdata.jdtdriver.JavaFileDriver;
import org.projectusus.core.statistics.test.PDETestForMetricsComputation;

import com.google.common.collect.Multimap;

public class ACD_PDETest extends PDETestForMetricsComputation {

    @Test
    public void assumptionsAreValid() throws Exception {
        project.createFolder( "acd" );
        project.createFolder( "acd2" );
        IFile file = createJavaFile( "acd/Acd.java" );
        IFile staticImport = createJavaFile( "acd2/Acd_staticImport.java" );
        IFile chain5 = createJavaFile( "acd/Chain5.java" );
        IFile field5 = createJavaFile( "acd/Field5.java" );

        createJavaFile( "acd/UnloadedClass.java" );
        IFile unloadedFile2 = createJavaFile( "acd2/UnloadedClass2.java" );

        ACDInspector inspector = new ACDInspector();
        new JavaFileDriver( file ).compute( createSetWith( inspector ) );
        new JavaFileDriver( staticImport ).compute( createSetWith( inspector ) );
        new JavaFileDriver( chain5 ).compute( createSetWith( inspector ) );
        new JavaFileDriver( field5 ).compute( createSetWith( inspector ) );

        Multimap<String, String> map = inspector.getTypeConnections();

        assertThat( map.get( "acd/Acd" ), is( empty() ) );
        assertThat( map.get( "acd/ClassWithStrings" ), is( empty() ) );
        assertThat( map.get( "acd/EmptyClass" ), is( empty() ) );
        assertThat( map.get( "acd/ToEmptyClass" ), contains( "acd/EmptyClass", "acd/EmptyClass" ) );
        assertThat( map.get( "acd/ClassWithGeneric" ), is( empty() ) );
        assertThat( map.get( "acd/ToClassWithGeneric" ), contains( "acd/ClassWithGeneric", "acd/ClassWithGeneric" ) );
        assertThat( map.get( "acd/ToClassAsGenericArgument" ), contains( "acd/Acd", "acd/Acd" ) );
        assertThat( map.get( "acd/ToTwoClasses" ), contains( "acd/EmptyClass", "acd/EmptyClass", "acd/Acd", "acd/Acd" ) );
        assertThat( map.get( "acd/ToUnloadedClass" ), contains( "acd/UnloadedClass", "acd/UnloadedClass" ) );
        assertThat( map.get( "acd/ToUnloadedClassInDifferentPackage" ), contains( "acd2/UnloadedClass2", "acd2/UnloadedClass2" ) );
        assertThat( map.get( "acd/EmptyInterface" ), is( empty() ) );
        assertThat( map.get( "acd/InterfaceToClass" ), contains( "acd/Acd", "acd/Acd" ) );
        assertThat( map.get( "acd/ImplementingClass" ), contains( "acd/EmptyInterface", "acd/EmptyInterface" ) );
        assertThat( map.get( "acd/ExtendingClass" ), contains( "acd/Acd", "acd/Acd" ) );
        assertThat( map.get( "acd/Acd_static" ), contains( "acd/Acd" ) );
        assertThat( map.get( "acd2/Acd_staticImport" ), contains( "acd/Acd" ) );
        assertThat( map.get( "acd/Chain1" ), is( empty() ) );
        assertThat( map.get( "acd/Chain2" ), contains( "acd/Chain1" ) );
        assertThat( map.get( "acd/Chain3" ), is( empty() ) );
        assertThat( map.get( "acd/Chain4" ), contains( "acd/Chain3", "acd/Chain3" ) );
        // assertThat( map.get( "acd/Chain5" ), contains( "acd/Chain1" ) );
        assertThat( map.get( "acd/Field1" ), is( empty() ) );
        // assertThat( map.get( "acd/Field2" ), contains( "acd/Field1" ) );
        assertThat( map.get( "acd/Field3" ), is( empty() ) );
        // assertThat( map.get( "acd/Field4" ), contains( "acd/Field3", "acd/Field3" ) );
        // assertThat( map.get( "acd/Field5" ), contains( "acd/Field1" ) );

        // das gehört nicht hier hin!
        // Stattdessen sicherstellen, dass beim Anlegen und Löschen die entsprechende Neuberechnung auch stattfindet!
        project.delete( unloadedFile2 );
        inspector = new ACDInspector();
        new JavaFileDriver( file ).compute( createSetWith( inspector ) );
        map = inspector.getTypeConnections();
        assertThat( map.get( "acd/Acd10" ), is( empty() ) );

    }
}
