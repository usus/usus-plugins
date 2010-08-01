package org.projectusus.ui.dependencygraph.common;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;

public class WorkbenchContext {

    private final IContextService service;
    private final String contextId;
    private IContextActivation activation;

    public WorkbenchContext( String contextId ) {
        this( (IContextService)PlatformUI.getWorkbench().getService( IContextService.class ), contextId );
    }

    public WorkbenchContext( IContextService service, String contextId ) {
        this.service = service;
        this.contextId = contextId;
    }

    public void activate() {
        if( isDeactivated() ) {
            activation = service.activateContext( contextId );
        }
    }

    public void deactivate() {
        if( isActivated() ) {
            service.deactivateContext( activation );
            activation = null;
        }
    }

    public boolean isActivated() {
        return !isDeactivated();
    }

    public boolean isDeactivated() {
        return activation == null;
    }

}
