package org.projectusus.ui.internal;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableCollection;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

public class Snapshot implements Iterable<AnalysisDisplayEntry> {

    private static final Collection<AnalysisDisplayEntry> NO_ENTRIES = emptyList();

    private final Collection<AnalysisDisplayEntry> entries;
    private final Date date = new Date();

    public Snapshot() {
        this( NO_ENTRIES );
    }

    public Snapshot( Collection<AnalysisDisplayEntry> entries ) {
        this.entries = unmodifiableCollection( entries );
    }

    public Date getDate() {
        return date;
    }

    public boolean isEmpty() {
        return entries.isEmpty();
    }

    public Iterator<AnalysisDisplayEntry> iterator() {
        return entries.iterator();
    }
}
