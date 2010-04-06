package org.projectusus.ui.dependencygraph;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.zest.core.viewers.EntityConnectionData;
import org.projectusus.core.internal.proportions.rawdata.ClassRepresenter;

public class ClassNodeLabelProvider extends LabelProvider {
	@Override
	public String getText(Object element) {
		if (element instanceof ClassRepresenter) {
			ClassRepresenter acdNode = (ClassRepresenter) element;
			return acdNode.getClassName();
		}
		if (element instanceof EntityConnectionData) {
			EntityConnectionData data = (EntityConnectionData) element;
			ClassRepresenter dest = (ClassRepresenter) data.dest;
			return String.valueOf(dest.getNumberOfAllChildren());
		}
		throw new RuntimeException("Type not supported: " + element.getClass().toString());
	}
}
