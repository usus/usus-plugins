package org.projectusus.ui.dependencygraph.action;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.projectusus.ui.dependencygraph.ClassGraphView;

public class RefreshGraphView implements IViewActionDelegate {

	private ClassGraphView classGraphView;

	public void run(IAction arg0) {
		classGraphView.refresh();
	}

	public void init(IViewPart viewPart) {
		this.classGraphView = (ClassGraphView) viewPart;
	}

	public void selectionChanged(IAction arg0, ISelection arg1) {
		// unused
	}

}
