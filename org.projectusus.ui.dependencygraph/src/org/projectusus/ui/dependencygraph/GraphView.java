package org.projectusus.ui.dependencygraph;

import java.util.Set;

import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.projectusus.core.internal.proportions.rawdata.GraphNode;

public abstract class GraphView extends ViewPart {

	private GraphViewer graphViewer;

	@Override
	public void createPartControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));

		createFilterArea(composite);
		createGraphArea(composite);

		drawGraph();
	}

	protected abstract void createFilterArea(Composite composite);

	protected void createGraphArea(Composite composite) {
		Composite graphArea = new Composite(composite, SWT.BORDER);
		graphArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		graphArea.setLayout(new FillLayout());
		graphViewer = new GraphViewer(graphArea, SWT.NONE);
		graphViewer.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);
		graphViewer.setContentProvider(new NodeContentProvider());
		graphViewer.setLabelProvider(new NodeLabelProvider());
		SpringLayoutAlgorithm layoutAlgorithm = new SpringLayoutAlgorithm(
				LayoutStyles.NO_LAYOUT_NODE_RESIZING);
		graphViewer.setFilters(new ViewerFilter[] { new NodeFilter(getFilterLimitProvider()) });
		// graphViewer.setInput(model.getRawData());
		// TODO sollte eigentlich hier hin

		graphViewer.setLayoutAlgorithm(layoutAlgorithm, true);
	}


	protected abstract FilterLimitProvider getFilterLimitProvider();

	public void refresh() {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				drawGraph();
			}
		});
	}

	private void drawGraph() {
		graphViewer.setInput(getGraphNodes());
		// graphViewer.resetFilters();
		// graphViewer.refresh();
	}
	
	protected abstract Set<? extends GraphNode> getGraphNodes();

	@Override
	public void setFocus() {
	}


}
