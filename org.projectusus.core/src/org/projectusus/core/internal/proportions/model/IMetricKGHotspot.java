package org.projectusus.core.internal.proportions.model;

import org.eclipse.core.resources.IFile;

public interface IMetricKGHotspot {

    IFile getFile();

    String getClassName();

    int getClassSize();
}
