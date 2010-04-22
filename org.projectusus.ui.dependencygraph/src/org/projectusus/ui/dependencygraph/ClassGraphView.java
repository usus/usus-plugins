package org.projectusus.ui.dependencygraph;

import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.projectusus.core.internal.proportions.rawdata.GraphNode;

public class ClassGraphView extends GraphView {

	private static final String SPINNER_TOOLTIP_TEXT = "Threshold for product of incoming and outgoing edges";
	private static final String SPINNER_TEXT = "Threshold ";
	private ClassGraphModel model;

	public ClassGraphView() {
		model = new ClassGraphModel();
	}
	
	protected void createFilterArea(Composite composite) {
		Composite filterArea = new Composite(composite, SWT.BORDER);
		filterArea.setLayout(new GridLayout(2, false));
		Label filterText = new Label(filterArea, SWT.NONE);
		filterText.setToolTipText(SPINNER_TOOLTIP_TEXT);
		filterText.setText(SPINNER_TEXT);

		final Spinner spinner = new Spinner(filterArea, SWT.BORDER);
		spinner.setMinimum(0);
		spinner.setMaximum(9999999);
		spinner.setSelection(1);
		spinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				String spinnerText = spinner.getText();
				if (spinnerText != null && spinnerText.length() > 0) {
					model.setMinimumEdges(Integer.valueOf(spinnerText));
					refresh();
				}
			}
		});
	}

	@Override
	protected Set<? extends GraphNode> getGraphNodes() {
		return model.getClassRepresenters();
	}

	@Override
	protected FilterLimitProvider getFilterLimitProvider() {
		return model;
	}


}
