package org.projectusus.testutil.parser;

import java.util.Map;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;

@SuppressWarnings( "restriction" )
public abstract class ASTTestBase {

    protected long complianceLevel = ClassFileConstants.JDK1_5;

    private String getVersionFor( long level ) {
        if( level == ClassFileConstants.JDK1_3 ) {
            return CompilerOptions.VERSION_1_3;
        } else if( this.complianceLevel == ClassFileConstants.JDK1_4 ) {
            return CompilerOptions.VERSION_1_4;
        } else if( this.complianceLevel == ClassFileConstants.JDK1_5 ) {
            return CompilerOptions.VERSION_1_5;
        } else if( this.complianceLevel == ClassFileConstants.JDK1_6 ) {
            return CompilerOptions.VERSION_1_6;
        } else if( this.complianceLevel == ClassFileConstants.JDK1_7 ) {
            return CompilerOptions.VERSION_1_7;
        }
        return "";
    }

    @SuppressWarnings( "unchecked" )
    protected Map<String, String> getCompilerOptions() {
        Map<String, String> options = new CompilerOptions().getMap();
        options.put( CompilerOptions.OPTION_ReportUnusedLocal, CompilerOptions.IGNORE );
        options.put( CompilerOptions.OPTION_Compliance, getVersionFor( this.complianceLevel ) );
        options.put( CompilerOptions.OPTION_Source, getVersionFor( this.complianceLevel ) );
        options.put( CompilerOptions.OPTION_TargetPlatform, getVersionFor( this.complianceLevel ) );
        options.put( CompilerOptions.OPTION_LocalVariableAttribute, CompilerOptions.GENERATE );
        options.put( CompilerOptions.OPTION_ReportUnusedPrivateMember, CompilerOptions.WARNING );
        options.put( CompilerOptions.OPTION_ReportUnusedImport, CompilerOptions.WARNING );
        options.put( CompilerOptions.OPTION_ReportLocalVariableHiding, CompilerOptions.WARNING );
        options.put( CompilerOptions.OPTION_ReportFieldHiding, CompilerOptions.WARNING );
        options.put( CompilerOptions.OPTION_ReportPossibleAccidentalBooleanAssignment, CompilerOptions.WARNING );
        options.put( CompilerOptions.OPTION_ReportSyntheticAccessEmulation, CompilerOptions.WARNING );
        options.put( CompilerOptions.OPTION_PreserveUnusedLocal, CompilerOptions.PRESERVE );
        options.put( CompilerOptions.OPTION_ReportUnnecessaryElse, CompilerOptions.WARNING );
        options.put( CompilerOptions.OPTION_ReportDeadCode, CompilerOptions.WARNING );
        return options;
    }

    public ASTNode runConversion( int astLevel, String source, boolean resolveBindings, boolean statementsRecovery, boolean bindingsRecovery, String unitName ) {

        ASTParser parser = ASTParser.newParser( astLevel );
        parser.setSource( source.toCharArray() );
        parser.setEnvironment( null, null, null, true );
        parser.setResolveBindings( resolveBindings );
        parser.setStatementsRecovery( statementsRecovery );
        parser.setBindingsRecovery( bindingsRecovery );
        parser.setCompilerOptions( getCompilerOptions() );
        parser.setUnitName( unitName );
        return parser.createAST( null );
    }

}
