// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.sqi;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.internal.proportions.model.IHotspot;

public class MethodRawDataTest {

    private final String CLASSNAME = "ClassName";
    private final String METHODNAME = "methodname";
    private MethodRawData methodResults;
    private final int SOURCEPOSITION = 77;
    private final int LINENUMBER = 12;
    
    @Before
    public void setup(){
        methodResults = new MethodRawData( SOURCEPOSITION, LINENUMBER, CLASSNAME, METHODNAME );
    }
    
    @Test
    public void basisIs1(){
        assertEquals( 1, methodResults.getViolationBasis( IsisMetrics.ACD ) );
        assertEquals( 1, methodResults.getViolationBasis( IsisMetrics.CC ) );
        assertEquals( 1, methodResults.getViolationBasis( IsisMetrics.CW ) );
        assertEquals( 1, methodResults.getViolationBasis( IsisMetrics.KG ) );
        assertEquals( 1, methodResults.getViolationBasis( IsisMetrics.ML ) );
        assertEquals( 1, methodResults.getViolationBasis( IsisMetrics.PC ) );
        assertEquals( 1, methodResults.getViolationBasis( IsisMetrics.TA ) );
    }
    
    @Test
    public void methodName(){
        assertEquals( METHODNAME, methodResults.getMethodName() );
    }
    
    @Test
    public void sourceposition(){
        assertEquals( SOURCEPOSITION, methodResults.getSourcePosition() );
    }
    
    @Test
    public void ccResultNoViolation(){
        assertEquals( 0, methodResults.getCCValue() );
        assertEquals( 0, methodResults.getViolationCount( IsisMetrics.CC ) );
        
        List<IHotspot> hotspots = new ArrayList<IHotspot>();
        methodResults.addHotspots( IsisMetrics.CC, hotspots );
        assertEquals(0, hotspots.size());
    }

    
    @Test
    public void ccResultViolation(){
        int value = 6;
        methodResults.setCCValue( value );
        
        assertEquals( value, methodResults.getCCValue() );
        assertEquals( 1, methodResults.getViolationCount( IsisMetrics.CC ) );
        
        List<IHotspot> hotspots = new ArrayList<IHotspot>();
        methodResults.addHotspots( IsisMetrics.CC, hotspots );
        assertEquals(1, hotspots.size());
        assertEquals(SOURCEPOSITION, hotspots.get( 0 ).getSourcePosition());
    }
    
    @Test
    public void mlResultNoViolation(){
        assertEquals( 0, methodResults.getMLValue() );
        assertEquals( 0, methodResults.getViolationCount( IsisMetrics.ML ) );
        
        List<IHotspot> hotspots = new ArrayList<IHotspot>();
        methodResults.addHotspots( IsisMetrics.ML, hotspots );
        assertEquals(0, hotspots.size());
    }
    
    @Test
    public void mlResultViolation(){
        int value = 16;
        methodResults.setMLValue( value );
        
        assertEquals( value, methodResults.getMLValue() );
        assertEquals( 1, methodResults.getViolationCount( IsisMetrics.ML ) );
        
        List<IHotspot> hotspots = new ArrayList<IHotspot>();
        methodResults.addHotspots( IsisMetrics.ML, hotspots );
        assertEquals(1, hotspots.size());
        assertEquals(SOURCEPOSITION, hotspots.get( 0 ).getSourcePosition());
    }
    
}
