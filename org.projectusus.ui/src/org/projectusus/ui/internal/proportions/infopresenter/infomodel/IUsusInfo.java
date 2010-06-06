// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.infopresenter.infomodel;

/**
 * information that is displayed to the User in the UI, such as metrics values for a given Java element (e.g. method).
 */
public interface IUsusInfo {

    String[] getCodeProportionInfos();

    String formatTitle();

    String[] getWarningInfos();

}
