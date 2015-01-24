package org.projectusus.ui.dependencygraph.sourcefolder;

import static org.projectusus.core.project2.UsusProjectSupport.asUsusProject;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IJavaProject;

public class SourceFolderChangeDetector {

    public static boolean detectSourceFolderChange( IJavaElementDelta delta ) {
        return new SourceFolderChangeDetector().analyze( delta );
    }

    public boolean analyze( IJavaElementDelta delta ) {
        int elementType = delta.getElement().getElementType();
        if( elementType == IJavaElement.PACKAGE_FRAGMENT_ROOT ) {
            return true;
        }

        if( elementType == IJavaElement.JAVA_MODEL ) {
            return checkModel( delta );
        }

        if( elementType == IJavaElement.JAVA_PROJECT ) {
            if( isCoveredProject( delta.getElement() ) ) {
                return checkProject( delta );
            }
        }

        return false;
    }

    private boolean isCoveredProject( IJavaElement javaElement ) {
        return asUsusProject( ((IJavaProject)javaElement).getProject() ).isUsusProject();
    }

    private boolean checkModel( IJavaElementDelta delta ) {
        return checkAll( delta.getChangedChildren() );
    }

    private boolean checkProject( IJavaElementDelta delta ) {
        return checkAll( delta.getAddedChildren() ) || checkAll( delta.getRemovedChildren() );
    }

    private boolean checkAll( IJavaElementDelta[] deltas ) {
        for( IJavaElementDelta delta : deltas ) {
            if( analyze( delta ) ) {
                return true;
            }
        }
        return false;
    }

}
