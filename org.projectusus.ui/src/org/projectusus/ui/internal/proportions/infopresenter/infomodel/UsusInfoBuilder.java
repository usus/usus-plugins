// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.infopresenter.infomodel;

import org.eclipse.jdt.core.IMethod;

public class UsusInfoBuilder {

    private final IMethod method;

    public UsusInfoBuilder( IMethod method ) {
        this.method = method;
    }

    public IUsusInfo create() {
        return method == null ? null : buildUsusInfo();
    }

    private IUsusInfo buildUsusInfo() {
        IUsusInfo result = new UnavailableUsusInfo( method );
        try {
            result = new UsusInfo( method );
        } catch( IllegalStateException isex ) {
            // something went wrong in calculation
        }
        return result;
    }

}
