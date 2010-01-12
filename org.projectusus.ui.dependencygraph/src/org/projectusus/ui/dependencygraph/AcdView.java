package org.projectusus.ui.dependencygraph;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;

public class AcdView extends ViewPart {

	private Composite composite;
	private IAcdModelListener listener;
	private GraphViewer graphViewer;

	@Override
	public void createPartControl(Composite parent) {
		composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout());
		composite.setSize(400, 400);
		graphViewer = new GraphViewer(composite, SWT.NONE);
		graphViewer.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);
		graphViewer.setContentProvider(new AcdNodeContentProvider());
		graphViewer.setLabelProvider(new AcdLabelProvider());
		graphViewer.setInput(AcdModel.getInstance().getRawData());
		SpringLayoutAlgorithm layoutAlgorithm = new SpringLayoutAlgorithm(
				LayoutStyles.NO_LAYOUT_NODE_RESIZING);
		graphViewer.setLayoutAlgorithm(layoutAlgorithm, true);
		initModelListener();
		drawGraph();
	}

	private void initModelListener() {
		listener = new IAcdModelListener() {
			public void ususModelChanged() {
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						refresh();
					}
				});
			}

		};
		AcdModel.getInstance().addAcdModelListener(listener);

	}

	public void refresh() {
		drawGraph();
	}

	private void drawGraph() {
		graphViewer.setInput(AcdModel.getInstance().getRawData());
	}

	@Override
	public void setFocus() {
	}

}
