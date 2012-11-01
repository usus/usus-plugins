package org.projectusus.core.project2;

public interface IUSUSProject {

    String ATT_USUS_PROJECT = "ususProject"; //$NON-NLS-1$
    String ENABLED = "enabled"; //$NON-NLS-1$

    boolean isUsusProject();

    void setUsusProject( boolean ususProject );

    String getProjectName();

}
