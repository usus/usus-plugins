// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.projectsettings;

public enum CompilerWarningSettingCode {

    annotationSuperInterface, assertIdentifier, autoboxing, deprecation,
    // deprecationInDeprecatedCode=disabled
    // deprecationWhenOverridingDeprecatedMethod=disabled
    discouragedReference, emptyStatement, enumIdentifier, fallthroughCase,
    // fatalOptionalError=enabled
    fieldHiding, finalParameterBound, finallyBlockNotCompletingNormally, forbiddenReference, hiddenCatchBlock, incompatibleNonInheritedInterfaceMethod, incompleteEnumSwitch, indirectStaticAccess, localVariableHiding, methodWithConstructorName, missingDeprecatedAnnotation, missingOverrideAnnotation, missingSerialVersion, noEffectAssignment, noImplicitStringConversion, nonExternalizedStringLiteral, nullReference, overridingPackageDefaultMethod, parameterAssignment, possibleAccidentalBooleanAssignment, potentialNullReference, rawTypeReference, redundantNullCheck, redundantSuperinterface,
    // specialParameterHidingField=disabled
    staticAccessReceiver,
    // suppressWarnings=enabled
    syntheticAccessEmulation, typeParameterHiding, uncheckedTypeOperation, undocumentedEmptyBlock, unhandledWarningToken, unnecessaryElse, unnecessaryTypeCheck, unqualifiedFieldAccess, unusedDeclaredThrownException,
    // unusedDeclaredThrownExceptionExemptExceptionAndThrowable=disabled
    // unusedDeclaredThrownExceptionIncludeDocCommentReference=disabled
    // unusedDeclaredThrownExceptionWhenOverriding=disabled
    unusedImport, unusedLabel, unusedLocal, unusedParameter,
    // unusedParameterIncludeDocCommentReference=enabled
    // unusedParameterWhenImplementingAbstract=disabled
    // unusedParameterWhenOverridingConcrete=disabled
    unusedPrivateMember, unusedWarningToken, varargsArgumentNeedCast;

    private static final String ORG_ECLIPSE_JDT_CORE_COMPILER_PROBLEM = "org.eclipse.jdt.core.compiler.problem."; //$NON-NLS-1$

    public String getSetting() {
        return ORG_ECLIPSE_JDT_CORE_COMPILER_PROBLEM + name();
    }

    public static CompilerWarningSettingCode fromString( String codeString ) {
        CompilerWarningSettingCode[] values = values();
        for( CompilerWarningSettingCode code : values ) {
            if( code.getSetting().equals( codeString ) ) {
                return code;
            }
        }
        return null;
    }

    public static boolean hasCode( String codeWithPrefix ) {
        return fromString( codeWithPrefix ) != null;
    }
}
