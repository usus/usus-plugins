// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.sqi;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.internal.proportions.model.IHotspot;
import org.projectusus.core.internal.proportions.rawdata.ClassRawData;
import org.projectusus.core.internal.proportions.rawdata.CodeProportionKind;

public class ClassRawDataTest {

    private final String CLASSNAME = "ClassName";
    private final String FULLNAME = "my.package.ClassName";
    private final String METHODNAME1 = "methodname1";
    private final int SOURCEPOSITION = 77;
    private final int LINENUMBER = 12;

    private ClassRawData classRawData;
    private MethodDeclaration method1;
    
    
    @Before
    public void setup(){
        classRawData = new ClassRawData(  CLASSNAME, FULLNAME, SOURCEPOSITION, LINENUMBER );
        method1 = mock( MethodDeclaration.class );
        initMethod( method1, METHODNAME1 );
    }

    @Test
    public void className(){
        assertEquals( CLASSNAME, classRawData.getClassName() );
    }
    
    @Test
    public void violationBasisNoMethods(){
        assertEquals( 1, classRawData.getViolationBasis( CodeProportionKind.KG ) );
        assertEquals( 0, classRawData.getViolationBasis( CodeProportionKind.CC ) );
        assertEquals( 0, classRawData.getViolationBasis( CodeProportionKind.ML ) );
    }

        @Test
    public void violationBasis1Method(){
        classRawData.setCCValue( method1, 0 );
        assertEquals( 1, classRawData.getViolationBasis( CodeProportionKind.KG ) );
        assertEquals( 1, classRawData.getViolationBasis( CodeProportionKind.CC ) );
        assertEquals( 1, classRawData.getViolationBasis( CodeProportionKind.ML ) );
    }
        
    @Test
    public void violationCountNoMethods(){
        assertEquals(0, classRawData.getViolationCount( CodeProportionKind.KG ));
        assertEquals(0, classRawData.getViolationCount( CodeProportionKind.CC ));
        assertEquals(0, classRawData.getViolationCount( CodeProportionKind.ML ));
    }
    
    @Test
    public void violationCountCC1Method(){
        int value = 6;
        classRawData.setCCValue( method1, value );
        assertEquals( 0, classRawData.getViolationCount( CodeProportionKind.KG ) );
        assertEquals( 1, classRawData.getViolationCount( CodeProportionKind.CC ) );
        assertEquals( 0, classRawData.getViolationCount( CodeProportionKind.ML ) );
    }
    @Test
    public void violationCountML1Method(){
        int value = 16;
        classRawData.setMLValue( method1, value );
        assertEquals( 0, classRawData.getViolationCount( CodeProportionKind.KG ) );
        assertEquals( 0, classRawData.getViolationCount( CodeProportionKind.CC ) );
        assertEquals( 1, classRawData.getViolationCount( CodeProportionKind.ML ) );
    }
    
    @Test
    public void addHotspotsNoMethods(){
        List<IHotspot> nameList = new ArrayList<IHotspot>();
        classRawData.addToHotspots( CodeProportionKind.KG, nameList );
        assertEquals(0, nameList.size());
        classRawData.addToHotspots( CodeProportionKind.CC, nameList );
        assertEquals(0, nameList.size());
        classRawData.addToHotspots( CodeProportionKind.ML, nameList );
        assertEquals(0, nameList.size());
    }
//    // TODO missing: KG tests
        
        private void initMethod( MethodDeclaration method, String methodname ) {
            SimpleName methodName = mock( SimpleName.class );
            when(method.getName()).thenReturn( methodName );
            when(methodName.toString()).thenReturn( methodname );
        }
}
