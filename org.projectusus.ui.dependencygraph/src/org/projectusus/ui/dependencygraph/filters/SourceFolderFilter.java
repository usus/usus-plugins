package org.projectusus.ui.dependencygraph.filters;

import static org.apache.commons.lang.StringUtils.join;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.projectusus.ui.dependencygraph.nodes.GraphNode;

public class SourceFolderFilter extends NodeAndEdgeFilter {

    private final List<IPath> allSourceFolders = new ArrayList<IPath>();
    private List<IPath> visibleSourceFolders = new ArrayList<IPath>();

    {
        getAllSourceFolders().add( Path.fromPortableString( "src/main/java" ) );
        getAllSourceFolders().add( Path.fromPortableString( "src/test/java" ) );

        getVisibleSourceFolders().add( Path.fromPortableString( "src/main/java" ) );
    }

    public SourceFolderFilter() {
    }

    public List<IPath> getAllSourceFolders() {
        return allSourceFolders;
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

        IJavaProject javaProject = javaElement.getJavaProject();
        IPackageFragmentRoot packageFragmentRoot = javaProject.getPackageFragmentRoot( node.getFile() );

        IPath packagePath = packageFragmentRoot.getPath();
        IPath projectPath = javaProject.getPath();
        IPath relativePath = packagePath.makeRelativeTo( projectPath );

        // TODO aOSD Richtig implementieren und testen
        // System.out.println( relativePath + ": " + relativePath.equals( Path.fromPortableString( sourceFolder ) ) );

        return true;
    }
}
