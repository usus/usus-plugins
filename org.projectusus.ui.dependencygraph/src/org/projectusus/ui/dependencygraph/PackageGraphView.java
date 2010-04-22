package org.projectusus.ui.dependencygraph;

import java.util.Set;

import org.eclipse.swt.widgets.Composite;
import org.projectusus.core.internal.proportions.rawdata.GraphNode;

public class PackageGraphView extends GraphView {

	private PackageGraphModel model;
	
	public PackageGraphView() {
		model = new PackageGraphModel();
	}
	
	@Override
	protected void createFilterArea(Composite composite) {
		// nothing to do so far
	}

	@Override
	protected FilterLimitProvider getFilterLimitProvider() {
		return model;
	}

	@Override
	protected Set<? extends GraphNode> getGraphNodes() {
		return model.getPackageRepresenters();
	}

}
