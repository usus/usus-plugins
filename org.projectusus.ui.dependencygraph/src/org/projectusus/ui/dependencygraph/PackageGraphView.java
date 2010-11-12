package org.projectusus.ui.dependencygraph;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.projectusus.ui.dependencygraph.common.DependencyGraphView;

public class PackageGraphView extends DependencyGraphView {

    public static final String VIEW_ID = PackageGraphView.class.getName();

    private static final String ONLY_IN_CYCLES = "only with cyclic dependencies";

    private Button checkbox;

    public PackageGraphView() {
        super( new PackageGraphModel() );
    }

    @Override
    public String getFilenameForScreenshot() {
        return "usus-package-graph";
    }

    @Override
    protected void createAdditionalWidget( Composite filterArea ) {
        checkbox = new Button( filterArea, SWT.CHECK );
        checkbox.setText( ONLY_IN_CYCLES );
        checkbox.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent e ) {
                Display.getDefault().asyncExec( new Runnable() {
                    public void run() {
                        setFilterLimit( checkbox.getSelection() ? 1 : 0 );
                        drawGraphConditionally();
                        refresh();
                    }
                } );
            }
        } );
        setFilterLimit( 0 );
    }

    @Override
    protected void updateAdditionalWidget() {
        // we do not need hook that here
    }

}
