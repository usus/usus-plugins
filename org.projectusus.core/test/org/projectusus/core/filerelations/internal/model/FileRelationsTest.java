package org.projectusus.core.filerelations.internal.model;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItems;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.ANOTHER_TARGET;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.ANOTHER_TARGET_CLASS;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.SOURCE;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.SOURCE_CLASS;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.TARGET;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.TARGET_CLASS;
import static org.projectusus.core.filerelations.model.TestServiceManager.createDescriptor;
import static org.projectusus.core.filerelations.test.IsSetOfMatcher.isSetOf;

import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.FileRelation;
import org.projectusus.core.internal.proportions.rawdata.UsusModel;

public class FileRelationsTest {

    @Before
    public void cleanup() {
        UsusModel.clear();
    }

    // @Test
    // public void shouldAddAndRetrieveSingleFileRelation() {
    // FileRelation sourceToTarget = FileRelation.of( sourceDescriptor, targetDescriptor );
    // FileRelation.of( sourceDescriptor, targetDescriptor );
    // assertThat( sourceDescriptor.getDirectRelationsFrom(), isSetOf( sourceToTarget ) );
    // assertThat( fileRelations.getDirectRelationsFrom( target ), isEmptySet() );
    // assertThat( fileRelations.getDirectRelationsTo( source ), isEmptySet() );
    // assertThat( fileRelations.getDirectRelationsTo( target ), isSetOf( sourceToTarget ) );
    // }
    //
    // @Test
    // public void shouldAddAndRetrieveMultipleFileRelations() {
    // FileRelation sourceToTarget = FileRelation.of( sourceDescriptor, targetDescriptor );
    // FileRelation sourceToAnotherTarget = FileRelation.of( sourceDescriptor, anotherTargetDescriptor );
    // assertThat( fileRelations.getDirectRelationsFrom( source ), isSetOf( sourceToTarget, sourceToAnotherTarget ) );
    // assertThat( fileRelations.getDirectRelationsFrom( target ), isEmptySet() );
    // assertThat( fileRelations.getDirectRelationsFrom( anotherTarget ), isEmptySet() );
    // assertThat( fileRelations.getDirectRelationsTo( source ), isEmptySet() );
    // assertThat( fileRelations.getDirectRelationsTo( target ), isSetOf( sourceToTarget ) );
    // assertThat( fileRelations.getDirectRelationsTo( anotherTarget ), isSetOf( sourceToAnotherTarget ) );
    // }

    // @Test
    // public void shouldRemoveAllOutgoingRelations() {
    // FileRelation sourceToTarget = FileRelation.of( sourceDescriptor, targetDescriptor );
    // fileRelations.removeDirectRelationsFrom( source );
    // assertThat( fileRelations.getDirectRelationsFrom( source ), isEmptySet() );
    // assertThat( fileRelations.getDirectRelationsTo( target ), isSetOf( sourceToTarget ) );
    // }
    //
    // @Test
    // public void shouldRemoveOutgoingRelationsFromSingleFile() {
    // FileRelation sourceToTarget = FileRelation.of( sourceDescriptor, targetDescriptor );
    // FileRelation sourceToAnotherTarget = FileRelation.of( sourceDescriptor, anotherTargetDescriptor );
    // FileRelation targetToAnotherTarget = FileRelation.of( targetDescriptor, anotherTargetDescriptor );
    // fileRelations.removeDirectRelationsFrom( target );
    // assertThat( fileRelations.getDirectRelationsFrom( source ), isSetOf( sourceToTarget, sourceToAnotherTarget ) );
    // assertThat( fileRelations.getDirectRelationsFrom( target ), isEmptySet() );
    // assertThat( fileRelations.getDirectRelationsTo( target ), isSetOf( sourceToTarget ) );
    // assertThat( fileRelations.getDirectRelationsTo( anotherTarget ), isSetOf( targetToAnotherTarget, sourceToAnotherTarget ) );
    // }

    @Test
    public void transitiveRelationsFromSingleRelation() {
        ClassDescriptor sourceDescriptor = createDescriptor( SOURCE, SOURCE_CLASS );
        ClassDescriptor targetDescriptor = createDescriptor( TARGET, TARGET_CLASS );
        FileRelation sourceToTarget = FileRelation.of( sourceDescriptor, targetDescriptor );
        assertThat( sourceDescriptor.getTransitiveRelationsFrom(), isSetOf( sourceToTarget ) );
    }

