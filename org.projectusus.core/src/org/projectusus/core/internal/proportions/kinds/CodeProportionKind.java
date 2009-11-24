package org.projectusus.core.internal.proportions.kinds;

import org.projectusus.core.internal.proportions.sqi.ClassRawData;
import org.projectusus.core.internal.proportions.sqi.CodeProportionUnit;
import org.projectusus.core.internal.proportions.sqi.MethodRawData;

public abstract class CodeProportionKind {

    private final String label;

    public static final CodeProportionKind ACD = new CodeProportionACD();
    public static final CodeProportionKind CC = new CodeProportionCC();
    public static final CodeProportionKind CW = new CodeProportionCW();
    public static final CodeProportionKind KG = new CodeProportionKG();
    public static final CodeProportionKind PC = new CodeProportionPC();
    public static final CodeProportionKind TA = new CodeProportionTA();

    public CodeProportionKind( String label ) {
        this.label = label;
    }

    public abstract CodeProportionUnit getUnit();

    public double getCalibration() {
        return 1.0;
    }

    public boolean isViolatedBy( MethodRawData methodResult ) {
        return false;
    }

    public boolean isViolatedBy( ClassRawData methodResult ) {
        return false;
    }

    public boolean isMethodTest() {
        return false;
    }

    public int getValueFor( MethodRawData methodResults ) {
        return 0;
    }

    public String getLabel() {
        return label;
    }
}
