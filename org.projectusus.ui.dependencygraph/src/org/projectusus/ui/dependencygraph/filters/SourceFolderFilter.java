package org.projectusus.ui.dependencygraph.filters;

import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.projectusus.ui.dependencygraph.nodes.GraphNode;

public class SourceFolderFilter extends NodeAndEdgeFilter {

    private final String sourceFolder;

    public SourceFolderFilter( String sourceFolder ) {
        this.sourceFolder = sourceFolder;
    }

    @Override
    public String getDescription() {
        return "Show only classes in source folder " + sourceFolder;
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
//        System.out.println( relativePath + ": " + relativePath.equals( Path.fromPortableString( sourceFolder ) ) );

        return true;
    }
}
