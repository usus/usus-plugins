// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.filerelations.model.Classname;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.filerelations.model.WrappedTypeBinding;

public class ClassRawDataTest {

    private static final String CLASSNAME = "ClassName"; //$NON-NLS-1$
    private static final String PACKAGENAME = "PackageName"; //$NON-NLS-1$
    private static final String METHODNAME1 = "methodname1"; //$NON-NLS-1$
    private static final int SOURCEPOSITION = 77;
    private static final int LINENUMBER = 12;

    private ClassRawData classRawData;
    private MethodDeclaration method1;

    @Before
    public void setup() {
        WrappedTypeBinding typeBinding = setupMocks();
        classRawData = new ClassRawData( typeBinding, CLASSNAME, SOURCEPOSITION, LINENUMBER );
        method1 = mock( MethodDeclaration.class );
        initMethod( method1, METHODNAME1 );
    }

    private WrappedTypeBinding setupMocks() {
        WrappedTypeBinding typeBinding = mock( WrappedTypeBinding.class );
        IFile resourceMock = mock( IFile.class );
        when( typeBinding.getUnderlyingResource() ).thenReturn( resourceMock );
        when( typeBinding.getClassname() ).thenReturn( new Classname( CLASSNAME ) );
        when( typeBinding.getPackagename() ).thenReturn( Packagename.of( PACKAGENAME, null ) );
        return typeBinding;
    }

    @Test
    public void numberOfNoMethods() {
        // assertEquals( 1, classRawData.getNumberOf( CodeProportionUnit.CLASS ) );
        // assertEquals( 0, classRawData.getNumberOf( CodeProportionUnit.METHOD ) );
    }

    @Test
    public void numberOf1Method() {
        classRawData.putData( method1, MetricsResults.CC, 0 );
        // assertEquals( 1, classRawData.getNumberOf( CodeProportionUnit.CLASS ) );
        // assertEquals( 1, classRawData.getNumberOf( CodeProportionUnit.METHOD ) );
    }

    @Test
    public void violationCountNoMethods() {
        // assertEquals( 0, classRawData.getViolationCount( CodeProportionKind.KG ) );
        // assertEquals( 0, classRawData.getViolationCount( CodeProportionKind.CC ) );
        // assertEquals( 0, classRawData.getViolationCount( CodeProportionKind.ML ) );
    }

    @Test
    public void violationCountCC1Method() {
        int value = 6;
        classRawData.putData( method1, MetricsResults.CC, value );
        // assertEquals( 0, classRawData.getViolationCount( CodeProportionKind.KG ) );
        // assertEquals( 1, classRawData.getViolationCount( CodeProportionKind.CC ) );
        // assertEquals( 0, classRawData.getViolationCount( CodeProportionKind.ML ) );
    }

    @Test
    public void violationCountML1Method() {
        int value = 16;
        classRawData.putData( method1, MetricsResults.ML, value );
        // assertEquals( 0, classRawData.getViolationCount( CodeProportionKind.KG ) );
        // assertEquals( 0, classRawData.getViolationCount( CodeProportionKind.CC ) );
        // assertEquals( 1, classRawData.getViolationCount( CodeProportionKind.ML ) );
    }

    @Test
    public void addHotspotsNoMethods() {
        // List<IHotspot> nameList = new ArrayList<IHotspot>();
        // classRawData.addToHotspots( CodeProportionKind.KG, nameList );
        // assertEquals( 0, nameList.size() );
        // classRawData.addToHotspots( CodeProportionKind.CC, nameList );
        // assertEquals( 0, nameList.size() );
        // classRawData.addToHotspots( CodeProportionKind.ML, nameList );
        // assertEquals( 0, nameList.size() );
    }

    // // TODO missing: KG tests

    private void initMethod( MethodDeclaration method, String methodname ) {
        SimpleName methodName = mock( SimpleName.class );
        when( method.getName() ).thenReturn( methodName );
        when( methodName.toString() ).thenReturn( methodname );
    }
}
