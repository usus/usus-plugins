// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.bugprison.core;

import static org.projectusus.bugprison.core.texts.BugPrisonTexts.AverageBugMetrics_allBugs;

public class AverageBugMetrics implements IAverageMetrics {

    private int numberOfBugs;
    final BugMetrics bugMetrics = new BugMetrics();

    public void addBugMetrics( BugMetrics bugMetrics ) {
        numberOfBugs++;
        this.bugMetrics.add( bugMetrics );
    }

    public double getAverageCyclomaticComplexity() {
        return computAverage( bugMetrics.getCyclomaticComplexity() );
    }

    public double getAverageMethodLength() {
        return computAverage( bugMetrics.getMethodLength() );
    }

    public double getAverageNumberOfMethodsInClass() {
        return computAverage( bugMetrics.getNumberOfMethods() );
    }

    private double computAverage( int value ) {
        if( numberOfBugs == 0 ) {
            return Double.NaN;
        }
        return value / numberOfBugs;
    }

    public void addAverageBugMetrics( AverageBugMetrics averageMetrics ) {
        numberOfBugs += averageMetrics.getNumberOfBugs();
        bugMetrics.add( averageMetrics.getBugMetrics() );
    }

    public BugMetrics getBugMetrics() {
        return bugMetrics;
    }

    public int getNumberOfBugs() {
        return numberOfBugs;
    }

    public String getName() {
        return AverageBugMetrics_allBugs;
    }

}
