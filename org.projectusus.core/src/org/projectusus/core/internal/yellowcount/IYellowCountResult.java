// Copyright (c) 2005-2006, 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
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

    int getFileCount();

    int getYellowProjectCount();

    int getProjectCount();

    int getFormattedCountPosition();

    int getFormattedCountLength();

}
