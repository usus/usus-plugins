package org.projectusus.ui.internal;

public class DisplayCategories {

    private AnalysisDisplayCategory[] categories;

    public DisplayCategories() {
        super();
        categories = new AnalysisDisplayCategory[0];
    }

    public AnalysisDisplayCategory[] getCategories() {
        return categories;
    }

    void replaceCategories( AnalysisDisplayCategory... categories ) {
        this.categories = categories;
    }

}