    @Test
    public void transitiveRelationsFromTwoRelations() {
        ClassDescriptor sourceDescriptor = createDescriptor( SOURCE, SOURCE_CLASS );
        ClassDescriptor targetDescriptor = createDescriptor( TARGET, TARGET_CLASS );
        ClassDescriptor anotherTargetDescriptor = createDescriptor( ANOTHER_TARGET, ANOTHER_TARGET_CLASS );
        FileRelation sourceToTarget = FileRelation.of( sourceDescriptor, targetDescriptor );
        FileRelation targetToAnotherTarget = FileRelation.of( targetDescriptor, anotherTargetDescriptor );
        assertThat( sourceDescriptor.getTransitiveRelationsFrom(), isSetOf( sourceToTarget, targetToAnotherTarget ) );
    }

    @Test
    public void transitiveRelationsFromCyclicRelationsBelowStart() {
        ClassDescriptor sourceDescriptor = createDescriptor( SOURCE, SOURCE_CLASS );
        ClassDescriptor targetDescriptor = createDescriptor( TARGET, TARGET_CLASS );
        ClassDescriptor anotherTargetDescriptor = createDescriptor( ANOTHER_TARGET, ANOTHER_TARGET_CLASS );
        FileRelation sourceToTarget = FileRelation.of( sourceDescriptor, targetDescriptor );
        FileRelation targetToAnotherTarget = FileRelation.of( targetDescriptor, anotherTargetDescriptor );
        FileRelation anotherTargetToTarget = FileRelation.of( anotherTargetDescriptor, targetDescriptor );
        assertThat( sourceDescriptor.getTransitiveRelationsFrom(), isSetOf( sourceToTarget, targetToAnotherTarget, anotherTargetToTarget ) );
    }

    @Test
    public void transitiveRelationsFromCyclicRelationsIncludingStart() {
        ClassDescriptor sourceDescriptor = createDescriptor( SOURCE, SOURCE_CLASS );
        ClassDescriptor targetDescriptor = createDescriptor( TARGET, TARGET_CLASS );
        ClassDescriptor anotherTargetDescriptor = createDescriptor( ANOTHER_TARGET, ANOTHER_TARGET_CLASS );
        FileRelation sourceToTarget = FileRelation.of( sourceDescriptor, targetDescriptor );
        FileRelation targetToAnotherTarget = FileRelation.of( targetDescriptor, anotherTargetDescriptor );
        FileRelation anotherTargetToTarget = FileRelation.of( anotherTargetDescriptor, targetDescriptor );
        FileRelation anotherTargetToSource = FileRelation.of( anotherTargetDescriptor, sourceDescriptor );
        assertThat( sourceDescriptor.getTransitiveRelationsFrom(), isSetOf( sourceToTarget, targetToAnotherTarget, anotherTargetToTarget, anotherTargetToSource ) );
    }

    @Test
    public void getAllDirectRelations() {
        ClassDescriptor sourceDescriptor = createDescriptor( SOURCE, SOURCE_CLASS );
        ClassDescriptor targetDescriptor = createDescriptor( TARGET, TARGET_CLASS );
        ClassDescriptor anotherTargetDescriptor = createDescriptor( ANOTHER_TARGET, ANOTHER_TARGET_CLASS );
        FileRelation sourceToTarget = FileRelation.of( sourceDescriptor, targetDescriptor );
        FileRelation anotherTargetToSource = FileRelation.of( anotherTargetDescriptor, sourceDescriptor );
        assertThat( FileRelation.getAllRelations(), hasItems( sourceToTarget, anotherTargetToSource ) );
    }

    // TODO
    // @Test
    // public void shouldRemoveAllIncidentRelations() {
    // fileRelations.add( sourceToTarget );
    // fileRelations.add( anotherTargetToSource );
    // fileRelations.add( targetToAnotherTarget );
    // fileRelations.removeAllIncidentRelations( source );
    // assertThat( fileRelations.getDirectRelationsFrom( source ), isEmptySet() );
    // assertThat( fileRelations.getDirectRelationsTo( source ), isEmptySet() );
    // assertThat( fileRelations.getDirectRelationsFrom( anotherTarget ), isEmptySet() );
    // assertThat( fileRelations.getDirectRelationsTo( anotherTarget ), isSetOf( targetToAnotherTarget ) );
    // assertThat( fileRelations.getDirectRelationsFrom( target ), isSetOf( targetToAnotherTarget ) );
    // assertThat( fileRelations.getDirectRelationsTo( target ), isEmptySet() );
    // }
}
