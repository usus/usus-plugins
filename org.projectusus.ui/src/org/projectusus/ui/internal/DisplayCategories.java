package org.projectusus.ui.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DisplayCategories {

    private IDisplayCategory[] categories;

    public DisplayCategories() {
        super();
        categories = new IDisplayCategory[0];
    }

    public IDisplayCategory[] getCategories() {
        return categories;
    }

    void replaceCategories( IDisplayCategory... cats ) {
        this.categories = cats;
    }

    List<AnalysisDisplayEntry> getAllEntries() {
        List<AnalysisDisplayEntry> entries = new ArrayList<AnalysisDisplayEntry>();
        for( IDisplayCategory category : categories ) {
            entries.addAll( Arrays.asList( category.getChildren() ) );
        }
        return entries;
    }

}
