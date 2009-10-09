// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.ViewPart;
import org.projectusus.core.internal.proportions.CodeProportions;
import org.projectusus.core.internal.proportions.ICodeProportionsListener;


public class CodeProportionsCockpit extends ViewPart {

    private TableViewer tableViewer;
    private ICodeProportionsListener listener;

    @Override
    public void createPartControl( Composite parent ) {
        createTableViewer( parent );
        initListener();
    }

    @Override
    public void setFocus() {
        if( tableViewer != null && !tableViewer.getControl().isDisposed() ) {
            tableViewer.getControl().setFocus();
        }
    }

    @Override
    public void dispose() {
        CodeProportions.getInstance().removeCodeProportionsListener( listener );
        super.dispose();
    }

    private void initListener() {
        listener = new ICodeProportionsListener() {
            public void codeProportionsChanged() {
                Display.getDefault().asyncExec( new Runnable() {
                    public void run() {
                        refresh();
                    }
                } );
            }
        };
        CodeProportions.getInstance().addCodeProportionsListener( listener );
    }

    private void refresh() {
        if( tableViewer != null && !tableViewer.getControl().isDisposed() ) {
            tableViewer.refresh();
        }
    }

    private void createTableViewer( Composite parent ) {
        tableViewer = new TableViewer( parent );
        tableViewer.setLabelProvider( new LabelProvider() );
        tableViewer.setContentProvider( new CodeProportionsCockpitCP() );
        tableViewer.setInput( CodeProportions.getInstance() );
    }
}
