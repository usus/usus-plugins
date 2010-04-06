package org.projectusus.ui.dependencygraph;

import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;

public class ClassGraphView extends ViewPart {

	private static final String SPINNER_TOOLTIP_TEXT = "Threshold for product of incoming and outgoing edges";
	private static final String SPINNER_TEXT = "Threshold ";
	private IGraphModelListener listener;
	private GraphViewer graphViewer;
	private GraphModel model;

	public ClassGraphView() {
		model = GraphModel.getInstance();
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));

		createFilterArea(composite);
		createGraphArea(composite);

		initModelListener();
		drawGraph();
	}

	private void createGraphArea(Composite composite) {
		Composite graphArea = new Composite(composite, SWT.BORDER);
		graphArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		graphArea.setLayout(new FillLayout());
		graphViewer = new GraphViewer(graphArea, SWT.NONE);
		graphViewer.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);
		graphViewer.setContentProvider(new ClassNodeContentProvider());
		graphViewer.setLabelProvider(new ClassNodeLabelProvider());
		SpringLayoutAlgorithm layoutAlgorithm = new SpringLayoutAlgorithm(
				LayoutStyles.NO_LAYOUT_NODE_RESIZING);
		graphViewer.setFilters(new ViewerFilter[] { new ClassNodeFilter() });

		graphViewer.setLayoutAlgorithm(layoutAlgorithm, true);
	}

	private void createFilterArea(Composite composite) {
		Composite filterArea = new Composite(composite, SWT.BORDER);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		filterArea.setLayout(layout);
		Label filterText = new Label(filterArea, SWT.NONE);
		filterText.setToolTipText(SPINNER_TOOLTIP_TEXT);
		filterText.setText(SPINNER_TEXT);

		final Spinner spinner = new Spinner(filterArea, SWT.BORDER);
		spinner.setSelection(model.getMinimumEdges());
		spinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				String spinnerText = spinner.getText();
				if (spinnerText != null && spinnerText.length() > 0) {
					GraphModel.getInstance().setMinimumEdges(
							Integer.valueOf(spinnerText));
					refresh();
				}
			}
		});
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
		model.addAcdModelListener(listener);

	}

	public void refresh() {
		drawGraph();
	}

	private void drawGraph() {
		graphViewer.setInput(model.getRawData());
	}

	@Override
	public void setFocus() {
	}

}
