// Copyright (c) 2005-2006 by Leif Frenzel.
// All rights reserved.
package org.projectusus.core.internal.yellowcount;

/**
 * <p>
 * implementors can register with the YellowCount singleton in order to get notified about changes in the yellow count.
 * </p>
 * 
 * @author Leif Frenzel
 */
public interface IYellowCountListener {

    void yellowCountChanged();
}
