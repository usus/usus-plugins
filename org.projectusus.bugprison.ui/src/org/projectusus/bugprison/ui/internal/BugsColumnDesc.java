// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.bugprison.ui.internal;

import static org.projectusus.ui.viewer.ColumnAlignment.RIGHT;

import org.projectusus.bugprison.core.Bug;
import org.projectusus.ui.viewer.IColumnDesc;
import org.projectusus.ui.viewer.UsusTreeColumn;

enum BugsColumnDesc implements IColumnDesc<Bug> {

	@UsusTreeColumn(weight = 15)
	COVERED("Title") {
		public String getLabel(Bug bug) {
			return bug.getTitle();
		}
	},
	@UsusTreeColumn(weight = 40)
	PACKAGE("Package") {
		public String getLabel(Bug bug) {
			return bug.getLocation().getPackageName();
		}
	},
	@UsusTreeColumn(weight = 20)
	CLASS_NAME("Classname") {
		public String getLabel(Bug bug) {
			return bug.getLocation().getClassName();
		}
	},
	@UsusTreeColumn(weight = 20)
	METHOD_NAME("Methodname") {
		public String getLabel(Bug bug) {
			return bug.getLocation().getMethodName();
		}
	},
	@UsusTreeColumn(weight = 20)
	METHOD_LENGTH("Method length") {
		public String getLabel(Bug bug) {
			return String.valueOf(bug.getBugMetrics().getMethodLength());
		}
	},
	@UsusTreeColumn(align = RIGHT, weight = 20)
	CYCLOMATIC_COMPLEXITY("Cyclomatic complexity") {
		public String getLabel(Bug bug) {
			return String
					.valueOf(bug.getBugMetrics().getCyclomaticComplexity());
		}
	},
	@UsusTreeColumn(align = RIGHT, weight = 20)
	NUMBER_OF_METHODS_IN_CLASS("# methods in class") {
		public String getLabel(Bug bug) {
			return String.valueOf(bug.getBugMetrics().getNumberOfMethods());
		}
	};

	private String header;

	BugsColumnDesc(String header) {
		this.header = header;
	}

	public String getHeader() {
		return header;
	}

	public boolean hasImage() {
		return false;
	}
}
