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

public class MethodResultsTest {

    private final String PACKAGENAME = "package.name";
    private final String CLASSNAME = "ClassName";
    private final String METHODNAME = "methodname";
    private MethodResults methodResults;
    private final int LINENO = 77;
    
    @Before
    public void setup(){
//       TODO methodResults = new MethodResults( CLASSNAME, METHODNAME, LINENO  );
    }
    
//    @Test
//    public void basisIs1(){
//        assertEquals( 1, methodResults.getViolationBasis( IsisMetrics.ACD ) );
//        assertEquals( 1, methodResults.getViolationBasis( IsisMetrics.CC ) );
//        assertEquals( 1, methodResults.getViolationBasis( IsisMetrics.CW ) );
//        assertEquals( 1, methodResults.getViolationBasis( IsisMetrics.KG ) );
//        assertEquals( 1, methodResults.getViolationBasis( IsisMetrics.ML ) );
//        assertEquals( 1, methodResults.getViolationBasis( IsisMetrics.PC ) );
//        assertEquals( 1, methodResults.getViolationBasis( IsisMetrics.TA ) );
//    }
//    
//    @Test
//    public void methodName(){
//        assertEquals( METHODNAME, methodResults.getMethodName() );
//    }
//    
//    @Test
//    public void lineNo(){
//        assertEquals( LINENO, methodResults.getLineNo() );
//    }
//    
//    @Test
//    public void ccResultNoViolation(){
//        assertEquals( 0, methodResults.getCCResult() );
//        assertEquals( 0, methodResults.getViolationCount( IsisMetrics.CC ) );
//        
//        List<String> methodNameList = new ArrayList<String>();
//        methodResults.getViolationNames( IsisMetrics.CC, methodNameList );
//        assertEquals( 0, methodNameList.size() );
//
//        List<Integer> methodLineNoList = new ArrayList<Integer>();
//        methodResults.getViolationLineNumbers( IsisMetrics.CC, methodLineNoList );
//        assertEquals( 0, methodLineNoList.size() );
//    }
//    
//    @Test
//    public void ccResultViolation(){
//        int value = 6;
//        methodResults.setCCResult( value );
//        
//        assertEquals( value, methodResults.getCCResult() );
//        assertEquals( 1, methodResults.getViolationCount( IsisMetrics.CC ) );
//        
//        List<String> methodNameList = new ArrayList<String>();
//        methodResults.getViolationNames( IsisMetrics.CC, methodNameList );
//        assertEquals( 1, methodNameList.size() );
//        assertEquals( METHODNAME, methodNameList.get( 0 ) );
//        
//        List<Integer> methodLineNoList = new ArrayList<Integer>();
//        methodResults.getViolationLineNumbers( IsisMetrics.CC, methodLineNoList );
//        assertEquals( 1, methodLineNoList.size() );
//        assertEquals( LINENO, methodLineNoList.get( 0 ).intValue() );
//    }
//    
//    @Test
//    public void mlResultNoViolation(){
//        assertEquals( 0, methodResults.getMLResult() );
//        assertEquals( 0, methodResults.getViolationCount( IsisMetrics.ML ) );
//        
//        List<String> methodNameList = new ArrayList<String>();
//        methodResults.getViolationNames( IsisMetrics.ML, methodNameList );
//        assertEquals( 0, methodNameList.size() );
//        
//        List<Integer> methodLineNoList = new ArrayList<Integer>();
//        methodResults.getViolationLineNumbers( IsisMetrics.ML, methodLineNoList );
//        assertEquals( 0, methodLineNoList.size() );
//    }
//    
//    @Test
//    public void mlResultViolation(){
//        int value = 16;
//        methodResults.setMLResult( value );
//        
//        assertEquals( value, methodResults.getMLResult() );
//        assertEquals( 1, methodResults.getViolationCount( IsisMetrics.ML ) );
//        
//        List<String> methodNameList = new ArrayList<String>();
//        methodResults.getViolationNames( IsisMetrics.ML, methodNameList );
//        assertEquals( 1, methodNameList.size() );
//        assertEquals( METHODNAME, methodNameList.get( 0 ) );
//
//        List<Integer> methodLineNoList = new ArrayList<Integer>();
//        methodResults.getViolationLineNumbers( IsisMetrics.ML, methodLineNoList );
//        assertEquals( 1, methodLineNoList.size() );
//        assertEquals( LINENO, methodLineNoList.get( 0 ).intValue() );
//    }
//    
}
