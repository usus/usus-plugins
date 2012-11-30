package org.projectusus.ui.viewer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( value = { ElementType.FIELD } )
@Retention( RetentionPolicy.RUNTIME )
public @interface UsusTreeColumn {

    // column weight (percentage of overall width in the table that this column takes)
    int weight() default 5;

    ColumnAlignment align() default ColumnAlignment.LEFT;

    boolean numeric() default false;

    boolean sortable() default true;

}
