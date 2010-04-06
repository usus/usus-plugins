package org.projectusus.ui.dependencygraph;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.projectusus.core.internal.proportions.rawdata.ClassRepresenter;

public class ClassNodeFilter extends ViewerFilter {

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
//		if (element instanceof ClassRepresenter) {
//			ClassRepresenter classRawData = (ClassRepresenter) element;
//			return (classRawData.getNumberOfChildren()
//					+ classRawData.getNumberOfParents() > GraphModel
//					.getInstance().getMinimumEdges() - 1);
//		}
		return true;
	}
}
