package org.projectusus.core.filerelations;

import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;
import static org.projectusus.core.filerelations.IsSetOfMatcher.isEmptySetOf;
import static org.projectusus.core.filerelations.IsSetOfMatcher.isSetOf;
import static org.projectusus.core.filerelations.TestServiceManager.createDescriptor;
import static org.projectusus.core.filerelations.TestServiceManager.createFileMock;

import org.eclipse.core.resources.IFile;
import org.junit.Test;

public class FileRelationsTest {

	private FileRelations fileRelations = new FileRelations();
	private IFile source = createFileMock();
	private IFile target = createFileMock();
	private IFile anotherTarget = createFileMock();
	private Classname sourceName = new Classname("sourceName");
	private Classname targetName = new Classname("targetName");
	private Classname anotherTargetName = new Classname("anotherTargetName");
	private FileRelation sourceToTarget = new FileRelation(createDescriptor(source, sourceName), createDescriptor(target, targetName));

	private FileRelation sourceToAnotherTarget = new FileRelation(createDescriptor(source, sourceName), createDescriptor(anotherTarget, anotherTargetName));
	private FileRelation targetToAnotherTarget = new FileRelation(createDescriptor(target, targetName), createDescriptor(anotherTarget, anotherTargetName));
	private FileRelation anotherTargetToTarget = new FileRelation(createDescriptor(anotherTarget, anotherTargetName), createDescriptor(target, targetName));
	private FileRelation anotherTargetToSource = new FileRelation(createDescriptor(anotherTarget, anotherTargetName), createDescriptor(source, sourceName));

	@Test
	public void shouldAddAndRetrieveSingleFileRelation() {
		fileRelations.add(sourceToTarget);
		fileRelations.add(sourceToTarget);
		assertThat(fileRelations.getDirectRelationsFrom(source), isSetOf(sourceToTarget));
		assertThat(fileRelations.getDirectRelationsFrom(target), isEmptySetOf(FileRelation.class));
		assertThat(fileRelations.getDirectRelationsTo(source), isEmptySetOf(FileRelation.class));
		assertThat(fileRelations.getDirectRelationsTo(target), isSetOf(sourceToTarget));
	}

	@Test
	public void shouldAddAndRetrieveMultipleFileRelations() {
		fileRelations.add(sourceToTarget);
		fileRelations.add(sourceToAnotherTarget);
		assertThat(fileRelations.getDirectRelationsFrom(source), isSetOf(sourceToTarget, sourceToAnotherTarget));
		assertThat(fileRelations.getDirectRelationsFrom(target), isEmptySetOf(FileRelation.class));
		assertThat(fileRelations.getDirectRelationsFrom(anotherTarget), isEmptySetOf(FileRelation.class));
		assertThat(fileRelations.getDirectRelationsTo(source), isEmptySetOf(FileRelation.class));
		assertThat(fileRelations.getDirectRelationsTo(target), isSetOf(sourceToTarget));
		assertThat(fileRelations.getDirectRelationsTo(anotherTarget), isSetOf(sourceToAnotherTarget));
	}
	
	@Test
	public void shouldRemoveAllOutgoingRelations() {
		fileRelations.add(sourceToTarget);
		fileRelations.removeDirectRelationsFrom(source);
		assertThat(fileRelations.getDirectRelationsFrom(source), isEmptySetOf(FileRelation.class));
		assertThat(fileRelations.getDirectRelationsTo(target), isSetOf(sourceToTarget));
	}
	
	@Test
	public void shouldRemoveOutgoingRelationsOfOneFile() {
		fileRelations.add(sourceToTarget);
		fileRelations.add(sourceToAnotherTarget);
		fileRelations.add(targetToAnotherTarget);
		fileRelations.removeDirectRelationsFrom(target);
		assertThat(fileRelations.getDirectRelationsFrom(source), isSetOf(sourceToTarget, sourceToAnotherTarget));
		assertThat(fileRelations.getDirectRelationsFrom(target), isEmptySetOf(FileRelation.class));
		assertThat(fileRelations.getDirectRelationsTo(target), isSetOf(sourceToTarget));
		assertThat(fileRelations.getDirectRelationsTo(anotherTarget), isSetOf(targetToAnotherTarget, sourceToAnotherTarget));
	}
	
	@Test
	public void transitiveRelationsFromSingleRelation() throws Exception {
		fileRelations.add(sourceToTarget);
		assertThat(fileRelations.getTransitiveRelationsFrom(source, sourceName), isSetOf(sourceToTarget));
	}
	
	@Test
	public void transitiveRelationsFromTwoRelations() throws Exception {
		fileRelations.add(sourceToTarget);
		fileRelations.add(targetToAnotherTarget);
		assertThat(fileRelations.getTransitiveRelationsFrom(source, sourceName), isSetOf(sourceToTarget, targetToAnotherTarget));
	}
	
	@Test
	public void transitiveRelationsFromCyclicRelationsBelowStart() throws Exception {
		fileRelations.add(sourceToTarget);
		fileRelations.add(targetToAnotherTarget);
		fileRelations.add(anotherTargetToTarget);
		assertThat(fileRelations.getTransitiveRelationsFrom(source, sourceName), isSetOf(sourceToTarget, targetToAnotherTarget, anotherTargetToTarget));
	}

	@Test
	public void transitiveRelationsFromCyclicRelationsIncludingStart() throws Exception {
		fileRelations.add(sourceToTarget);
		fileRelations.add(targetToAnotherTarget);
		fileRelations.add(anotherTargetToTarget);
		fileRelations.add(anotherTargetToSource);
		assertThat(fileRelations.getTransitiveRelationsFrom(source, sourceName), isSetOf(sourceToTarget, targetToAnotherTarget, anotherTargetToTarget, anotherTargetToSource));
	}
	
}
