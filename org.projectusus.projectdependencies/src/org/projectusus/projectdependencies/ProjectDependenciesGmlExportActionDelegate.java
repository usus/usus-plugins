
package org.projectusus.projectdependencies;

import static java.util.Collections.emptyList;

import java.io.File;
import java.io.FileWriter;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionDelegate;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.jgrapht.ext.GmlExporter;
import org.jgrapht.ext.IntegerEdgeNameProvider;
import org.jgrapht.ext.IntegerNameProvider;
import org.jgrapht.ext.VertexNameProvider;
import org.projectusus.core.filerelations.model.Relation;
import org.projectusus.core.filerelations.model.RelationGraph;
import org.projectusus.core.filerelations.model.Relations;

public class ProjectDependenciesGmlExportActionDelegate extends ActionDelegate
		implements IWorkbenchWindowActionDelegate {

	private Shell shell;

	@Override
	public void init(IWorkbenchWindow window) {
		shell = window.getShell();
	}

	@Override
	public void run(IAction action) {
		final List<IWorkingSet> selectedWorkingSets = selectWorkingSets();
		if (selectedWorkingSets.isEmpty()) {
			return;
		}
		FileDialog dialog = new FileDialog(shell, SWT.SAVE);
		dialog.setFilterExtensions(new String[] { "*.gml" });
		String fileName = dialog.open();
		if (fileName != null) {
			exportSafely(new File(fileName), createWorkingSetFilter(selectedWorkingSets));
		}
	}

	private IProjectFilter createWorkingSetFilter(
			final List<IWorkingSet> selectedWorkingSets) {
		return new IProjectFilter() {
			@Override
			public boolean accept(IProject project) {
				for (IWorkingSet workingSet : selectedWorkingSets) {
					if (workingSetContains(workingSet, project)) {
						return true;
					}
				}
				return false;
			}
			
			private boolean workingSetContains(IWorkingSet workingSet, IProject project) {
				for (IAdaptable element : workingSet.getElements()) {
					if (project.equals(element.getAdapter(IProject.class))) {
						return true;
					}
				}
				return false;
			}
		};
	}

	private List<IWorkingSet> selectWorkingSets() {
		ListSelectionDialog selectionDialog = new ListSelectionDialog(shell, PlatformUI.getWorkbench().getWorkingSetManager().getAllWorkingSets(), ArrayContentProvider.getInstance(), new LabelProvider() {
			private ResourceManager images = new LocalResourceManager(JFaceResources.getResources());

		    @Override
			public void dispose() {
		        images.dispose();
		        super.dispose();
		    }

		    @Override
			public Image getImage(Object object) {
		        ImageDescriptor imageDescriptor = ((IWorkingSet) object).getImageDescriptor();
		        return imageDescriptor == null ? null : (Image) images.get(imageDescriptor);
		    } 

		    @Override 
			public String getText(Object object) {
		        return ((IWorkingSet) object).getLabel();
		    }
		}, "Please select working sets to export:");
		selectionDialog.setTitle("Select working sets");
		
		List<IWorkingSet> selectedWorkingSets = emptyList();
		if (selectionDialog.open() == Window.OK) {
			Object[] result = selectionDialog.getResult();
			selectedWorkingSets = new LinkedList<IWorkingSet>();
			for (Object object : result) { 
				selectedWorkingSets.add((IWorkingSet) object);
			}
		}
		return selectedWorkingSets;
	}

	private void exportSafely(File targetFile, IProjectFilter filter) {
		try {
			export(targetFile, filter);
		}
		catch (Exception e) {
			// TODO Handle this properly
			e.printStackTrace();
		}
	}

	public void export(File targetFile, IProjectFilter filter) throws Exception {
		RelationGraph<IProject> graph = new RelationGraph<IProject>(buildRelations(filter));
		GmlExporter<IProject, Relation<IProject>> gmlExporter = buildGmlExporter();
		gmlExporter.export(new FileWriter(targetFile), graph);
	}

	private GmlExporter<IProject, Relation<IProject>> buildGmlExporter() {
		VertexNameProvider<IProject> vertexNameProvider = new VertexNameProvider<IProject>() {

			@Override
			public String getVertexName(IProject project) {
				return project.getName();
			}
		};
		GmlExporter<IProject, Relation<IProject>> exporter = new GmlExporter<IProject, Relation<IProject>>(new IntegerNameProvider<IProject>(), vertexNameProvider,
			new IntegerEdgeNameProvider<Relation<IProject>>(), null);
		exporter.setPrintLabels(GmlExporter.PRINT_VERTEX_LABELS);
		return exporter;
	}

	private Relations<IProject> buildRelations(IProjectFilter filter) throws CoreException {
		Relations<IProject> relations = new Relations<IProject>();
		Set<IProject> filteredProjects = getFilteredProjects(filter);
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

	private Set<IProject> getFilteredProjects(IProjectFilter filter) {
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
