package org.projectusus.core.internal.proportions.sqi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.ACD;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.CC;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.CW;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.KG;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.ML;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.PC;
import static org.projectusus.core.internal.proportions.rawdata.CodeProportionKind.TA;

import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.internal.ReflectionUtil;
import org.projectusus.core.internal.proportions.rawdata.AcdModel;
import org.projectusus.core.internal.proportions.rawdata.ClassRawData;
import org.projectusus.core.internal.proportions.rawdata.CodeProportionUnit;
import org.projectusus.core.internal.proportions.rawdata.MethodRawData;
import org.projectusus.core.internal.proportions.rawdata.WorkspaceRawData;

public class CodeProportionsKindTest {

    private MethodRawData methodRawDataOK;
    private MethodRawData methodRawDataFailing;
    private ClassRawData classRawDataOK;
    private ClassRawData classRawDataFailing;
    
    @Before
    public void setup() throws Throwable {
        methodRawDataFailing = new MethodRawData( 1, 1, "className", "methodName" );
        methodRawDataFailing.setMLValue( 16 );
        methodRawDataFailing.setCCValue( 22 );
        methodRawDataOK = new MethodRawData( 1, 1, "className", "methodName" );
        methodRawDataOK.setMLValue( 1 );
        methodRawDataOK.setCCValue( 2 );

        ReflectionUtil.setValue( WorkspaceRawData.class, WorkspaceRawData.class, mock( WorkspaceRawData.class ), "instance" );
        when( WorkspaceRawData.getInstance().getViolationBasis( KG ) ).thenReturn( 100 );
        when( ClassRawData.getAcdModel() ).thenReturn( new AcdModel() );
        
        classRawDataOK = mock( ClassRawData.class );
        when( classRawDataOK.getNumberOfMethods() ).thenReturn( 1 );
        when( classRawDataOK.getCCDResult() ).thenReturn( 0 );

        classRawDataFailing = mock( ClassRawData.class );
        when( classRawDataFailing.getNumberOfMethods() ).thenReturn( 128 );
        when( classRawDataFailing.getCCDResult() ).thenReturn( 1000 );
        
    }

    @Test
    public void testGetLabel() {
        assertEquals( "Average component dependency", ACD.getLabel() );
        assertEquals( "Cyclomatic complexity", CC.getLabel() );
        assertEquals( "Compiler warnings", CW.getLabel() );
        assertEquals( "Class size", KG.getLabel() );
        assertEquals( "Method length", ML.getLabel() );
        assertEquals( "Package cycles", PC.getLabel() );
        assertEquals( "Test coverage", TA.getLabel() );
    }

    @Test
    public void testGetUnit() {
        assertEquals( CodeProportionUnit.CLASS, ACD.getUnit() );
        assertEquals( CodeProportionUnit.METHOD, CC.getUnit() );
        assertEquals( CodeProportionUnit.FILE, CW.getUnit() );
        assertEquals( CodeProportionUnit.CLASS, KG.getUnit() );
        assertEquals( CodeProportionUnit.METHOD, ML.getUnit() );
        assertEquals( CodeProportionUnit.PACKAGE, PC.getUnit() );
        assertEquals( CodeProportionUnit.METHOD, TA.getUnit() );
    }

    @Test
    public void testIsViolatedByMethodRawDataOK() {
      assertFalse( ACD.isViolatedBy( methodRawDataOK ) );
      assertFalse( CC.isViolatedBy( methodRawDataOK ) );
      assertFalse( CW.isViolatedBy( methodRawDataOK ) );
      assertFalse( KG.isViolatedBy( methodRawDataOK ) );
      assertFalse( ML.isViolatedBy( methodRawDataOK ) );
      assertFalse( PC.isViolatedBy( methodRawDataOK ) );
      assertFalse( TA.isViolatedBy( methodRawDataOK ) );
    }

    @Test
    public void testIsViolatedByMethodRawDataFailing() {
        assertFalse( ACD.isViolatedBy( methodRawDataFailing ) );
        assertTrue( CC.isViolatedBy( methodRawDataFailing ) );
        assertFalse( CW.isViolatedBy( methodRawDataFailing ) );
        assertFalse( KG.isViolatedBy( methodRawDataFailing ) );
        assertTrue( ML.isViolatedBy( methodRawDataFailing ) );
        assertFalse( PC.isViolatedBy( methodRawDataFailing ) );
        assertFalse( TA.isViolatedBy( methodRawDataFailing ) );
    }
    
    @Test
    public void testIsViolatedByClassRawDataOK() {
        assertFalse( ACD.isViolatedBy( classRawDataOK ) );
        assertFalse( CC.isViolatedBy( classRawDataOK ) );
        assertFalse( CW.isViolatedBy( classRawDataOK ) );
        assertFalse( KG.isViolatedBy( classRawDataOK ) );
        assertFalse( ML.isViolatedBy( classRawDataOK ) );
        assertFalse( PC.isViolatedBy( classRawDataOK ) );
        assertFalse( TA.isViolatedBy( classRawDataOK ) );
    }
    
    @Test
    public void testIsViolatedByClassRawDataFailing() {
        assertTrue( ACD.isViolatedBy( classRawDataFailing ) );
        assertFalse( CC.isViolatedBy( classRawDataFailing ) );
        assertFalse( CW.isViolatedBy( classRawDataFailing ) );
        assertTrue( KG.isViolatedBy( classRawDataFailing ) );
        assertFalse( ML.isViolatedBy( classRawDataFailing ) );
        assertFalse( PC.isViolatedBy( classRawDataFailing ) );
        assertFalse( TA.isViolatedBy( classRawDataFailing ) );
    }
    
    @Test
    public void testIsMethodTest() {
        assertFalse( ACD.isMethodKind() );
        assertTrue( CC.isMethodKind() );
        assertFalse( CW.isMethodKind() );
        assertFalse( KG.isMethodKind() );
        assertTrue( ML.isMethodKind() );
        assertFalse( PC.isMethodKind() );
        assertTrue( TA.isMethodKind() );
    }

    @Test
    public void testGetCalibration() {
      assertEquals( 1.0, ACD.getCalibration(), 0.0 );
      assertEquals( 100.0, CC.getCalibration(), 0.0 );
      assertEquals( 1.0, CW.getCalibration(), 0.0 );
      assertEquals( 25.0, KG.getCalibration(), 0.0 );
      assertEquals( 25.0, ML.getCalibration(), 0.0 );
      assertEquals( 1.0, PC.getCalibration(), 0.0 );
      assertEquals( 1.0, TA.getCalibration(), 0.0 );
    }

    @Test
    public void testGetValueFor() {
      assertEquals( 0, ACD.getValueFor( methodRawDataFailing ) );
      assertEquals( 22, CC.getValueFor( methodRawDataFailing ) );
      assertEquals( 0, CW.getValueFor( methodRawDataFailing ) );
      assertEquals( 0, KG.getValueFor( methodRawDataFailing ) );
      assertEquals( 16, ML.getValueFor( methodRawDataFailing ) );
      assertEquals( 0, PC.getValueFor( methodRawDataFailing ) );
      assertEquals( 0, TA.getValueFor( methodRawDataFailing ) );
    }
}
