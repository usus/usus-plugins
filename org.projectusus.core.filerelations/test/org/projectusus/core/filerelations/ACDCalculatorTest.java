package org.projectusus.core.filerelations;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.projectusus.core.filerelations.TestServiceManager.asSet;
import static org.projectusus.core.filerelations.TestServiceManager.createDescriptor;
import static org.projectusus.core.filerelations.TestServiceManager.createFileMock;

import java.util.HashSet;

import org.eclipse.core.resources.IFile;
import org.junit.Test;

public class ACDCalculatorTest {

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
	public void calculateCcdOfClassWithoutRelations() throws Exception {
		FileRelations relations = mock(FileRelations.class);
		IFile file = createFileMock();
		Classname clazz = new Classname("MeineKlasse");
		when(relations.getTransitiveRelationsFrom(file, clazz)).thenReturn(new HashSet<FileRelation>());
		
		int ccd = new ACDCalculator(relations).getCCD( createDescriptor(file, clazz) );
		
		assertThat(ccd, is(1));
		verify(relations).getTransitiveRelationsFrom(file, clazz);
	}
	
	@Test
	public void calculateCcdOfClassWithDirectRelations() throws Exception {
		FileRelations relations = mock(FileRelations.class);
		IFile file = createFileMock();
		Classname clazz = new Classname("MeineKlasse");
		FileRelation relation = new FileRelation(createDescriptor(file, clazz), createDescriptor(createFileMock()));
		when(relations.getTransitiveRelationsFrom(file, clazz)).thenReturn(asSet(relation));
		
		int ccd = new ACDCalculator(relations).getCCD( createDescriptor(file, clazz) );
		
		assertThat(ccd, is(2));
		verify(relations).getTransitiveRelationsFrom(file, clazz);
	}
	
	@Test
	public void calculateCcdOfClassWithIndirectRelations() throws Exception {
		
		FileRelations relations = mock(FileRelations.class);
		when(relations.getTransitiveRelationsFrom(source, sourceName)).thenReturn(asSet(sourceToTarget, targetToAnotherTarget));
		
		int ccd = new ACDCalculator(relations).getCCD( createDescriptor(source, sourceName) );
		
		assertThat(ccd, is(3));
		verify(relations).getTransitiveRelationsFrom(source, sourceName);
	}
	
	@Test
	public void calculateCcdOfClassWithIndirectCyclicRelations() throws Exception {
		
		FileRelations relations = mock(FileRelations.class);
		when(relations.getTransitiveRelationsFrom(source, sourceName)).thenReturn(asSet(sourceToTarget, sourceToAnotherTarget, targetToAnotherTarget, anotherTargetToTarget, anotherTargetToSource));
		
		int ccd = new ACDCalculator(relations).getCCD( createDescriptor(source, sourceName) );
		
		assertThat(ccd, is(3));
		verify(relations).getTransitiveRelationsFrom(source, sourceName);
	}
	
}
