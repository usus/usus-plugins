// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.bugreport;

import static java.lang.String.valueOf;
import static org.projectusus.ui.internal.util.UITexts.BugPage_classname;
import static org.projectusus.ui.internal.util.UITexts.BugPage_cyclomatic_complexity;
import static org.projectusus.ui.internal.util.UITexts.BugPage_method_length;
import static org.projectusus.ui.internal.util.UITexts.BugPage_methodname;
import static org.projectusus.ui.internal.util.UITexts.BugPage_number_of_methods_in_class;
import static org.projectusus.ui.internal.util.UITexts.BugPage_packagename;
import static org.projectusus.ui.internal.util.UITexts.BugPage_report_bug;
import static org.projectusus.ui.internal.util.UITexts.BugPage_title;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.projectusus.core.internal.bugreport.Bug;
import org.projectusus.ui.internal.util.UITexts;

public class BugPage extends WizardPage {

    private Text packageName;
    private Text className;
    private Text methodName;
    private final Bug bug;
    private Text title;
    private Text cyclomaticComplexity;
    private Text methodLength;
    private Text numberOfMethodsInClass;

    public BugPage( Bug bug ) {
        super( "" ); //$NON-NLS-1$
        setTitle( BugPage_report_bug );
        setDescription( BugPage_report_bug );
        // setImageDescriptor( getSharedImages().getDescriptor( WIZARD_REPORT_BUG ) );
        this.bug = bug;
    }

    public void createControl( Composite parent ) {
        Composite composite = new Composite( parent, SWT.NULL );
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        composite.setLayout( gridLayout );
        title = createEditableText( composite, BugPage_title );
        packageName = createNotEditableText( composite, BugPage_packagename );
        className = createNotEditableText( composite, BugPage_classname );
        methodName = createNotEditableText( composite, BugPage_methodname );
        cyclomaticComplexity = createNotEditableText( composite, BugPage_cyclomatic_complexity );
        methodLength = createNotEditableText( composite, BugPage_method_length );
        numberOfMethodsInClass = createNotEditableText( composite, BugPage_number_of_methods_in_class );

        initView();
        setControl( composite );
    }

    private void initView() {
        title.setText( bug.getTitle() );
        packageName.setText( bug.getLocation().getPackageName() );
        className.setText( bug.getLocation().getClassName() );
        methodName.setText( bug.getLocation().getMethodName() );
        cyclomaticComplexity.setText( valueOf( bug.getBugMetrics().getCyclomaticComplexity() ) );
        methodLength.setText( valueOf( bug.getBugMetrics().getMethodLength() ) );
        numberOfMethodsInClass.setText( valueOf( bug.getBugMetrics().getNumberOfMethods() ) );
    }

    private Text createNotEditableText( Composite composite, String labelText ) {
        return createText( composite, labelText, false );
    }

    private Text createEditableText( Composite composite, String labelText ) {
        Text text = createText( composite, labelText, true );
        text.addModifyListener( new ModifyListener() {

            public void modifyText( ModifyEvent e ) {
                updateModel();
            }
        } );
        return text;
    }

    private void updateModel() {
        bug.setTitle( title.getText() );
        if( bug.getTitle().length() == 0 ) {
            setMessage( UITexts.BugPage_insert_title, IMessageProvider.WARNING );
        } else {
            setMessage( null );
        }
    }

    private Text createText( Composite composite, String labelText, boolean editable ) {
        Label label = new Label( composite, SWT.NULL );
        label.setText( labelText );
        int style = SWT.NONE;
        if( editable ) {
            style = style | SWT.BORDER;
        }
        Text text = new Text( composite, style );
        GridData gridData = new GridData();
        gridData.horizontalAlignment = SWT.FILL;
        gridData.grabExcessHorizontalSpace = true;
        text.setLayoutData( gridData );
        text.setEditable( editable );
        return text;
    }

    public Bug getBug() {
        return bug;
    }

}
