package org.projectusus.core.internal.proportions.model;

import org.eclipse.core.resources.IFile;

public interface IMetricMLHotspot extends IHotspot {

    IFile getFile();

    String getClassName();

    String getMethodName();

    int getMethodLength();
}
