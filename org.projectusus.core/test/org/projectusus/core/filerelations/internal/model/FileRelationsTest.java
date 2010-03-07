package org.projectusus.core.filerelations.internal.model;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItems;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.anotherTarget;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.anotherTargetToSource;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.anotherTargetToTarget;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.source;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.sourceClass;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.sourceToAnotherTarget;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.sourceToTarget;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.target;
import static org.projectusus.core.filerelations.model.SimpleTestScenario.targetToAnotherTarget;
import static org.projectusus.core.filerelations.test.IsSetOfMatcher.isEmptySet;
import static org.projectusus.core.filerelations.test.IsSetOfMatcher.isSetOf;

import org.junit.Test;

public class FileRelationsTest {

    private FileRelations fileRelations = new FileRelations();

    @Test
    public void shouldAddAndRetrieveSingleFileRelation() {
        fileRelations.add( sourceToTarget );
        fileRelations.add( sourceToTarget );
        assertThat( fileRelations.getDirectRelationsFrom( source ), isSetOf( sourceToTarget ) );
        assertThat( fileRelations.getDirectRelationsFrom( target ), isEmptySet() );
        assertThat( fileRelations.getDirectRelationsTo( source ), isEmptySet() );
        assertThat( fileRelations.getDirectRelationsTo( target ), isSetOf( sourceToTarget ) );
    }

    @Test
    public void shouldAddAndRetrieveMultipleFileRelations() {
        fileRelations.add( sourceToTarget );
        fileRelations.add( sourceToAnotherTarget );
        assertThat( fileRelations.getDirectRelationsFrom( source ), isSetOf( sourceToTarget, sourceToAnotherTarget ) );
        assertThat( fileRelations.getDirectRelationsFrom( target ), isEmptySet() );
        assertThat( fileRelations.getDirectRelationsFrom( anotherTarget ), isEmptySet() );
        assertThat( fileRelations.getDirectRelationsTo( source ), isEmptySet() );
        assertThat( fileRelations.getDirectRelationsTo( target ), isSetOf( sourceToTarget ) );
        assertThat( fileRelations.getDirectRelationsTo( anotherTarget ), isSetOf( sourceToAnotherTarget ) );
    }

    @Test
    public void shouldRemoveAllOutgoingRelations() {
        fileRelations.add( sourceToTarget );
        fileRelations.removeDirectRelationsFrom( source );
        assertThat( fileRelations.getDirectRelationsFrom( source ), isEmptySet() );
        assertThat( fileRelations.getDirectRelationsTo( target ), isSetOf( sourceToTarget ) );
    }

    @Test
    public void shouldRemoveOutgoingRelationsFromSingleFile() {
        fileRelations.add( sourceToTarget );
        fileRelations.add( sourceToAnotherTarget );
        fileRelations.add( targetToAnotherTarget );
        fileRelations.removeDirectRelationsFrom( target );
        assertThat( fileRelations.getDirectRelationsFrom( source ), isSetOf( sourceToTarget, sourceToAnotherTarget ) );
        assertThat( fileRelations.getDirectRelationsFrom( target ), isEmptySet() );
        assertThat( fileRelations.getDirectRelationsTo( target ), isSetOf( sourceToTarget ) );
        assertThat( fileRelations.getDirectRelationsTo( anotherTarget ), isSetOf( targetToAnotherTarget, sourceToAnotherTarget ) );
    }

    @Test
    public void transitiveRelationsFromSingleRelation() {
        fileRelations.add( sourceToTarget );
        assertThat( fileRelations.getTransitiveRelationsFrom( source, sourceClass ), isSetOf( sourceToTarget ) );
    }

    @Test
    public void transitiveRelationsFromTwoRelations() {
        fileRelations.add( sourceToTarget );
        fileRelations.add( targetToAnotherTarget );
        assertThat( fileRelations.getTransitiveRelationsFrom( source, sourceClass ), isSetOf( sourceToTarget, targetToAnotherTarget ) );
    }

    @Test
    public void transitiveRelationsFromCyclicRelationsBelowStart() {
        fileRelations.add( sourceToTarget );
        fileRelations.add( targetToAnotherTarget );
        fileRelations.add( anotherTargetToTarget );
        assertThat( fileRelations.getTransitiveRelationsFrom( source, sourceClass ), isSetOf( sourceToTarget, targetToAnotherTarget, anotherTargetToTarget ) );
    }

    @Test
    public void transitiveRelationsFromCyclicRelationsIncludingStart() {
        fileRelations.add( sourceToTarget );
        fileRelations.add( targetToAnotherTarget );
        fileRelations.add( anotherTargetToTarget );
        fileRelations.add( anotherTargetToSource );
        assertThat( fileRelations.getTransitiveRelationsFrom( source, sourceClass ), isSetOf( sourceToTarget, targetToAnotherTarget, anotherTargetToTarget, anotherTargetToSource ) );
    }

    @Test
    public void getAllDirectRelations() {
        fileRelations.add( sourceToTarget );
        fileRelations.add( anotherTargetToSource );
        assertThat( fileRelations.getAllDirectRelations(), hasItems( sourceToTarget, anotherTargetToSource ) );
    }
}
