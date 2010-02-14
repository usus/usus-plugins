package org.projectusus.core.filerelations;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

public class FileRelations {
	
	private final SetMultimap<IFile, FileRelation> incomingRelations;
	private final SetMultimap<IFile, FileRelation> outgoingRelations;
	
	public FileRelations() {
		incomingRelations = HashMultimap.create();
		outgoingRelations = HashMultimap.create();
	}

	public void add(FileRelation relation) {
		incomingRelations.put(relation.getTargetFile(), relation);
		outgoingRelations.put(relation.getSourceFile(), relation);
	}

	public Set<FileRelation> getDirectRelationsFrom(IFile sourceFile) {
		return outgoingRelations.get(sourceFile);
	}

	public Set<FileRelation> getDirectRelationsTo(IFile targetFile) {
		return incomingRelations.get(targetFile);
	}

	public void removeDirectRelationsFrom(IFile sourceFile) {
		outgoingRelations.removeAll(sourceFile);
	}


	public Set<FileRelation> getTransitiveRelationsFrom(IFile file, Classname clazz) {
		Set<FileRelation> transitives = new HashSet<FileRelation>();
		getTransitiveRelationsFrom(file, clazz, transitives);
		return transitives;
	}

	private void getTransitiveRelationsFrom(IFile file, Classname clazz, Set<FileRelation> transitives) {
		for(FileRelation directRelation : outgoingRelations.get(file)){
			if(directRelation.getSourceClassname().equals(clazz) && !transitives.contains(directRelation)){
				transitives.add(directRelation);
				this.getTransitiveRelationsFrom(directRelation.getTargetFile(), directRelation.getTargetClassname(), transitives);
			}
		}
	}

}
