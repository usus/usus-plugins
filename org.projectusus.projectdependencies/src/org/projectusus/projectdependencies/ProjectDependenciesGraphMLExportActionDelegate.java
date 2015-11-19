
package org.projectusus.projectdependencies;

import java.io.File;
import java.io.FileWriter;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.actions.ActionDelegate;
import org.jgrapht.ext.GraphMLExporter;
import org.jgrapht.ext.IntegerEdgeNameProvider;
import org.jgrapht.ext.VertexNameProvider;
import org.projectusus.core.filerelations.model.Relation;
import org.projectusus.core.filerelations.model.RelationGraph;
import org.projectusus.core.filerelations.model.Relations;

public class ProjectDependenciesGraphMLExportActionDelegate extends ActionDelegate
		implements IWorkbenchWindowActionDelegate {

	private Shell shell;
	private IProjectFilter filter = new IProjectFilter() {

		@Override
		public boolean accept(IProject project) {
			return !"c".equals(project.getName());
		}
	};

	@Override
	public void init(IWorkbenchWindow window) {
		shell = window.getShell();
	}

	@Override
	public void run(IAction action) {
		FileDialog dialog = new FileDialog(shell, SWT.SAVE);
		dialog.setFilterExtensions(new String[] { "*.graphml" });
		String result = dialog.open();
		if (result != null) {
			exportSafely(new File(result));
		}
	}

	private void exportSafely(File targetFile) {
		try {
			export(targetFile);
		}
		catch (Exception e) {
			// TODO Handle this properly
			e.printStackTrace();
		}
	}

	public void export(File targetFile) throws Exception {
		RelationGraph<IProject> graph = new RelationGraph<IProject>(buildRelations());
		GraphMLExporter<IProject, Relation<IProject>> graphMLExporter = buildGraphMLExporter();
		graphMLExporter.export(new FileWriter(targetFile), graph);
	}

	private GraphMLExporter<IProject, Relation<IProject>> buildGraphMLExporter() {
		VertexNameProvider<IProject> vertexNameProvider = new VertexNameProvider<IProject>() {

			@Override
			public String getVertexName(IProject project) {
				return project.getName();
			}
		};
		return new GraphMLExporter<IProject, Relation<IProject>>(vertexNameProvider, vertexNameProvider,
			new IntegerEdgeNameProvider<Relation<IProject>>(), null);
	}

	private Relations<IProject> buildRelations() throws CoreException {
		Relations<IProject> relations = new Relations<IProject>();
		Set<IProject> filteredProjects = getFilteredProjects();
		for (IProject sourceProject : filteredProjects) {
			IProject[] referencedProjects = sourceProject.getReferencedProjects();
			for (IProject targetProject : referencedProjects) {
				if (filteredProjects.contains(targetProject)) {
					relations.add(sourceProject, targetProject);
				}
			}
		}
		return relations;
	}

	private Set<IProject> getFilteredProjects() {
		Set<IProject> result = new LinkedHashSet<IProject>();
		IProject[] allProjects = getAllProjects();
		for (IProject project : allProjects) {
			if (filter.accept(project)) {
				result.add(project);
			}
		}
		return result;
	}

	private IProject[] getAllProjects() {
		return ResourcesPlugin.getWorkspace().getRoot().getProjects();
	}

}
