package org.projectusus.ui.dependencygraph.sourcefolder;

import static org.eclipse.jdt.core.IPackageFragmentRoot.K_SOURCE;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;

public class SourceFolderScanner {

    public Set<IPath> scan( IJavaProject... projects ) throws JavaModelException {
        Set<IPath> paths = newSet();
        for( IJavaProject project : projects ) {
            paths.addAll( scanProject( project ) );
        }
        return paths;
    }

    private Set<IPath> scanProject( IJavaProject project ) throws JavaModelException {
        Set<IPath> paths = newSet();
        // TODO aOSD Für Nicht-Java-Projekte fliegt hier eine Java Model Exception (nachstellen mit org.projectusus-feature)
        for( IPackageFragmentRoot packageFragmentRoot : project.getPackageFragmentRoots() ) {
            if( isSourceFolder( packageFragmentRoot ) ) {
                IPath sourceFolderPath = makeRelativeToBasePath( packageFragmentRoot.getPath(), project.getPath() );
                paths.add( sourceFolderPath );
            }
        }
        return paths;
    }

    private boolean isSourceFolder( IPackageFragmentRoot packageFragmentRoot ) throws JavaModelException {
        return packageFragmentRoot.getKind() == K_SOURCE;
    }

    private IPath makeRelativeToBasePath( IPath deepPath, IPath basePath ) {
        return deepPath.makeRelativeTo( basePath );
    }

    private final static Comparator<IPath> PATH_COMPARATOR = new Comparator<IPath>() {
        public int compare( IPath path1, IPath path2 ) {
            return path1.toPortableString().compareTo( path2.toPortableString() );
        }
    };

    private <T extends IPath> Set<T> newSet() {
        return new TreeSet<T>( PATH_COMPARATOR );
    }
}
