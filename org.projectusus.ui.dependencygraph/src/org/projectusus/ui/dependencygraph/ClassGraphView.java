package org.projectusus.ui.dependencygraph;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Scale;
import org.projectusus.ui.dependencygraph.common.DependencyGraphView;

public class ClassGraphView extends DependencyGraphView {

    public static final String VIEW_ID = ClassGraphView.class.getName();

    private static final String SCALE_LEFT_TEXT = "All";
    private static final String SCALE_RIGHT_TEXT = "None";

    private Scale scale;

    public ClassGraphView() {
        super( new ClassGraphModel() );
    }

    @Override
    protected int calcMaxFilterValue( int maxFilterValue ) {
        return maxFilterValue + 1;
    }

    @Override
    public String getFilenameForScreenshot() {
        return "usus-class-graph";
    }

    @Override
    protected void updateAdditionalWidget() {
        int maxFilterValue = getMaxFilterValue();
        initFilterLimit( maxFilterValue );
        scale.setMaximum( calcMaxFilterValue( maxFilterValue ) );
    }

    private void initFilterLimit( int maxFilterValue ) {
        if( getFilterLimit() == -1 ) {
            setFilterLimit( maxFilterValue );
            scale.setSelection( getFilterLimit() );
        }
    }

    @Override
    protected void createAdditionalWidget( Composite filterArea ) {
        createLabel( filterArea, SCALE_LEFT_TEXT );

        scale = new Scale( filterArea, SWT.HORIZONTAL );
        scale.setMinimum( 0 );
        scale.setMaximum( 9999999 );
        scale.setSelection( getFilterLimit() );
        scale.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent e ) {
                Display.getDefault().asyncExec( new Runnable() {
                    public void run() {
                        int spinnerValue = scale.getSelection();
                        setFilterLimit( spinnerValue );
                        drawGraphConditionally();
                    }
                } );
            }
        } );

        createLabel( filterArea, SCALE_RIGHT_TEXT );
    }
}
