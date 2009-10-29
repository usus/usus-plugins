// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.hotspots;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.texteditor.ITextEditor;
import org.projectusus.core.internal.proportions.model.IHotspot;
import org.projectusus.ui.internal.UsusUIPlugin;
import org.projectusus.ui.internal.proportions.CockpitView;

public class HotSpotsView extends ViewPart {

    private TableViewer tableViewer;

    @Override
    public void createPartControl( Composite parent ) {
        createTableViewer( parent );
        initSelectionListener();
        initOpenListener();
    }

    @Override
    public void setFocus() {
        if( tableViewer != null && !tableViewer.getControl().isDisposed() ) {
            tableViewer.getControl().setFocus();
        }
    }

    private void createTableViewer( Composite parent ) {
        tableViewer = new TableViewer( parent );
        tableViewer.setLabelProvider( new LabelProvider() );
        tableViewer.setContentProvider( new HotspotsCP() );
    }

    private void initSelectionListener() {
        ISelectionService service = getViewSite().getWorkbenchWindow().getSelectionService();
        service.addSelectionListener( new ISelectionListener() {
            public void selectionChanged( IWorkbenchPart part, ISelection selection ) {
                if( part instanceof CockpitView && selection instanceof IStructuredSelection ) {
                    IStructuredSelection ssel = (IStructuredSelection)selection;
                    tableViewer.setInput( ssel.getFirstElement() );
                }
            }
        } );
    }

    private void initOpenListener() {
        tableViewer.addOpenListener( new IOpenListener() {
            public void open( OpenEvent event ) {
                ISelection selection = event.getSelection();
                if( !selection.isEmpty() && selection instanceof IStructuredSelection ) {
                    IStructuredSelection ssel = (IStructuredSelection)selection;
                    Object element = ssel.getFirstElement();
                    if( element instanceof IHotspot ) {
                        openEditorAt( (IHotspot)element );
                    }
                }
            }
        } );
    }

    private void openEditorAt( IHotspot hotspot ) {
        ICompilationUnit compilationUnit = JavaCore.createCompilationUnitFrom( hotspot.getFile() );
        if( compilationUnit != null ) {
            try {
                IEditorPart editor = JavaUI.openInEditor( compilationUnit );
                if( editor instanceof ITextEditor ) {
                    openAtElement( hotspot, compilationUnit, editor );
                }
            } catch( CoreException cex ) {
                UsusUIPlugin.getDefault().getLog().log( cex.getStatus() );
            } catch( BadLocationException badlox ) {
                // get stuffed
            }
        }
    }

    private void openAtElement( IHotspot hotspot, ICompilationUnit compilationUnit, IEditorPart editor ) throws BadLocationException, JavaModelException, PartInitException {
        ITextEditor textEditor = (ITextEditor)editor;
        IDocument document = textEditor.getDocumentProvider().getDocument( textEditor.getEditorInput() );
        if( document != null ) {
            int lineOffset = document.getLineOffset( hotspot.getLine() );
            IJavaElement element = compilationUnit.getElementAt( lineOffset );
            JavaUI.openInEditor( element, true, true );
        }
    }
}
