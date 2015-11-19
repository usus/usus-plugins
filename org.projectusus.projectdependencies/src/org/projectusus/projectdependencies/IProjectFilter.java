
package org.projectusus.projectdependencies;

import org.eclipse.core.resources.IProject;

public interface IProjectFilter {

	boolean accept(IProject project);

}
