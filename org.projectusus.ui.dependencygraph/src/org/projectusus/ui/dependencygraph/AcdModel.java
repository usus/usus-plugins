package org.projectusus.ui.dependencygraph;

import java.util.HashSet;
import java.util.Set;

import org.projectusus.core.internal.proportions.rawdata.ClassRawData;
import org.projectusus.core.internal.proportions.rawdata.WorkspaceRawData;

public class AcdModel {

	private static AcdModel instance;
	private final Set<IAcdModelListener> listeners;

	private AcdModel() {
		listeners = new HashSet<IAcdModelListener>();
	}

	public static AcdModel getInstance() {
		if (instance == null) {
			instance = new AcdModel();
		}
		return instance;
	}

	public void addAcdModelListener(IAcdModelListener listener) {
		listeners.add(listener);
	}

	public void removeAcdModelListener(IAcdModelListener listener) {
		listeners.remove(listener);
	}

	public Set<ClassRawData> getRawData() {
		return WorkspaceRawData.getInstance().getAllClassRawData();
	}

	public void update() {
		notifyListeners();
	}

	private void notifyListeners() {
		for (IAcdModelListener listener : listeners) {
			listener.ususModelChanged();
		}
	}

}
