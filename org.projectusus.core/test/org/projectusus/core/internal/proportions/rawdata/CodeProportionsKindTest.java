package org.projectusus.core.internal.proportions.rawdata;

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

public class CodeProportionsKindTest {

    private UsusModel ususModel;
    private MethodRawData methodRawDataOK;
    private MethodRawData methodRawDataFailing;
    private ClassRawData classRawDataOK;
    private ClassRawData classRawDataFailing;

    @Before
    public void setup() {
        methodRawDataFailing = new MethodRawData( 1, 1, "className", "methodName" ); //$NON-NLS-1$ //$NON-NLS-2$
        methodRawDataFailing.setMLValue( 16 );
        methodRawDataFailing.setCCValue( 22 );
        methodRawDataOK = new MethodRawData( 1, 1, "className", "methodName" ); //$NON-NLS-1$ //$NON-NLS-2$
        methodRawDataOK.setMLValue( 1 );
        methodRawDataOK.setCCValue( 2 );

        ususModel = mock( UsusModel.class );
        when( new Integer( ususModel.getNumberOf( CodeProportionUnit.CLASS ) ) ).thenReturn( new Integer( 100 ) );

        classRawDataOK = mock( ClassRawData.class );
        when( new Integer( classRawDataOK.getNumberOfMethods() ) ).thenReturn( new Integer( 1 ) );
        when( new Integer( classRawDataOK.getCCDResult() ) ).thenReturn( new Integer( 0 ) );

        classRawDataFailing = mock( ClassRawData.class );
        when( new Integer( classRawDataFailing.getNumberOfMethods() ) ).thenReturn( new Integer( 128 ) );
        when( new Integer( classRawDataFailing.getCCDResult() ) ).thenReturn( new Integer( 1000 ) );

    }

    @Test
    public void testGetLabel() {
        assertEquals( "Average component dependency", ACD.getLabel() ); //$NON-NLS-1$
        assertEquals( "Cyclomatic complexity", CC.getLabel() ); //$NON-NLS-1$
        assertEquals( "Warnings", CW.getLabel() ); //$NON-NLS-1$
        assertEquals( "Class size", KG.getLabel() ); //$NON-NLS-1$
        assertEquals( "Method length", ML.getLabel() ); //$NON-NLS-1$
        assertEquals( "Packages in cycles", PC.getLabel() ); //$NON-NLS-1$
        assertEquals( "Test coverage", TA.getLabel() ); //$NON-NLS-1$
    }

    @Test
    public void testGetUnit() {
        assertEquals( CodeProportionUnit.CLASS, ACD.getUnit() );
        assertEquals( CodeProportionUnit.METHOD, CC.getUnit() );
        assertEquals( CodeProportionUnit.ANYFILE, CW.getUnit() );
        assertEquals( CodeProportionUnit.CLASS, KG.getUnit() );
        assertEquals( CodeProportionUnit.METHOD, ML.getUnit() );
        assertEquals( CodeProportionUnit.PACKAGE, PC.getUnit() );
        assertEquals( CodeProportionUnit.LINE, TA.getUnit() );
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
        // assertFalse( ACD.isViolatedBy( classRawDataOK ) );
        // TODO geht hier nicht wg. Zugriff aufs Usus Model
        assertFalse( CC.isViolatedBy( classRawDataOK ) );
        assertFalse( CW.isViolatedBy( classRawDataOK ) );
        assertFalse( KG.isViolatedBy( classRawDataOK ) );
        assertFalse( ML.isViolatedBy( classRawDataOK ) );
        assertFalse( PC.isViolatedBy( classRawDataOK ) );
        assertFalse( TA.isViolatedBy( classRawDataOK ) );
    }

    @Test
    public void testIsViolatedByClassRawDataFailing() {
        // assertTrue( ACD.isViolatedBy( classRawDataFailing ) );
        // TODO geht hier nicht wg. Zugriff aufs Usus Model
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
        assertFalse( TA.isMethodKind() );
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
