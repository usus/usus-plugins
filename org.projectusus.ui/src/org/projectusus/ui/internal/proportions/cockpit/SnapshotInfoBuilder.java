package org.projectusus.ui.internal.proportions.cockpit;

import static java.lang.Long.valueOf;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.Date;

import org.projectusus.ui.internal.Snapshot;

public class SnapshotInfoBuilder {

    public String buildInfo( Snapshot snapshot ) {
        Date date = snapshot.getDate();
        return "Snapshot taken at " + format( date ) + " (" + buildAgoMessage( date ) + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    protected String buildAgoMessage( Date date ) {
        return buildAgoMessage( date, new Date() );
    }

    protected String buildAgoMessage( Date timeInPast, Date now ) {
        return buildAgoMessage( (now.getTime() - timeInPast.getTime()) / 1000 );
    }

    public String buildAgoMessage( long seconds ) {
        StringBuilder result = new StringBuilder();
        buildAgoMessageImpl( seconds, result );
        return result.toString();
    }

    private boolean buildAgoMessageImpl( long seconds, StringBuilder result ) {
        return inDays( seconds, result ) || inHours( seconds, result ) || inMinutes( seconds, result ) || inSeconds( seconds, result );
    }

    private boolean inSeconds( long seconds, StringBuilder result ) {
        if( seconds < 0 ) {
            result.append( "in the future" ); //$NON-NLS-1$
        } else if( seconds < 10 ) {
            result.append( "just now" ); //$NON-NLS-1$
        } else {
            result.append( "less than a minute ago" ); //$NON-NLS-1$
        }
        return true;
    }

    private boolean inMinutes( long seconds, StringBuilder result ) {
        return inUnit( seconds, 60, "about a minute ago", "about {0} minutes ago", result ); //$NON-NLS-1$ //$NON-NLS-2$
    }

    private boolean inHours( long seconds, StringBuilder result ) {
        return inUnit( seconds, 60 * 60, "about an hour ago", "about {0} hours ago", result ); //$NON-NLS-1$ //$NON-NLS-2$
    }

    private boolean inDays( long seconds, StringBuilder result ) {
        return inUnit( seconds, 60 * 60 * 24, "about a day ago", "about {0} days ago", result ); //$NON-NLS-1$ //$NON-NLS-2$
    }

    private boolean inUnit( long seconds, int denominator, String singleUnit, String multipleUnits, StringBuilder result ) {
        long diff = seconds / denominator;
        if( diff == 1 ) {
            result.append( singleUnit );
        } else if( diff > 1 ) {
            result.append( MessageFormat.format( multipleUnits, valueOf( diff ) ) );
        }
        return diff > 0;
    }

    private String format( Date date ) {
        return DateFormat.getDateTimeInstance( DateFormat.SHORT, DateFormat.SHORT ).format( date );
    }

}
