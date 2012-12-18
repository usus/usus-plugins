package acd_staticimport;

import static acd_staticimport.ClassWithConstant.DATE_TIME_PATTERN;
import static java.util.Locale.ENGLISH;
import static org.joda.time.format.DateTimeFormat.forPattern;

public class ClassWithStaticImportInMethodParameter {

    private void convertTime() {
        forPattern( DATE_TIME_PATTERN ).withLocale( ENGLISH );
    }

}
