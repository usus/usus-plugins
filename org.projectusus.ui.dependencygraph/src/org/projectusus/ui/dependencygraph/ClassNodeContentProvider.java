package org.projectusus.ui.dependencygraph;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.zest.core.viewers.IGraphEntityContentProvider;
import org.projectusus.core.internal.proportions.rawdata.ClassRepresenter;

public class ClassNodeContentProvider extends ArrayContentProvider implements
		IGraphEntityContentProvider {

	public Object[] getConnectedTo(Object entity) {
		if (entity instanceof ClassRepresenter) {
			ClassRepresenter acdNode = (ClassRepresenter) entity;
			return acdNode.getChildren().toArray();
		}
		throw new RuntimeException("Type not supported");
	}

}
