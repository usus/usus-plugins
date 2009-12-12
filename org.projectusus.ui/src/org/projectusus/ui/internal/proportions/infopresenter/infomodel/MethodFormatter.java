// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.infopresenter.infomodel;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;

public class MethodFormatter {

    private final IMethod method;

    public MethodFormatter( IMethod method ) {
        this.method = method;
    }

    public String formatHeadInfo() {
        String result = ""; //$NON-NLS-1$
        try {
            result = formatSignature();
        } catch( JavaModelException jamox ) {
            // must live with blank signature
        }
        return result;
    }

    private String formatSignature() throws JavaModelException {
        String returnType = Signature.toString( method.getReturnType() );
        String name = method.getElementName();
        String params = Signature.toString( method.getSignature() );
        params = params.substring( returnType.length() ).trim();
        return returnType + " " + name + params; //$NON-NLS-1$
    }
}
