package org.projectusus.ui.dependencygraph.action;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.projectusus.ui.dependencygraph.GraphModel;

public class MinusGraphView implements IViewActionDelegate {
	public void run(IAction arg0) {
		GraphModel.getInstance().decreaseLimit();
	}

	public void init(IViewPart arg0) {
		// unused
	}

	public void selectionChanged(IAction arg0, ISelection arg1) {
		// unused
	}
}
