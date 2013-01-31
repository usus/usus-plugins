package org.projectusus.c4j;

import net.sourceforge.c4j.ContractBase;

public class UsusContractBase<T> extends ContractBase<T> {
    public UsusContractBase( T target ) {
        super( target );
    }

    public void assertThat( boolean condition, String message ) {
        String contract = this.getClass().getName();
        String target = m_target.getClass().getName();
        String extendedMessage = "Contract: " + contract + ", Target: " + target + " - " + message;
        C4JFileWriter.assertStatic( condition, extendedMessage );
        assert condition : extendedMessage;
    }
}
