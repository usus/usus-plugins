// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

import org.projectusus.core.internal.proportions.modelupdate.IUsusModelUpdate;

/**
 * provides update facilities to the Usus model.
 * 
 * @author leif
 */
public interface IUsusModelWriteAccess {

    void update( IUsusModelUpdate updateCommand );
}
