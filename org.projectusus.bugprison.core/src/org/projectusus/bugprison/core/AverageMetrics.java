// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.bugprison.core;

import static org.projectusus.bugprison.core.texts.BugPrisonTexts.AverageMetrics_overall;

import org.eclipse.core.resources.IProject;
import org.projectusus.core.basis.JavaModelPath;
import org.projectusus.core.statistics.visitors.ClassCountVisitor;
import org.projectusus.core.statistics.visitors.CyclomaticComplexityCountVisitor;
import org.projectusus.core.statistics.visitors.MethodCountVisitor;
import org.projectusus.core.statistics.visitors.MethodLengthCountVisitor;

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

    public void addProjectResults( IProject project ) {
        JavaModelPath path = new JavaModelPath( project );
        numberOfMethods += new MethodCountVisitor( path ).visitAndReturn().getMethodCount();
        numberOfClasses += new ClassCountVisitor( path ).visitAndReturn().getClassCount();

        totalCC += new CyclomaticComplexityCountVisitor( path ).visitAndReturn().getMetricsSum();
        totalML += new MethodLengthCountVisitor( path ).visitAndReturn().getMetricsSum();
    }

    public String getName() {
        return AverageMetrics_overall;
    }

}
