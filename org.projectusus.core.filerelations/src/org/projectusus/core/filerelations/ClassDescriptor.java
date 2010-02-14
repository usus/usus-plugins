package org.projectusus.core.filerelations;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.eclipse.core.resources.IFile;

public class ClassDescriptor {

	private IFile file;
	private Classname classname;
	private Packagename packagename;
	
	public ClassDescriptor(IFile file, Classname classname,
			Packagename packagename) {
		this.file = file;
		this.classname = classname;
		this.packagename = packagename;
	}

	public IFile getFile() {
		return file;
	}

	public Classname getClassname() {
		return classname;
	}
	
	@Override
	public boolean equals(Object object) {
		return object instanceof ClassDescriptor && equals((ClassDescriptor) object);
	}
	
	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		builder.append(file).append(classname).append(packagename);
		return builder.toHashCode();
	}

	private boolean equals(ClassDescriptor other) {
		EqualsBuilder builder = new EqualsBuilder();
		builder.append(file, other.file);
		builder.append(classname, other.classname);
		builder.append(packagename, other.packagename);
		return builder.isEquals();
	}
}
