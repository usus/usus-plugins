// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.nasty.ui.internal.badthings;

import static org.projectusus.nasty.ui.internal.util.TracingOption.NUCLEAR_STRIKE;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.refactoring.IJavaRefactorings;
import org.eclipse.jdt.core.refactoring.descriptors.RenameJavaElementDescriptor;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringContribution;
import org.eclipse.ltk.core.refactoring.RefactoringCore;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

class SwitchNamesRefactoring {

    private final IMethod target;
    private final IMethod nameSwitchTarget;

    SwitchNamesRefactoring( WSAnalysisResult analyzed ) {
        this.target = analyzed.getNukeTarget();
        this.nameSwitchTarget = analyzed.getNameSwitchTarget();
    }

    void execute() {
        rename( target, nameSwitchTarget.getElementName() );
        rename( nameSwitchTarget, target.getElementName() );
    }

    private void rename( IMethod methodToRename, String newName ) {
        try {
            RefactoringStatus status = new RefactoringStatus();
            Refactoring refactoring = mkRefactoring( status, methodToRename, newName );
            if( status.isOK() ) {
                if( refactoring.checkAllConditions( nullMonitor() ).isOK() ) {
                    Change change = refactoring.createChange( nullMonitor() );
                    NUCLEAR_STRIKE.trace( "Nuking :-) \nFrom: " + methodToRename + "\nTo  :" + newName );
                    change.perform( nullMonitor() );
                }
            }
        } catch( CoreException cex ) {
            NUCLEAR_STRIKE.trace( cex );
        }
    }

    private Refactoring mkRefactoring( RefactoringStatus status, IMethod methodToRename, String newName ) throws CoreException {
        RenameJavaElementDescriptor desc = loadDescriptor();
        desc.setJavaElement( methodToRename );
        desc.setNewName( newName );
        return desc.createRefactoring( status );
    }

    private RenameJavaElementDescriptor loadDescriptor() {
        String id = IJavaRefactorings.RENAME_METHOD;
        RefactoringContribution contrib = RefactoringCore.getRefactoringContribution( id );
        return (RenameJavaElementDescriptor)contrib.createDescriptor();
    }

    private IProgressMonitor nullMonitor() {
        return new NullProgressMonitor();
    }
}
