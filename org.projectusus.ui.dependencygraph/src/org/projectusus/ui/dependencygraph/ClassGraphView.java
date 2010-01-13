package org.projectusus.ui.dependencygraph;

import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;

public class ClassGraphView extends ViewPart {

	private Composite composite;
	private IGraphModelListener listener;
	private GraphViewer graphViewer;

	@Override
	public void createPartControl(Composite parent) {
		composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout());
		composite.setSize(400, 400);
		graphViewer = new GraphViewer(composite, SWT.NONE);
		graphViewer.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);
		graphViewer.setContentProvider(new ClassNodeContentProvider());
		graphViewer.setLabelProvider(new ClassNodeLabelProvider());
		graphViewer.setInput(GraphModel.getInstance().getRawData());
		SpringLayoutAlgorithm layoutAlgorithm = new SpringLayoutAlgorithm(
				LayoutStyles.NO_LAYOUT_NODE_RESIZING);
		graphViewer.setFilters(new ViewerFilter[] { new ClassNodeFilter() });

		graphViewer.setLayoutAlgorithm(layoutAlgorithm, true);
		initModelListener();
		drawGraph();
	}

	private void initModelListener() {
		listener = new IGraphModelListener() {
			public void ususModelChanged() {
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						refresh();
					}
				});
			}

		};
		GraphModel.getInstance().addAcdModelListener(listener);

	}

	public void refresh() {
		drawGraph();
	}

	private void drawGraph() {
		graphViewer.setInput(GraphModel.getInstance().getRawData());
	}

	@Override
	public void setFocus() {
	}

}
