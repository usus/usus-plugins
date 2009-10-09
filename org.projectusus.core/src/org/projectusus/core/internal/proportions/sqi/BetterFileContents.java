// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.sqi;

import com.puppycrawl.tools.checkstyle.api.FileContents;

public class BetterFileContents {

    private final FileContents contents;

    public BetterFileContents( FileContents contents ) {
        super();
        this.contents = contents;
    }

    public boolean lineIsBlank( int i ) {
        return contents.lineIsBlank( i );
    }

    public boolean lineIsComment( int i ) {
        return contents.lineIsComment( i );
    }
}
