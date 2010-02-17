package org.projectusus.core.filerelations;

import static org.junit.Assert.assertThat;
import static org.projectusus.core.filerelations.IsSetOfMatcher.isEmptySet;
import static org.projectusus.core.filerelations.IsSetOfMatcher.isSetOf;
import static org.projectusus.core.filerelations.SimpleTestScenario.anotherTarget;
import static org.projectusus.core.filerelations.SimpleTestScenario.anotherTargetToSource;
import static org.projectusus.core.filerelations.SimpleTestScenario.anotherTargetToTarget;
import static org.projectusus.core.filerelations.SimpleTestScenario.source;
import static org.projectusus.core.filerelations.SimpleTestScenario.sourceClass;
import static org.projectusus.core.filerelations.SimpleTestScenario.sourceToAnotherTarget;
import static org.projectusus.core.filerelations.SimpleTestScenario.sourceToTarget;
import static org.projectusus.core.filerelations.SimpleTestScenario.target;
import static org.projectusus.core.filerelations.SimpleTestScenario.targetToAnotherTarget;

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

}
