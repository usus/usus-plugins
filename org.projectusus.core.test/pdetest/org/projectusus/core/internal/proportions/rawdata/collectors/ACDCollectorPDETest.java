package org.projectusus.core.internal.proportions.rawdata.collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.junit.Test;
import org.projectusus.adapter.ForcedRecompute;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.Classname;
import org.projectusus.core.internal.proportions.rawdata.PDETestForMetricsComputation;
import org.projectusus.statistics.ACDStatistic;

public class ACDCollectorPDETest extends PDETestForMetricsComputation {

    @Test
    public void singleClass() throws Exception {
        createFileAndBuild( "1" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, ClassDescriptor.getAll().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
    }

    @Test
    public void twoUnrelatedClasses() throws Exception {
        createFileAndBuild( "2" );
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, ClassDescriptor.getAll().size() );
        assertEquals( 0.5, getACD(), 0.0001 );
    }

    @Test
    public void twoRelatedClasses1knows2() throws Exception {
        createFileAndBuild( "3_1" );
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, ClassDescriptor.getAll().size() );
        assertEquals( 0.75, getACD(), 0.0001 );
    }

    @Test
    public void twoRelatedClassesKnowEachOther() throws Exception {
        createFileAndBuild( "_twoKnowEachOther" );
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, ClassDescriptor.getAll().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
        // TODO NR
        // assertEquals( 2, model.getSumOfAllDirectChildrenOfAllClasses() );
        // assertEquals( 4, model.getSumOfAllKnownChildrenOfAllClasses() );
    }

    @Test
    public void twoRelatedClasses1knows2Statically() throws Exception {
        createFileAndBuild( "_1knows2static" );
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, ClassDescriptor.getAll().size() );
        assertEquals( 0.75, getACD(), 0.0001 );
    }

    @Test
    public void twoRelatedClasses1knows2Generic() throws Exception {
        createFileAndBuild( "_1knows2generic" );
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, ClassDescriptor.getAll().size() );
        assertEquals( 0.75, getACD(), 0.0001 );
    }

    @Test
    public void twoRelatedClasses1knows2InGenericArgument() throws Exception {
        createFileAndBuild( "_1knows2InGenericArg" );
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, ClassDescriptor.getAll().size() );
        assertEquals( 0.75, getACD(), 0.0001 );
    }

    @Test
    public void twoRelatedClasses2know2() throws Exception {
        createFileAndBuild( "3_2" );
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, ClassDescriptor.getAll().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
    }

    @Test
    public void threeUnrelatedClasses() throws Exception {
        createFileAndBuild( "4" );
        assertEquals( 3, getNumberOfClasses() );
        assertEquals( 3, ClassDescriptor.getAll().size() );
        assertEquals( 0.3333, getACD(), 0.0001 );
    }

    @Test
    public void threeRelatedClasses1knows2() throws Exception {
        createFileAndBuild( "5" );
        assertEquals( 3, getNumberOfClasses() );
        assertEquals( 3, ClassDescriptor.getAll().size() );
        assertEquals( 4 / 9.0, getACD(), 0.0001 );
    }

    @Test
    public void threeRelatedClasses2know2() throws Exception {
        createFileAndBuild( "6" );
        assertEquals( 3, getNumberOfClasses() );
        assertEquals( 3, ClassDescriptor.getAll().size() );
        assertEquals( 5 / 9.0, getACD(), 0.0001 );
    }

    @Test
    public void threeRelatedClasses3know2() throws Exception {
        createFileAndBuild( "7" );
        assertEquals( 3, getNumberOfClasses() );
        assertEquals( 3, ClassDescriptor.getAll().size() );
        assertEquals( 7 / 9.0, getACD(), 0.0001 );
    }

    @Test
    public void tenRelatedClasses1knows2() throws Exception {
        createFileAndBuild( "8" );
        assertEquals( 10, getNumberOfClasses() );
        assertEquals( 10, ClassDescriptor.getAll().size() );
        assertEquals( 0.11, getACD(), 0.0001 );
    }

    @Test
    public void tenRelatedClasses1knows2know2() throws Exception {
        createFileAndBuild( "9" );
        assertEquals( 10, getNumberOfClasses() );
        assertEquals( 10, ClassDescriptor.getAll().size() );
        assertEquals( 0.2, getACD(), 0.0001 );
    }

    @Test
    public void twoFiles_11aKnows11b() throws Exception {
        createFile( "11b" );
        createFileAndBuild( "11a" );
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, ClassDescriptor.getAll().size() );
        assertEquals( 0.75, getACD(), 0.0001 );
    }

    @Test
    public void twoFiles_11aLoaded2nd_Knows11bLoaded1st() throws Exception {
        createFileAndBuild( "11b" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, ClassDescriptor.getAll().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
        createFile( "11a" );
        workspace.buildIncrementallyAndWait();
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, ClassDescriptor.getAll().size() );
        assertEquals( 0.75, getACD(), 0.0001 );
    }

    @Test
    public void twoFiles_GameStateLoaded2nd_KnowsLRUCacheLoaded1st_WithPackages() throws Exception {
        createWSFolders();
        project.createFile( "org/doublemill/model/util/LRUCache.java", loadResource( "Acd_LRUCache.test" ) );
        workspace.buildFullyAndWait();
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, ClassDescriptor.getAll().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
        project.createFile( "org/doublemill/model/ai/GameStateAI.java", loadResource( "Acd_GameStateAI.test" ) );
        workspace.buildIncrementallyAndWait();
        new ForcedRecompute().schedule();
        Thread.sleep( 1000 );
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, ClassDescriptor.getAll().size() );
        assertEquals( 0.75, getACD(), 0.0001 );
        // assertEquals( 1, model.getSumOfAllDirectChildrenOfAllClasses() );
        // assertEquals( 3, model.getSumOfAllKnownChildrenOfAllClasses() );
    }

    @Test
    public void twoFiles_GameStateLoaded1st_KnowsLRUCacheLoaded2nd_WithPackages() throws Exception {
        createWSFolders();
        project.createFile( "org/doublemill/model/ai/GameStateAI.java", loadResource( "Acd_GameStateAI.test" ) );
        workspace.buildFullyAndWait();
        System.out.println( "=====================" );
        System.out.println( "Erster Autobuild beendet." );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, ClassDescriptor.getAll().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
        project.createFile( "org/doublemill/model/util/LRUCache.java", loadResource( "Acd_LRUCache.test" ) );
        workspace.buildIncrementallyAndWait();
        new ForcedRecompute().schedule();
        Thread.sleep( 1000 );
        System.out.println( "=====================" );
        System.out.println( "Zweiter Autobuild beendet." );
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, ClassDescriptor.getAll().size() );
        assertEquals( 0.75, getACD(), 0.0001 );
        System.out.println( "=====================" );
        System.out.println( "Test beendet." );
        // assertEquals( 1, model.getSumOfAllDirectChildrenOfAllClasses() );
        // assertEquals( 3, model.getSumOfAllKnownChildrenOfAllClasses() );
    }

    @Test
    public void twoFiles_11aLoaded2nd_Knows11bLoaded1st_11bIsDeleted() throws Exception {
        IFile file11b = createFileAndBuild( "11b" );
        createFileAndBuild( "11a" );
        workspace.buildFullyAndWait(); // seems to fix a racing condition that only occurs during testing
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, ClassDescriptor.getAll().size() );
        assertEquals( 0.75, getACD(), 0.0001 );
        project.delete( file11b );
        workspace.buildIncrementallyAndWait();
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, ClassDescriptor.getAll().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
    }

    @Test
    public void oneFile_ItKnowsAnotherWhichIsMissing() throws Exception {
        createFileAndBuild( "11a" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, ClassDescriptor.getAll().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
    }

    @Test
    public void oneFile_ItKnowsAnotherWhichIsCreatedLater() throws Exception {
        createFileAndBuild( "11a" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, ClassDescriptor.getAll().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
        createFile( "11b" );
        workspace.buildIncrementallyAndWait();
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, ClassDescriptor.getAll().size() );
        assertEquals( 0.75, getACD(), 0.0001 );
    }

    @Test
    public void oneFile_ItIsKnownByAnotherWhichIsCreatedLater() throws Exception {
        createFileAndBuild( "11b" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, ClassDescriptor.getAll().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
        createFile( "11a" );
        workspace.buildIncrementallyAndWait();
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, ClassDescriptor.getAll().size() );
        assertEquals( 0.75, getACD(), 0.0001 );
    }

    @Test
    public void twoFiles_TheFirstKnowsTheSecond_FirstIsDeleted() throws Exception {
        createFileAndBuild( "11b" );
        IFile firstFile = createFileAndBuild( "11a" );
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, ClassDescriptor.getAll().size() );
        assertEquals( 0.75, getACD(), 0.0001 );
        project.delete( firstFile );
        workspace.buildIncrementallyAndWait();
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, ClassDescriptor.getAll().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
    }

    @Test
    public void twoFilesKnowEachOther() throws Exception {
        createFileAndBuild( "10a" );
        createFileAndBuild( "10b" );
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, ClassDescriptor.getAll().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
        // assertEquals( 2, model.getSumOfAllDirectChildrenOfAllClasses() );
        // assertEquals( 4, model.getSumOfAllKnownChildrenOfAllClasses() );
    }

    @Test
    public void twoFilesKnowEachOtherTheSecondIsDeleted() throws Exception {
        createFileAndBuild( "10a" );
        IFile secondFile = createFileAndBuild( "10b" );
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, ClassDescriptor.getAll().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
        project.delete( secondFile );
        workspace.buildIncrementallyAndWait();
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, ClassDescriptor.getAll().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
    }

    @Test
    public void singleInterface() throws Exception {
        createFileAndBuild( "15" );
        assertEquals( 1, getNumberOfClasses() );
        assertEquals( 1, ClassDescriptor.getAll().size() );
        assertEquals( 1.0, getACD(), 0.0001 );
    }

    @Test
    public void interfaceKnowsClass() throws Exception {
        createFileAndBuild( "16" );
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, ClassDescriptor.getAll().size() );
        assertEquals( 3 / 4.0, getACD(), 0.0001 );
    }

    @Test
    public void classLoaded2nd_ImplementsInterfaceLoaded1st() throws Exception {
        createFileAndBuild( "13b" );
        createFileAndBuild( "13a" );
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, ClassDescriptor.getAll().size() );
        assertEquals( 3 / 4.0, getACD(), 0.0001 );
    }

    @Test
    public void classLoaded1st_ImplementsInterfaceLoaded2nd() throws Exception {
        createFileAndBuild( "13a" );
        createFileAndBuild( "13b" );
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, ClassDescriptor.getAll().size() );
        assertEquals( 3 / 4.0, getACD(), 0.0001 );
    }

    @Test
    public void classExtendsClass_SuperClassIsLoadedFirst() throws Exception {
        createFileAndBuild( "14b" );
        createFileAndBuild( "14a" );
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, ClassDescriptor.getAll().size() );
        assertEquals( 3 / 4.0, getACD(), 0.0001 );
    }

    @Test
    public void classExtendsClass_SuperClassIsLoadedSecond() throws Exception {
        createFileAndBuild( "14a" );
        createFileAndBuild( "14b" );
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, ClassDescriptor.getAll().size() );
        assertEquals( 3 / 4.0, getACD(), 0.0001 );
    }

    @Test
    public void classImplementsInterfaceExtendsInterface() throws Exception {
        createFileAndBuild( "12a" );
        assertEquals( 1, getNumberOfClasses() );
        Set<ClassDescriptor> descriptors = ClassDescriptor.getAll();
        assertEquals( 1, descriptors.size() );
        for( ClassDescriptor descriptor : descriptors ) {
            assertEquals( 1, descriptor.getCCD() );
            assertEquals( 1, descriptor.getTransitiveParentCount() );
        }
        assertEquals( 1.0, getACD(), 0.0001 );

        createFileAndBuild( "12b" );
        assertEquals( 2, getNumberOfClasses() );
        descriptors = ClassDescriptor.getAll();
        assertEquals( 2, descriptors.size() );
        for( ClassDescriptor descriptor : descriptors ) {
            if( descriptor.getClassname().toString().equals( "Acd12a" ) ) {
                assertEquals( 2, descriptor.getCCD() );
                assertEquals( 1, descriptor.getTransitiveParentCount() );
            } else if( descriptor.getClassname().toString().equals( "Acd12b" ) ) {
                assertEquals( 1, descriptor.getCCD() );
                assertEquals( 2, descriptor.getTransitiveParentCount() );
            } else {
                fail( "Found unknown ClassDescriptor" );
            }
        }
        assertEquals( 3 / 4.0, getACD(), 0.0001 );

        createFileAndBuild( "12c" );
        assertEquals( 3, getNumberOfClasses() );
        assertEquals( 3, ClassDescriptor.getAll().size() );
        descriptors = ClassDescriptor.getAll();
        assertEquals( 3, descriptors.size() );
        for( ClassDescriptor descriptor : descriptors ) {
            if( descriptor.getClassname().toString().equals( "Acd12a" ) ) {
                assertEquals( 3, descriptor.getCCD() );
                assertEquals( 1, descriptor.getTransitiveParentCount() );
            } else if( descriptor.getClassname().toString().equals( "Acd12b" ) ) {
                assertEquals( 2, descriptor.getCCD() );
                assertEquals( 2, descriptor.getTransitiveParentCount() );
            } else if( descriptor.getClassname().toString().equals( "Acd12c" ) ) {
                assertEquals( 1, descriptor.getCCD() );
                assertEquals( 3, descriptor.getTransitiveParentCount() );
            } else {
                fail( "Found unknown ClassDescriptor" );
            }
        }
        assertEquals( 6 / 9.0, getACD(), 0.0001 );
    }

    @Test
    public void classKnowsClassKnowsClass() throws Exception {
        createFileAndBuild( "17a" );
        assertEquals( 1, getNumberOfClasses() );
        Set<ClassDescriptor> descriptors = ClassDescriptor.getAll();
        assertEquals( 1, descriptors.size() );
        for( ClassDescriptor descriptor : descriptors ) {
            assertEquals( 1, descriptor.getCCD() );
            assertEquals( 1, descriptor.getTransitiveParentCount() );
        }
        assertEquals( 1.0, getACD(), 0.0001 );

        createFileAndBuild( "17b" );
        assertEquals( 2, getNumberOfClasses() );
        descriptors = ClassDescriptor.getAll();
        assertEquals( 2, descriptors.size() );
        for( ClassDescriptor descriptor : descriptors ) {
            if( descriptor.getClassname().toString().equals( "Acd17a" ) ) {
                assertEquals( 2, descriptor.getCCD() );
                assertEquals( 1, descriptor.getTransitiveParentCount() );
            } else if( descriptor.getClassname().toString().equals( "Acd17b" ) ) {
                assertEquals( 1, descriptor.getCCD() );
                assertEquals( 2, descriptor.getTransitiveParentCount() );
            } else {
                fail( "Found unknown ClassDescriptor" );
            }
        }
        assertEquals( 3 / 4.0, getACD(), 0.0001 );

        createFileAndBuild( "17c" );
        assertEquals( 3, getNumberOfClasses() );
        assertEquals( 3, ClassDescriptor.getAll().size() );
        descriptors = ClassDescriptor.getAll();
        assertEquals( 3, descriptors.size() );
        for( ClassDescriptor descriptor : descriptors ) {
            if( descriptor.getClassname().toString().equals( "Acd17a" ) ) {
                assertEquals( 3, descriptor.getCCD() );
                assertEquals( 1, descriptor.getTransitiveParentCount() );
            } else if( descriptor.getClassname().toString().equals( "Acd17b" ) ) {
                assertEquals( 2, descriptor.getCCD() );
                assertEquals( 2, descriptor.getTransitiveParentCount() );
            } else if( descriptor.getClassname().toString().equals( "Acd17c" ) ) {
                assertEquals( 1, descriptor.getCCD() );
                assertEquals( 3, descriptor.getTransitiveParentCount() );
            } else {
                fail( "Found unknown ClassDescriptor" );
            }
        }
        assertEquals( 6 / 9.0, getACD(), 0.0001 );
    }

    @Test
    public void staticMethodWithClassName() throws Exception {
        project.createFolder( "oops" );
        createJavaFile( "oops/Acd_static1.java" );
        workspace.buildFullyAndWait();
        createJavaFile( "oops/Acd_static2.java" );
        workspace.buildFullyAndWait();
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, ClassDescriptor.getAll().size() );
        assertEquals( 3 / 4.0, getACD(), 0.0001 );
    }

    @Test
    public void methodChainingWithClassnameAndStaticAndNonstaticMethod() throws Exception {
        project.createFolder( "methodchaining" );
        createJavaFile( "methodchaining/Chain1.java" );
        workspace.buildFullyAndWait();
        createJavaFile( "methodchaining/Chain2.java" );
        workspace.buildFullyAndWait();
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, ClassDescriptor.getAll().size() );
        assertEquals( 3 / 4.0, getACD(), 0.0001 );
    }

    @Test
    public void methodChainingWithStaticImportAndStaticAndNonstaticMethod() throws Exception {
        project.createFolder( "methodchaining" );
        createJavaFile( "methodchaining/Chain1.java" );
        workspace.buildFullyAndWait();
        createJavaFile( "methodchaining/Chain5.java" );
        workspace.buildFullyAndWait();
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, ClassDescriptor.getAll().size() );
        assertEquals( 3 / 4.0, getACD(), 0.0001 );
    }

    @Test
    public void methodChainingWithConstructorAndNonstaticAndStaticMethod() throws Exception {
        project.createFolder( "methodchaining" );
        createJavaFile( "methodchaining/Chain3.java" );
        workspace.buildFullyAndWait();
        createJavaFile( "methodchaining/Chain4.java" );
        workspace.buildFullyAndWait();
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, ClassDescriptor.getAll().size() );
        assertEquals( 3 / 4.0, getACD(), 0.0001 );
    }

    @Test
    public void fieldChainingWithClassnameAndStaticAndNonstaticField() throws Exception {
        project.createFolder( "methodchaining" );
        createJavaFile( "methodchaining/Field1.java" );
        workspace.buildFullyAndWait();
        createJavaFile( "methodchaining/Field2.java" );
        workspace.buildFullyAndWait();
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, ClassDescriptor.getAll().size() );
        assertEquals( 3 / 4.0, getACD(), 0.0001 );
    }

    @Test
    public void fieldChainingWithStaticImportAndStaticAndNonstaticField() throws Exception {
        project.createFolder( "methodchaining" );
        createJavaFile( "methodchaining/Field1.java" );
        workspace.buildFullyAndWait();
        createJavaFile( "methodchaining/Field5.java" );
        workspace.buildFullyAndWait();
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, ClassDescriptor.getAll().size() );
        assertEquals( 3 / 4.0, getACD(), 0.0001 );
    }

    @Test
    public void fieldChainingWithConstructorAndNonstaticAndStaticField() throws Exception {
        project.createFolder( "methodchaining" );
        createJavaFile( "methodchaining/Field3.java" );
        workspace.buildFullyAndWait();
        createJavaFile( "methodchaining/Field4.java" );
        workspace.buildFullyAndWait();
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, ClassDescriptor.getAll().size() );
        assertEquals( 3 / 4.0, getACD(), 0.0001 );
    }

    @Test
    public void staticMethodWithoutClassName() throws Exception {
        project.createFolder( "oops" );
        createJavaFile( "oops/Acd_static1.java" );
        workspace.buildFullyAndWait();
        createJavaFile( "oops/Acd_static3.java" );
        workspace.buildFullyAndWait();
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, ClassDescriptor.getAll().size() );
        assertEquals( 3 / 4.0, getACD(), 0.0001 );
    }

    @Test
    public void staticFieldWithClassName() throws Exception {
        project.createFolder( "acd_staticfields" );
        createJavaFile( "acd_staticfields/Acd_static1.java" );
        workspace.buildFullyAndWait();
        createJavaFile( "acd_staticfields/Acd_static2.java" );
        workspace.buildFullyAndWait();
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, ClassDescriptor.getAll().size() );
        for( ClassDescriptor descriptor : ClassDescriptor.getAll() ) {
            if( descriptor.getClassname().equals( new Classname( "Acd_static1" ) ) ) {
                assertEquals( 0, descriptor.getChildren().size() );
            }
            if( descriptor.getClassname().equals( new Classname( "Acd_static2" ) ) ) {
                assertEquals( 1, descriptor.getChildren().size() );
                for( ClassDescriptor child : descriptor.getChildren() ) {
                    assertEquals( "Acd_static1", child.getClassname().toString() );
                }
            }
        }
        assertEquals( 3 / 4.0, getACD(), 0.0001 );
    }

    @Test
    public void staticFieldWithoutClassName() throws Exception {
        project.createFolder( "acd_staticfields" );
        createJavaFile( "acd_staticfields/Acd_static1.java" );
        workspace.buildFullyAndWait();
        createJavaFile( "acd_staticfields/Acd_static3.java" );
        workspace.buildFullyAndWait();
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, ClassDescriptor.getAll().size() );
        for( ClassDescriptor descriptor : ClassDescriptor.getAll() ) {
            if( descriptor.getClassname().equals( new Classname( "Acd_static1" ) ) ) {
                assertEquals( 0, descriptor.getChildren().size() );
            }
            if( descriptor.getClassname().equals( new Classname( "Acd_static3" ) ) ) {
                assertEquals( 1, descriptor.getChildren().size() );
                for( ClassDescriptor child : descriptor.getChildren() ) {
                    assertEquals( "Acd_static1", child.getClassname().toString() );
                }
            }
        }
        assertEquals( 3 / 4.0, getACD(), 0.0001 );
    }

    @Test
    public void arrayReferenceInField() throws Exception {
        project.createFolder( "arrays" );
        createJavaFile( "arrays/NormalClass.java" );
        workspace.buildFullyAndWait();
        createJavaFile( "arrays/ClassWithArrayReferenceInField.java" );
        workspace.buildFullyAndWait();
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, ClassDescriptor.getAll().size() );
        assertEquals( 3 / 4.0, getACD(), 0.0001 );
    }

    @Test
    public void arrayReferenceInLocalVariable() throws Exception {
        project.createFolder( "arrays" );
        createJavaFile( "arrays/NormalClass.java" );
        workspace.buildFullyAndWait();
        createJavaFile( "arrays/ClassWithArrayReferenceInLocalVariable.java" );
        workspace.buildFullyAndWait();
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, ClassDescriptor.getAll().size() );
        assertEquals( 3 / 4.0, getACD(), 0.0001 );
    }

    @Test
    public void arrayReferenceInMethodParameter() throws Exception {
        project.createFolder( "arrays" );
        createJavaFile( "arrays/NormalClass.java" );
        workspace.buildFullyAndWait();
        createJavaFile( "arrays/ClassWithArrayReferenceInMethodParameter.java" );
        workspace.buildFullyAndWait();
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, ClassDescriptor.getAll().size() );
        assertEquals( 3 / 4.0, getACD(), 0.0001 );
    }

    @Test
    public void arrayReferenceInMethodReturnType() throws Exception {
        project.createFolder( "arrays" );
        createJavaFile( "arrays/NormalClass.java" );
        workspace.buildFullyAndWait();
        createJavaFile( "arrays/ClassWithArrayReferenceInMethodReturnType.java" );
        workspace.buildFullyAndWait();
        assertEquals( 2, getNumberOfClasses() );
        assertEquals( 2, ClassDescriptor.getAll().size() );
        assertEquals( 3 / 4.0, getACD(), 0.0001 );
    }

    @Test
    public void importsAreNotConsideredForACD() throws Exception {
        project.createFolder( "imports" );
        createJavaFile( "imports/Class1.java" );
        workspace.buildFullyAndWait();
        createJavaFile( "imports/Class2.java" );
        createJavaFile( "imports/Class3.java" );
        workspace.buildFullyAndWait();
        assertEquals( 3, getNumberOfClasses() );
        assertEquals( 3, ClassDescriptor.getAll().size() );
        assertEquals( 3 / 9.0, getACD(), 0.0001 );
    }

    @Test
    public void innerClassesHaveTheirOwnConnections_InnerClassBeforeAttribute() throws Exception {
        project.createFolder( "inner" );
        createJavaFile( "inner/ClassWithInnerBeforeAttribute.java" );
        workspace.buildFullyAndWait();
        createJavaFile( "inner/Class2.java" );
        workspace.buildFullyAndWait();
        assertEquals( 3, getNumberOfClasses() );
        assertEquals( 3, ClassDescriptor.getAll().size() );
        assertEquals( 5 / 9.0, getACD(), 0.0001 );
    }

    @Test
    public void innerClassesHaveTheirOwnConnections_AttributeBeforeInnerClass() throws Exception {
        project.createFolder( "inner" );
        createJavaFile( "inner/ClassWithInnerAfterAttribute.java" );
        workspace.buildFullyAndWait();
        createJavaFile( "inner/Class2.java" );
        workspace.buildFullyAndWait();
        assertEquals( 3, getNumberOfClasses() );
        assertEquals( 3, ClassDescriptor.getAll().size() );
        assertEquals( 5 / 9.0, getACD(), 0.0001 );
    }

    private double getACD() {
        ACDStatistic statistic = new ACDStatistic();
        statistic.visit();
        return statistic.getRelativeACD();
    }

    protected IFile createFile( String filenumber ) throws Exception {
        return super.createFile( "Acd" + filenumber );
    }

    private void createWSFolders() throws CoreException {
        project.createFolder( "org" );
        project.createFolder( "org/doublemill" );
        project.createFolder( "org/doublemill/model" );
        project.createFolder( "org/doublemill/model/ai" );
        project.createFolder( "org/doublemill/model/util" );
    }
}
