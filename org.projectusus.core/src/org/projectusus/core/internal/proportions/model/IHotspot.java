// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.model;

import org.eclipse.core.resources.IFile;

public interface IHotspot {

    IFile getFile();

    int getSourcePosition();

    int getHotness();

}
