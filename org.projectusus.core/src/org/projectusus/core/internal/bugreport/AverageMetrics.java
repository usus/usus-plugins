// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.bugreport;

import static org.projectusus.core.internal.util.CoreTexts.AverageMetrics_overall;

import org.eclipse.core.resources.IProject;
import org.projectusus.core.internal.UsusCorePlugin;
import org.projectusus.core.internal.proportions.IUsusModel;
import org.projectusus.core.internal.proportions.rawdata.CodeProportionKind;
import org.projectusus.core.internal.proportions.rawdata.CodeProportionUnit;

public class AverageMetrics implements IAverageMetrics {

    private int numberOfMethods;
    private int totalCC;
    private int totalML;
    private int numberOfClasses;

    public double getAverageCyclomaticComplexity() {
        return calculateAverage( totalCC, numberOfMethods );
    }

    private double calculateAverage( int metricValue, int metricValueBasis ) {
        if( metricValueBasis == 0 ) {
            return 0.0;
        }
        return metricValue / metricValueBasis;
    }

    public double getAverageMethodLength() {
        return calculateAverage( totalML, numberOfMethods );
    }

    public double getAverageNumberOfMethodsInClass() {
        return calculateAverage( numberOfMethods, numberOfClasses );
    }

    // TODO nr: direkt die Summen aus dem WorkspaceRD geben lassen?
    public void addProjectResults( IProject project ) {
        IUsusModel model = UsusCorePlugin.getUsusModel();
        numberOfMethods += model.getNumberOf( project, CodeProportionUnit.METHOD );
        numberOfClasses += model.getNumberOf( project, CodeProportionUnit.CLASS );

        totalCC += model.getOverallMetric( project, CodeProportionKind.CC );
        totalML += model.getOverallMetric( project, CodeProportionKind.ML );
    }

    public String getName() {
        return AverageMetrics_overall;
    }

}
