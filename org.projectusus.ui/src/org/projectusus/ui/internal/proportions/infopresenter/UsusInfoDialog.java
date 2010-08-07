package org.projectusus.ui.internal.proportions.infopresenter;

import static org.eclipse.swt.layout.GridData.FILL_BOTH;

import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.projectusus.ui.internal.proportions.infopresenter.infomodel.IUsusInfo;

public class UsusInfoDialog extends PopupDialog {

    private final IUsusInfo ususInfo;

    public UsusInfoDialog( Shell parent, IUsusInfo ususInfo ) {
        super( parent, PopupDialog.INFOPOPUPRESIZE_SHELLSTYLE, true, false, false, true, false, ususInfo.formatTitle(), "Project Usus Infos" ); //$NON-NLS-1$
        this.ususInfo = ususInfo;
    }

    @Override
    protected Control createDialogArea( Composite parent ) {
        Composite area = (Composite)super.createDialogArea( parent );
        UsusInfoViewer viewer = new UsusInfoViewer( area, ususInfo );
        viewer.getControl().setLayoutData( new GridData( FILL_BOTH ) );
        viewer.expandAll();
        return area;
    }
}
