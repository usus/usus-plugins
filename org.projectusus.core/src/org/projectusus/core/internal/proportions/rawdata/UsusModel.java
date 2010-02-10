// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.projectusus.core.internal.proportions.IUsusModel;
import org.projectusus.core.internal.proportions.IUsusModelListener;
import org.projectusus.core.internal.proportions.IUsusModelWriteAccess;
import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.core.internal.proportions.model.IUsusElement;
import org.projectusus.core.internal.proportions.model.UsusModelRootNode;
import org.projectusus.core.internal.proportions.modelupdate.IUsusModelHistory;
import org.projectusus.core.internal.proportions.modelupdate.IUsusModelUpdate;
import org.projectusus.core.internal.proportions.modelupdate.UsusModelHistory;

public class UsusModel implements IUsusModel, IUsusModelWriteAccess {

    private final Set<IUsusModelListener> listeners;
    private final UsusModelHistory history;
    private final UsusModelRootNode rootNode;
    private final WorkspaceRawData workspaceRawData;

    public UsusModel() {
        rootNode = new UsusModelRootNode();
        listeners = new HashSet<IUsusModelListener>();
        history = new UsusModelHistory();
        workspaceRawData = new WorkspaceRawData();
    }

    // interface of IUsusModelWriteAccess
    // //////////////////////////////////

    public void update( IUsusModelUpdate updateCommand ) {
        if( updateCommand == null || updateCommand.getType() == null ) {
            throw new IllegalArgumentException();
        }
        doUpdate( updateCommand );
    }

    // interface of IUsusModel
    // ////////////////////////

    public IUsusModelHistory getHistory() {
        return history;
    }

    public IUsusElement[] getElements() {
        return rootNode.getElements();
    }

    // Methoden, die auf WorkspaceRawData zugreifen:
    public CodeProportion getCodeProportion( CodeProportionKind metric ) {
        return workspaceRawData.getCodeProportion( metric );
    }

    public int getNumberOf( CodeProportionUnit unit ) {
        return workspaceRawData.getNumberOf( unit );
    }

    public int getOverallMetric( CodeProportionKind metric ) {
        return workspaceRawData.getOverallMetric( metric );
    }

    public void dropRawData( IProject project ) {
        workspaceRawData.dropRawData( project );
    }

    public void dropRawData( IFile file ) {
        workspaceRawData.dropRawData( file );
    }

    public ProjectRawData getProjectRawData( IProject project ) {
        return workspaceRawData.getProjectRawData( project );
    }

    public List<Integer> getAllClassesCCDResults() {
        Set<ClassRawData> classes = workspaceRawData.getAllClassRawData();
        List<Integer> ccdList = new ArrayList<Integer>();
        for( ClassRawData node : classes ) {
            ccdList.add( new Integer( node.getCCDResult() ) );
        }
        return ccdList;
    }

    public int getSumOfAllDirectChildrenOfAllClasses() {
        int sumDirectChildren = 0;
        for( ClassRawData clazz : workspaceRawData.getAllClassRawData() ) {
            sumDirectChildren += clazz.getChildren().size();
        }
        return sumDirectChildren;
    }

    public int getSumOfAllKnownChildrenOfAllClasses() {
        int sumKnownClasses = 0;
        for( ClassRawData clazz : workspaceRawData.getAllClassRawData() ) {
            sumKnownClasses += clazz.getAllChildren().size();
        }
        return sumKnownClasses;
    }

    public void resetRawData( IProject project ) {
        workspaceRawData.resetRawData( project );
    }

    // //////////////////////////////////

    public void addUsusModelListener( IUsusModelListener listener ) {
        listeners.add( listener );
    }

    public void removeUsusModelListener( IUsusModelListener listener ) {
        listeners.remove( listener );
    }

    // internal
    // ////////

    private void notifyListeners() {
        IUsusElement[] elements = getElements();
        for( IUsusModelListener listener : listeners ) {
            listener.ususModelChanged( history, asList( elements ) );
        }
    }

    private void doUpdate( IUsusModelUpdate updateCommand ) {
        for( CodeProportion entry : updateCommand.getEntries() ) {
            rootNode.add( entry );
        }
        history.add( updateCommand );
        notifyListeners();
    }
}
