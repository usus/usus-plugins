// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.sqi;

import java.util.List;

import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.core.internal.proportions.model.IHotspot;
import org.projectusus.core.internal.proportions.model.MetricKGHotspot;

public class ClassResults extends Results<MethodDeclaration, MethodResults> {

    private final AbstractTypeDeclaration typeDecl;

    public ClassResults( AbstractTypeDeclaration typeDecl ) {
        this.typeDecl = typeDecl;
    }

    public void setCCResult( MethodDeclaration node, int value ) {
        getResults( node ).setCCResult( value );
    }

    public void setMLResult( MethodDeclaration node, int value ) {
        getResults( node ).setMLResult( value );
    }

    private MethodResults getResults( MethodDeclaration node ) {
        MethodResults methodResults = getResults( node, new MethodResults( node ) );
        return methodResults;
    }

    public String getClassName() {
        return typeDecl.getName().toString();
    }

    public int getSourcePosition() {
        return typeDecl.getStartPosition();
    }

    @Override
    public int getViolationBasis( IsisMetrics metric ) {
        if( metric.isMethodTest() ) {
            return super.getViolationBasis( metric );
        }
        return 1;
    }

    @Override
    public int getViolationCount( IsisMetrics metric ) {
        if( metric.isMethodTest() ) {
            return super.getViolationCount( metric );
        }
        return metric.isViolatedBy( this ) ? 1 : 0;
    }

    @Override
    public void addHotspots( IsisMetrics metric, List<IHotspot> hotspots ) {
        if( metric.isMethodTest() ) {
            super.addHotspots( metric, hotspots );
        } else if( metric.isViolatedBy( this ) ) {
            hotspots.add( new MetricKGHotspot( getClassName(), this.getResultCount(), getSourcePosition() ) );
        }
    }
}
