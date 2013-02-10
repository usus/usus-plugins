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
        IFile methodChainStaticImport = createJavaFile( "acd/MethodChainStaticImport.java" );
        IFile fieldChainStaticImport = createJavaFile( "acd/FieldChainStaticImport.java" );
        IFile fieldChainPublic = createJavaFile( "acd/FieldChainPublic.java" );
        IFile methodChainPublic = createJavaFile( "acd/MethodChainPublic.java" );
        IFile imports = createJavaFile( "acd2/Imports.java" );

        createJavaFile( "acd/UnloadedClass.java" );
        IFile unloadedFile2 = createJavaFile( "acd2/UnloadedClass2.java" );

        ACDInspector inspector = new ACDInspector();
        new JavaFileDriver( file ).compute( createSetWith( inspector ) );
        new JavaFileDriver( staticImport ).compute( createSetWith( inspector ) );
        new JavaFileDriver( methodChainStaticImport ).compute( createSetWith( inspector ) );
        new JavaFileDriver( fieldChainStaticImport ).compute( createSetWith( inspector ) );
        new JavaFileDriver( fieldChainPublic ).compute( createSetWith( inspector ) );
        new JavaFileDriver( methodChainPublic ).compute( createSetWith( inspector ) );
        new JavaFileDriver( imports ).compute( createSetWith( inspector ) );

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
        assertThat( map.get( "acd/InvokingStaticMethod" ), contains( "acd/Acd" ) );

        assertThat( map.get( "acd/MethodChain" ), is( empty() ) );
        assertThat( map.get( "acd/MethodChain_StaticNonstatic" ), contains( "acd/MethodChain" ) );
        assertThat( map.get( "acd/MethodChain_NonstaticStatic" ), contains( "acd/MethodChain", "acd/MethodChain" ) );
        assertThat( map.get( "acd/FieldChain" ), is( empty() ) );
        assertThat( map.get( "acd/FieldChain_StaticNonstatic" ), contains( "acd/FieldChain", "acd/FieldChain", "acd/FieldChain" ) );
        assertThat( map.get( "acd/FieldChain_NonstaticStatic" ), contains( "acd/FieldChain", "acd/FieldChain", "acd/FieldChain", "acd/FieldChain" ) );

        assertThat( map.get( "acd/ClassWithArrayReferenceInField" ), contains( "acd/EmptyClass", "acd/EmptyClass" ) );
        assertThat( map.get( "acd/ClassWithArrayReferenceInLocalVariable" ), contains( "acd/EmptyClass", "acd/EmptyClass" ) );
        assertThat( map.get( "acd/ClassWithArrayReferenceInMethodParameter" ), contains( "acd/EmptyClass", "acd/EmptyClass" ) );
        assertThat( map.get( "acd/ClassWithArrayReferenceInMethodReturnType" ), contains( "acd/EmptyClass", "acd/EmptyClass" ) );

        assertThat( map.get( "acd/ClassWithInnerBeforeAttribute" ), contains( "acd/EmptyClass", "acd/EmptyClass" ) );
        assertThat( map.get( "acd/InnerClassBeforeAttribute" ), contains( "acd/EmptyClass", "acd/EmptyClass" ) );
        assertThat( map.get( "acd/ClassWithInnerAfterAttribute" ), contains( "acd/EmptyClass", "acd/EmptyClass" ) );
        assertThat( map.get( "acd/InnerClassAfterAttribute" ), contains( "acd/EmptyClass", "acd/EmptyClass" ) );

        // the following tests use more than one file
        assertThat( map.get( "acd2/Acd_staticImport" ), contains( "acd/Acd" ) );

        assertThat( map.get( "acd/MethodChainPublic" ), is( empty() ) );
        assertThat( map.get( "acd/MethodChainStaticImport" ), is( empty() ) ); // eclipse bug!
        assertThat( map.get( "acd/MethodChainStaticImportPublic" ), contains( "acd/MethodChainPublic" ) );

        assertThat( map.get( "acd/FieldChainPublic" ), is( empty() ) );
        assertThat( map.get( "acd/FieldChainStaticImport" ), is( empty() ) ); // eclipse bug!
        assertThat( map.get( "acd/FieldChainStaticImportPublic" ), contains( "acd/FieldChainPublic", "acd/FieldChainPublic" ) );
        assertThat( map.get( "acd2/Imports" ), is( empty() ) );

        // das gehört nicht hier hin!
        // Stattdessen sicherstellen, dass beim Anlegen und Löschen die entsprechende Neuberechnung auch stattfindet!
        project.delete( unloadedFile2 );
        inspector = new ACDInspector();
        new JavaFileDriver( file ).compute( createSetWith( inspector ) );
        map = inspector.getTypeConnections();
        assertThat( map.get( "acd/Acd10" ), is( empty() ) );

    }
}
