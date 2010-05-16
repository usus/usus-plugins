// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.core.IUsusElement;
import org.projectusus.core.IUsusModelListener;
import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.CodeProportionKind;
import org.projectusus.core.basis.CodeProportionUnit;
import org.projectusus.core.filerelations.FileRelationMetrics;
import org.projectusus.core.filerelations.model.BoundType;
import org.projectusus.core.internal.proportions.IUsusModelMetricsWriter;
import org.projectusus.core.internal.proportions.IUsusModelWriteAccess;
import org.projectusus.core.internal.proportions.model.UsusModelCache;
import org.projectusus.core.internal.proportions.modelupdate.checkpoints.CheckpointHistory;

import com.mountainminds.eclemma.core.analysis.IJavaModelCoverage;

public class UsusModel implements IUsusModel, IUsusModelWriteAccess, IUsusModelMetricsWriter {

    private final Set<IUsusModelListener> listeners;
    private final CheckpointHistory history;
    private final UsusModelCache cache;
    private final MetricsAccessor metrics;
    private boolean needsFullRecompute;

    public UsusModel() {
        cache = new UsusModelCache();
        listeners = new HashSet<IUsusModelListener>();
        history = new CheckpointHistory();
        metrics = new MetricsAccessor();
        needsFullRecompute = false;
    }

    // interface of IUsusModelWriteAccess
    // //////////////////////////////////

    public void updateAfterComputationRun( boolean computationSuccessful, IProgressMonitor monitor ) {
        needsFullRecompute = !computationSuccessful;
        metrics.repairRelations( monitor );
        ArrayList<CodeProportion> codeProportions = metrics.getCodeProportions();
        history.addComputationResult( codeProportions );
        cache.refreshAll( codeProportions );
        notifyListeners();
    }

    public void dropRawData( IProject project ) {
        metrics.dropRawData( project );
    }

    public void dropRawData( IFile file ) {
        metrics.dropRawData( file );
    }

    // interface of IUsusModelMetricsWriter
    // /////////////////////////////////////

    public void addClassReference( BoundType sourceType, BoundType targetType ) {
        metrics.addClassReference( sourceType, targetType );
    }

    public void setCCValue( IFile file, MethodDeclaration methodDecl, int value ) {
        metrics.setCCValue( file, methodDecl, value );
    }

    public void setCCValue( IFile file, Initializer initializer, int value ) {
        metrics.setCCValue( file, initializer, value );
    }

    public void addClass( IFile file, AbstractTypeDeclaration node ) {
        metrics.addClass( file, node );
    }

    public void setMLValue( IFile file, MethodDeclaration methodDecl, int value ) {
        metrics.setMLValue( file, methodDecl, value );
    }

    public void setMLValue( IFile file, Initializer initializer, int value ) {
        metrics.setMLValue( file, initializer, value );
    }

    public void collectCoverageInfo( IJavaModelCoverage javaModelCoverage ) {
        if( javaModelCoverage.getInstrumentedProjects().length == 0 ) {
            return;
        }
        metrics.updateCoverage( javaModelCoverage );
        CodeProportion codeProportion = getCodeProportion( CodeProportionKind.TA );
        history.addTestResult( codeProportion );
        cache.refresh( codeProportion );
        notifyListeners();
    }

    public void setWarningsCount( IFile file, int markerCount ) {
        metrics.setWarningsCount( file, markerCount );
    }

    public void setWarningsCount( IProject project, int markerCount ) {
        metrics.setWarningsCount( project, markerCount );
    }

    // interface of IUsusModel
    // ////////////////////////

    public CheckpointHistory getHistory() {
        return history;
    }

    public IUsusElement[] getElements() {
        return cache.getElements().clone();
    }

    // ///////////////////////////////////////////////////////////////////////

    // Methoden, die auf WorkspaceRawData zugreifen:
    public CodeProportion getCodeProportion( CodeProportionKind metric ) {
        return cache.getCodeProportion( metric );
    }

    public int getNumberOf( CodeProportionUnit unit ) {
        return metrics.getNumberOf( unit );
    }

    public int getNumberOf( IProject project, CodeProportionUnit unit ) {
        return metrics.getNumberOf( project, unit );
    }

    public int getNumberOfMethods( IType type ) {
        return metrics.getNumberOfMethods( type );
    }

    public int getCCD( IType type ) {
        return metrics.getCCD( type );
    }

    public int getOverallMetric( CodeProportionKind metric ) {
        return metrics.getOverallMetric( metric );
    }

    public int getOverallMetric( IProject project, CodeProportionKind metric ) {
        return metrics.getOverallMetric( project, metric );
    }

    public int getViolationCount( IProject project, CodeProportionKind metric ) {
        return metrics.getViolationCount( project, metric );
    }

    public Set<ClassRepresenter> getAllClassRepresenters() {
        return metrics.getAllClassRepresenters();
    }

    public Set<PackageRepresenter> getAllPackages() {
        return metrics.getAllPackages();
    }

    public int getCCValue( IMethod method ) throws JavaModelException {
        return metrics.getCCValue( method );
    }

    public int getMLValue( IMethod method ) throws JavaModelException {
        return metrics.getMLValue( method );
    }

    public double getRelativeACD() {
        return metrics.getRelativeACD();
    }

    public int getNumberOfWarnings( IFile file ) throws JavaModelException {
        return metrics.getNumberOfWarnings( file );
    }

    public YellowCountResult getWarnings() {
        return metrics.getWarnings();
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
        for( IUsusModelListener listener : listeners ) {
            listener.ususModelChanged();
        }
    }

    public int getNumberOfProjectsViolatingCW() {
        return metrics.getNumberOfProjectsViolatingCW();
    }

    public boolean needsFullRecompute() {
        return needsFullRecompute;
    }

    /**
     * @deprecated will soon be deleted; access to FileRelations via Descriptors then
     */
    public FileRelationMetrics getFileRelationMetrics() {
        return metrics.getFileRelationMetrics();
    }
}
