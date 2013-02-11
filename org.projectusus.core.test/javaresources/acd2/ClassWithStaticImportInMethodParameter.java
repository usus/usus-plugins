package acd2;

import static acd.Acd.STRING;
import static java.util.Locale.ENGLISH;
import static org.joda.time.format.DateTimeFormat.forPattern;

public class ClassWithStaticImportInMethodParameter {

    private void convertTime() {
        forPattern( STRING ).withLocale( ENGLISH );
    }

}
