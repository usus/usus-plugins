// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.hotspots;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.projectusus.core.internal.proportions.sqi.IsisMetrics.ML;

import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.junit.Test;
import org.projectusus.core.internal.proportions.model.CodeProportion;

public class ExtractHotspotsPagePDETest {
	
	@Test
	public void nonStructuredSelectionYieldsNull() {
		assertNull(new ExtractHotspotsPage(newSelection(false)).compute());
		assertNull(new ExtractHotspotsPage(newSelection(true)).compute());
	}
	
	@Test
	public void emptySelectionYieldsNull() {
		assertNull(new ExtractHotspotsPage(new StructuredSelection()).compute());
	}
	
	@Test
	public void noPageForOtherAdaptableThanCodeProportion() {
		Object platformObject = new PlatformObject() {};
		StructuredSelection selection = new StructuredSelection(platformObject);
		assertNull(new ExtractHotspotsPage(selection).compute());
	}
	
	@Test
	public void pageForCodeProportion() {
		CodeProportion codeProportion = new CodeProportion(ML);
		StructuredSelection selection = new StructuredSelection(codeProportion);
		assertNotNull(new ExtractHotspotsPage(selection).compute());
	}

	private ISelection newSelection(final boolean empty) {
		return new ISelection() {
			public boolean isEmpty() {
				return empty;
			}
		};
	}
}
