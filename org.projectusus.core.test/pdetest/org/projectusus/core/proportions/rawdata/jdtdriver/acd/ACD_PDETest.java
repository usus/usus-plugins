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
        assertThat( map.get( "acd/Acd2" ), is( empty() ) );
        assertThat( map.get( "acd/EmptyClass" ), is( empty() ) );
        assertThat( map.get( "acd/Acd3Helper" ), contains( "acd/EmptyClass", "acd/EmptyClass" ) );
        assertThat( map.get( "acd/Acd4" ), contains( "acd/Acd4Helper", "acd/Acd4Helper" ) );
        assertThat( map.get( "acd/Acd4Helper" ), contains( "acd/Acd4", "acd/Acd4" ) );
        assertThat( map.get( "acd/Acd5" ), is( empty() ) );
        assertThat( map.get( "acd/Acd5Helper" ), contains( "acd/Acd5", "acd/Acd5" ) );
        assertThat( map.get( "acd/Acd6" ), is( empty() ) );
        assertThat( map.get( "acd/Acd6Helper" ), contains( "acd/Acd6", "acd/Acd6" ) );
        assertThat( map.get( "acd/Acd7Helper" ), contains( "acd/Acd", "acd/Acd" ) );
        assertThat( map.get( "acd/Acd8" ), contains( "acd/Helper1", "acd/Helper1", "acd/Helper2", "acd/Helper2" ) );
        assertThat( map.get( "acd/Helper1" ), contains( "acd/Helper3", "acd/Helper3", "acd/Helper4", "acd/Helper4" ) );
        assertThat( map.get( "acd/Helper2" ), contains( "acd/Helper5", "acd/Helper5", "acd/Helper6", "acd/Helper6" ) );
        assertThat( map.get( "acd/Helper3" ), is( empty() ) );
        assertThat( map.get( "acd/Helper4" ), is( empty() ) );
        assertThat( map.get( "acd/Helper5" ), is( empty() ) );
        assertThat( map.get( "acd/Helper6" ), is( empty() ) );
        assertThat( map.get( "acd/Acd9" ), contains( "acd/UnloadedClass", "acd/UnloadedClass" ) );
        assertThat( map.get( "acd/Acd10" ), contains( "acd2/UnloadedClass2", "acd2/UnloadedClass2" ) );
        assertThat( map.get( "acd/Intf" ), is( empty() ) );
        assertThat( map.get( "acd/Intf2" ), contains( "acd/Acd", "acd/Acd" ) );
        assertThat( map.get( "acd/Acd11" ), contains( "acd/Intf", "acd/Intf" ) );
        assertThat( map.get( "acd/Acd12" ), contains( "acd/Acd", "acd/Acd" ) );
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
