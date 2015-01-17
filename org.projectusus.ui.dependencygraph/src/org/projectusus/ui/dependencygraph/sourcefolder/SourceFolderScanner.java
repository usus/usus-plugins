package org.projectusus.ui.dependencygraph.sourcefolder;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;

public class SourceFolderScanner {

    public Set<IPath> scan( IJavaProject... projects ) throws JavaModelException {
        Set<IPath> paths = new HashSet<IPath>();
        for( IJavaProject project : projects ) {
            paths.addAll( scanProject( project ) );
        }
        return paths;
    }

    private Set<IPath> scanProject( IJavaProject project ) throws JavaModelException {
        Set<IPath> paths = new HashSet<IPath>();
        for( IPackageFragmentRoot packageFragmentRoot : project.getPackageFragmentRoots() ) {
            IPath sourceFolderPath = makeRelativeToBasePath( packageFragmentRoot.getPath(), project.getPath() );
            paths.add( sourceFolderPath );
        }
        return paths;
    }

    private IPath makeRelativeToBasePath( IPath deepPath, IPath basePath ) {
        return deepPath.makeRelativeTo( basePath );
    }

}
