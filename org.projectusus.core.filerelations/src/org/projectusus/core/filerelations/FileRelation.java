package org.projectusus.core.filerelations;

import org.eclipse.core.resources.IFile;

public class FileRelation {
	
	private final ClassDescriptor source;
	private final ClassDescriptor target;

	public FileRelation(ClassDescriptor sourceFile, ClassDescriptor targetFile) {
		this.source = sourceFile;
		this.target = targetFile;
	}

	public IFile getSourceFile() {
		return source.getFile();
	}

	public IFile getTargetFile() {
		return target.getFile();
	}

	public Classname getSourceClassname() {
		return source.getClassname();
	}

	public Classname getTargetClassname() {
		return target.getClassname();
	}

	public ClassDescriptor getSourceDescriptor() {
		return source;
	}

	public ClassDescriptor getTargetDescriptor() {
		return target;
	}
	
	

}
