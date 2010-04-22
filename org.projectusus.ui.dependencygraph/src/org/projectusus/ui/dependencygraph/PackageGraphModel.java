package org.projectusus.ui.dependencygraph;

import java.util.Set;

import org.projectusus.core.internal.UsusCorePlugin;
import org.projectusus.core.internal.proportions.rawdata.PackageRepresenter;

public class PackageGraphModel implements FilterLimitProvider {

	public int getFilterLimit() {
		return 0;
	}

	public Set<PackageRepresenter> getPackageRepresenters() {
		return UsusCorePlugin.getUsusModel().getAllPackages();
	}


}
