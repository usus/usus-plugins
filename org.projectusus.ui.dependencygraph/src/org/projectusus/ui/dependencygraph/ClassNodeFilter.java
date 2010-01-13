package org.projectusus.ui.dependencygraph;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.projectusus.core.internal.proportions.rawdata.ClassRawData;

public class ClassNodeFilter extends ViewerFilter {

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof ClassRawData) {
			ClassRawData classRawData = (ClassRawData) element;
			return (classRawData.getChildren().size()
					+ classRawData.getParents().size() > GraphModel
					.getInstance().getMinimumEdges() - 1);
		}
		return true;
	}
}
