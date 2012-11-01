package org.projectusus.c4j;

import java.io.PrintWriter;

import net.sourceforge.c4j.ContractBase;

import org.projectusus.core.UsusCorePlugin;

public class UsusContractBase<T> extends ContractBase<T> {
    public UsusContractBase( T target ) {
        super( target );
    }

    public void assertThat( boolean condition, String message ) {
        String contract = this.getClass().getName();
        String target = m_target.getClass().getName();
        String extendedMessage = "Contract: " + contract + ", Target: " + target + " - " + message;
        assertStatic( condition, extendedMessage );
    }

    public static void assertStatic( boolean condition, String message ) {
        if( !condition ) {
            PrintWriter writer = UsusCorePlugin.getDefault().getC4JFileWriter();
            writer.println( message );
            writer.flush();

        }
    }
}
