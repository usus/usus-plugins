// Copyright (c) 2005-2006 by Leif Frenzel.
// All rights reserved.
package org.projectusus.core.internal.yellowcount;

/**
 * <p>
 * encapsultes the results of counting yellow things in the workspace.
 * </p>
 * 
 * @author Leif Frenzel
 */
public interface IYellowCountResult {

    int getYellowCount();

    int getYellowProjectCount();

    int getProjectCount();

    int getFormattedCountPosition();

    int getFormattedCountLength();
}
