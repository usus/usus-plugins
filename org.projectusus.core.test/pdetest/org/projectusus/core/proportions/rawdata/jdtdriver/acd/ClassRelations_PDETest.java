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

public class ClassRelations_PDETest extends PDETestForMetricsComputation {

    @Test
    public void assumptionsAreValid() throws Exception {
        project.createFolder( "cr" );
        project.createFolder( "cr2" );
        IFile file = createJavaFile( "cr/Acd.java" );
        IFile staticImport = createJavaFile( "cr2/Acd_staticImport.java" );
        IFile methodStaticImport = createJavaFile( "cr/MethodStaticImport.java" );
        IFile fieldStaticImport = createJavaFile( "cr/FieldStaticImport.java" );
        IFile methodChainStaticImport = createJavaFile( "cr/MethodChainStaticImport.java" );
        IFile fieldChainStaticImport = createJavaFile( "cr/FieldChainStaticImport.java" );
        IFile fieldChainPublic = createJavaFile( "cr/FieldChainPublic.java" );
        IFile methodChainPublic = createJavaFile( "cr/MethodChainPublic.java" );
        IFile imports = createJavaFile( "cr2/Imports.java" );
        IFile staticImportInParam = createJavaFile( "cr2/ClassWithStaticImportInMethodParameter.java" );

        createJavaFile( "cr/UnloadedClass.java" );
        IFile unloadedFile2 = createJavaFile( "cr2/UnloadedClass2.java" );

        ACDInspector inspector = new ACDInspector();
        new JavaFileDriver( file ).compute( createSetWith( inspector ) );
        new JavaFileDriver( staticImport ).compute( createSetWith( inspector ) );
        new JavaFileDriver( methodChainPublic ).compute( createSetWith( inspector ) );
        new JavaFileDriver( methodStaticImport ).compute( createSetWith( inspector ) );
        new JavaFileDriver( methodChainStaticImport ).compute( createSetWith( inspector ) );
        new JavaFileDriver( fieldChainPublic ).compute( createSetWith( inspector ) );
        new JavaFileDriver( fieldStaticImport ).compute( createSetWith( inspector ) );
        new JavaFileDriver( fieldChainStaticImport ).compute( createSetWith( inspector ) );
        new JavaFileDriver( imports ).compute( createSetWith( inspector ) );
        new JavaFileDriver( staticImportInParam ).compute( createSetWith( inspector ) );

        Multimap<String, String> map = inspector.getTypeConnections();

        assertThat( map.get( "cr/Acd" ), is( empty() ) );
        assertThat( map.get( "cr/ClassWithStrings" ), is( empty() ) );
        assertThat( map.get( "cr/EmptyClass" ), is( empty() ) );
        assertThat( map.get( "cr/ToEmptyClass" ), contains( "cr/EmptyClass", "cr/EmptyClass" ) );
        assertThat( map.get( "cr/ClassWithGeneric" ), is( empty() ) );
        assertThat( map.get( "cr/ToClassWithGeneric" ), contains( "cr/ClassWithGeneric", "cr/ClassWithGeneric" ) );
        assertThat( map.get( "cr/ToClassAsGenericArgument" ), contains( "cr/Acd", "cr/Acd" ) );
        assertThat( map.get( "cr/ToTwoClasses" ), contains( "cr/EmptyClass", "cr/EmptyClass", "cr/Acd", "cr/Acd" ) );
        assertThat( map.get( "cr/ToUnloadedClass" ), contains( "cr/UnloadedClass", "cr/UnloadedClass" ) );
        assertThat( map.get( "cr/ToUnloadedClassInDifferentPackage" ), contains( "cr2/UnloadedClass2", "cr2/UnloadedClass2" ) );
        assertThat( map.get( "cr/EmptyInterface" ), is( empty() ) );
        assertThat( map.get( "cr/InterfaceToClass" ), contains( "cr/Acd", "cr/Acd" ) );
        assertThat( map.get( "cr/ImplementingClass" ), contains( "cr/EmptyInterface", "cr/EmptyInterface" ) );
        assertThat( map.get( "cr/ExtendingClass" ), contains( "cr/Acd", "cr/Acd" ) );
        assertThat( map.get( "cr/InvokingStaticMethod" ), contains( "cr/Acd" ) );
        assertThat( map.get( "cr/ReferencingStaticField" ), contains( "cr/Acd", "cr/Acd" ) );

        assertThat( map.get( "cr/MethodChain" ), is( empty() ) );
        assertThat( map.get( "cr/MethodChain_StaticNonstatic" ), contains( "cr/MethodChain" ) );
        assertThat( map.get( "cr/MethodChain_NonstaticStatic" ), contains( "cr/MethodChain", "cr/MethodChain" ) );
        assertThat( map.get( "cr/FieldChain" ), is( empty() ) );
        assertThat( map.get( "cr/FieldChain_StaticNonstatic" ), contains( "cr/FieldChain", "cr/FieldChain", "cr/FieldChain" ) );
        assertThat( map.get( "cr/FieldChain_NonstaticStatic" ), contains( "cr/FieldChain", "cr/FieldChain", "cr/FieldChain", "cr/FieldChain" ) );

        assertThat( map.get( "cr/ClassWithArrayReferenceInField" ), contains( "cr/EmptyClass", "cr/EmptyClass" ) );
        assertThat( map.get( "cr/ClassWithArrayReferenceInLocalVariable" ), contains( "cr/EmptyClass", "cr/EmptyClass" ) );
        assertThat( map.get( "cr/ClassWithArrayReferenceInMethodParameter" ), contains( "cr/EmptyClass", "cr/EmptyClass" ) );
        assertThat( map.get( "cr/ClassWithArrayReferenceInMethodReturnType" ), contains( "cr/EmptyClass", "cr/EmptyClass" ) );

        assertThat( map.get( "cr/ClassWithInnerBeforeAttribute" ), contains( "cr/EmptyClass", "cr/EmptyClass" ) );
        assertThat( map.get( "cr/InnerClassBeforeAttribute" ), contains( "cr/EmptyClass", "cr/EmptyClass" ) );
        assertThat( map.get( "cr/ClassWithInnerAfterAttribute" ), contains( "cr/EmptyClass", "cr/EmptyClass" ) );
        assertThat( map.get( "cr/InnerClassAfterAttribute" ), contains( "cr/EmptyClass", "cr/EmptyClass" ) );

        // the following tests use more than one file
        assertThat( map.get( "cr2/Acd_staticImport" ), contains( "cr/Acd" ) );

        assertThat( map.get( "cr/MethodStaticImport" ), is( empty() ) ); // eclipse bug!
        assertThat( map.get( "cr/MethodStaticImportPublic" ), contains( "cr/MethodChainPublic" ) );

        assertThat( map.get( "cr/MethodChainPublic" ), is( empty() ) );
        assertThat( map.get( "cr/MethodChainStaticImport" ), is( empty() ) ); // eclipse bug!
        assertThat( map.get( "cr/MethodChainStaticImportPublic" ), contains( "cr/MethodChainPublic" ) );

        assertThat( map.get( "cr/FieldStaticImport" ), is( empty() ) ); // eclipse bug!
        assertThat( map.get( "cr/FieldStaticImportPublic" ), contains( "cr/FieldChainPublic" ) );

        assertThat( map.get( "cr/FieldChainPublic" ), is( empty() ) );
        assertThat( map.get( "cr/FieldChainStaticImport" ), is( empty() ) ); // eclipse bug!
        assertThat( map.get( "cr/FieldChainStaticImportPublic" ), contains( "cr/FieldChainPublic", "cr/FieldChainPublic" ) );
        assertThat( map.get( "cr2/Imports" ), is( empty() ) );
        assertThat( map.get( "cr2/ClassWithStaticImportInMethodParameter" ), contains( "cr/Acd" ) );

        // das gehört nicht hier hin!
        // Stattdessen sicherstellen, dass beim Anlegen und Löschen die entsprechende Neuberechnung auch stattfindet!
        project.delete( unloadedFile2 );
        inspector = new ACDInspector();
        new JavaFileDriver( file ).compute( createSetWith( inspector ) );
        map = inspector.getTypeConnections();
        assertThat( map.get( "cr/ToUnloadedClassInDifferentPackage" ), is( empty() ) );

    }
}
