package org.projectusus.ui.dependencygraph;

import java.util.Set;

import org.projectusus.core.internal.UsusCorePlugin;
import org.projectusus.core.internal.proportions.rawdata.ClassRepresenter;

public class ClassGraphModel implements FilterLimitProvider {

	private int minimumEdges;

	public ClassGraphModel() {
		minimumEdges = 2;
	}

	public Set<ClassRepresenter> getClassRepresenters() {
		return UsusCorePlugin.getUsusModel().getAllClassRepresenters();
	}

	public void setMinimumEdges(Integer minimumEdges) {
		this.minimumEdges = minimumEdges;
	}

	public int getFilterLimit() {
		return minimumEdges;
	}

}
