// Copyright (c) 2005-2006, 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
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
