package org.projectusus.ui.internal.proportions.cockpit;

import static org.junit.Assert.assertThat;
import static org.projectusus.matchers.UsusMatchers.endsWith;
import static org.projectusus.matchers.UsusMatchers.equalTo;
import static org.projectusus.matchers.UsusMatchers.not;
import static org.projectusus.matchers.UsusMatchers.startsWith;

import java.util.Date;

import org.junit.Test;
import org.projectusus.ui.internal.Snapshot;

public class SnapshotInfoBuilderTest {

    private static final int MINUTES = 60;
    private static final int HOURS = MINUTES * 60;
    private static final int DAYS = HOURS * 24;

    private final SnapshotInfoBuilder builder = new SnapshotInfoBuilder();
    private final Date now = new Date();

    @Test
    public void buildInfo() {
        String info = builder.buildInfo( new Snapshot() );
        assertThat( info, startsWith( "Snapshot taken at " ) ); //$NON-NLS-1$
        assertThat( info, endsWith( " (just now)" ) ); //$NON-NLS-1$
    }

    @Test
    public void buildAgoMessage_InTheFuture() {
        assertThat( builder.buildAgoMessage( secondsAgo( -1 ), now ), equalTo( "in the future" ) ); //$NON-NLS-1$
    }

    @Test
    public void buildAgoMessage_JustNow() {
        assertThat( builder.buildAgoMessage( now ), equalTo( "just now" ) ); //$NON-NLS-1$
        assertThat( builder.buildAgoMessage( now, now ), equalTo( "just now" ) ); //$NON-NLS-1$
        assertThat( builder.buildAgoMessage( secondsAgo( 9 ), now ), equalTo( "just now" ) ); //$NON-NLS-1$
        assertThat( builder.buildAgoMessage( secondsAgo( 10 ), now ), not( equalTo( "just now" ) ) ); //$NON-NLS-1$
    }

    @Test
    public void buildAgoMessage_LessThanAMinuteAgo() {
        assertThat( builder.buildAgoMessage( 10 ), equalTo( "less than a minute ago" ) ); //$NON-NLS-1$
        assertThat( builder.buildAgoMessage( 59 ), equalTo( "less than a minute ago" ) ); //$NON-NLS-1$
        assertThat( builder.buildAgoMessage( 60 ), not( equalTo( "less than a minute ago" ) ) ); //$NON-NLS-1$
    }

    @Test
    public void buildAgoMessage_Minutes() {
        checkMessage( 1, MINUTES, "about a minute ago" ); //$NON-NLS-1$
        checkMessage( 2, MINUTES, "about 2 minutes ago" ); //$NON-NLS-1$
        checkMessage( 59, MINUTES, "about 59 minutes ago" ); //$NON-NLS-1$
    }

    @Test
    public void buildAgoMessage_Hours() {
        checkMessage( 1, HOURS, "about an hour ago" ); //$NON-NLS-1$
        checkMessage( 2, HOURS, "about 2 hours ago" ); //$NON-NLS-1$
        checkMessage( 23, HOURS, "about 23 hours ago" ); //$NON-NLS-1$
    }

    @Test
    public void buildAgoMessage_Days() {
        checkMessage( 1, DAYS, "about a day ago" ); //$NON-NLS-1$
        checkMessage( 2, DAYS, "about 2 days ago" ); //$NON-NLS-1$
    }

    public void checkMessage( int amount, int unit, String message ) {
        assertThat( builder.buildAgoMessage( amount * unit ), equalTo( message ) );
        assertThat( builder.buildAgoMessage( (amount + 1) * unit - 1 ), equalTo( message ) );
        assertThat( builder.buildAgoMessage( (amount + 1) * unit ), not( equalTo( message ) ) );
    }

    private Date secondsAgo( int amount ) {
        return millisAgo( amount * 1000 );
    }

    private Date millisAgo( int amount ) {
        return new Date( now.getTime() - amount );
    }
}
