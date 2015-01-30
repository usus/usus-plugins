package org.projectusus.ui.dependencygraph.sourcefolder;

import static org.apache.commons.lang.StringUtils.join;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.projectusus.ui.dependencygraph.filters.NodeAndEdgeFilter;
import org.projectusus.ui.dependencygraph.nodes.GraphNode;

public class SourceFolderFilter extends NodeAndEdgeFilter {

    private List<IPath> allSourceFolders = new ArrayList<IPath>();
    private List<IPath> visibleSourceFolders = new ArrayList<IPath>();

    public List<IPath> getAllSourceFolders() {
        return allSourceFolders;
    }

    public void updateSourceFolders( List<IPath> allSourceFolders ) {
        this.visibleSourceFolders.retainAll( allSourceFolders );
        this.visibleSourceFolders.addAll( addedSourceFolders( allSourceFolders ) );
        this.allSourceFolders = new LinkedList<IPath>( allSourceFolders );
    }

    private LinkedList<IPath> addedSourceFolders( List<IPath> newSourceFolders ) {
        List<IPath> oldSourceFolders = this.allSourceFolders;
        LinkedList<IPath> result = new LinkedList<IPath>( newSourceFolders );
        result.removeAll( oldSourceFolders );
        return result;
    }

    public List<IPath> getVisibleSourceFolders() {
        return visibleSourceFolders;
    }

    public void setVisibleSourceFolders( List<IPath> visibleSourceFolders ) {
        this.visibleSourceFolders = visibleSourceFolders;
    }

    @Override
    public String getDescription() {
        return "Show only classes in source folders " + join( visibleSourceFolders, ", " );
    }

    @Override
    protected boolean select( GraphNode node, Set<GraphNode> others ) {
        IJavaElement javaElement = JavaCore.create( node.getFile() );
        IJavaElement packageFragmentRoot = javaElement.getAncestor( IJavaElement.PACKAGE_FRAGMENT_ROOT );
        IJavaProject javaProject = packageFragmentRoot.getJavaProject();

        IPath packagePath = packageFragmentRoot.getPath();
        IPath projectPath = javaProject.getPath();
        IPath relativePath = packagePath.makeRelativeTo( projectPath );

        return visibleSourceFolders.contains( relativePath );
    }

    public boolean isFiltering() {
        return getAllSourceFolders().size() > getVisibleSourceFolders().size();
    }
}
