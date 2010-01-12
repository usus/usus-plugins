package org.projectusus.ui.dependencygraph;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

/**
 * Refresh action for acd view.
 * 
 * @author Joachim Meyer - meyer.joachim@gmail.com
 * 
 */
public class RefreshAcdView implements IViewActionDelegate {

	public void run(IAction arg0) {
		AcdModel.getInstance().update();
	}

	public void init(IViewPart arg0) {
		// unused
	}

	public void selectionChanged(IAction arg0, ISelection arg1) {
		// unused
	}

}
