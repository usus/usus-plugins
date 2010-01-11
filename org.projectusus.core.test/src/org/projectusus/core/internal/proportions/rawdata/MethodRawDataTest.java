// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.internal.proportions.model.IHotspot;
import org.projectusus.core.internal.proportions.rawdata.CodeProportionKind;
import org.projectusus.core.internal.proportions.rawdata.CodeProportionUnit;
import org.projectusus.core.internal.proportions.rawdata.MethodRawData;

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
        assertEquals( 1, methodResults.getNumberOf( CodeProportionUnit.CLASS ) );
        assertEquals( 1, methodResults.getNumberOf( CodeProportionUnit.METHOD ) );
        assertEquals( 1, methodResults.getNumberOf( CodeProportionUnit.FILE ) );
        assertEquals( 1, methodResults.getNumberOf( CodeProportionUnit.PACKAGE ) );
        assertEquals( 1, methodResults.getNumberOf( CodeProportionUnit.LINE ) );
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
        assertEquals( 0, methodResults.getViolationCount( CodeProportionKind.CC ) );
        
        List<IHotspot> hotspots = new ArrayList<IHotspot>();
        methodResults.addToHotspots( CodeProportionKind.CC, hotspots );
        assertEquals(0, hotspots.size());
    }

    
    @Test
    public void ccResultViolation(){
        int value = 6;
        methodResults.setCCValue( value );
        
        assertEquals( value, methodResults.getCCValue() );
        assertEquals( 1, methodResults.getViolationCount( CodeProportionKind.CC ) );
        
        List<IHotspot> hotspots = new ArrayList<IHotspot>();
        methodResults.addToHotspots( CodeProportionKind.CC, hotspots );
        assertEquals(1, hotspots.size());
        assertEquals(SOURCEPOSITION, hotspots.get( 0 ).getSourcePosition());
    }
    
    @Test
    public void mlResultNoViolation(){
        assertEquals( 0, methodResults.getMLValue() );
        assertEquals( 0, methodResults.getViolationCount( CodeProportionKind.ML ) );
        
        List<IHotspot> hotspots = new ArrayList<IHotspot>();
        methodResults.addToHotspots( CodeProportionKind.ML, hotspots );
        assertEquals(0, hotspots.size());
    }
    
    @Test
    public void mlResultViolation(){
        int value = 16;
        methodResults.setMLValue( value );
        
        assertEquals( value, methodResults.getMLValue() );
        assertEquals( 1, methodResults.getViolationCount( CodeProportionKind.ML ) );
        
        List<IHotspot> hotspots = new ArrayList<IHotspot>();
        methodResults.addToHotspots( CodeProportionKind.ML, hotspots );
        assertEquals(1, hotspots.size());
        assertEquals(SOURCEPOSITION, hotspots.get( 0 ).getSourcePosition());
    }
    
}
