public class Tasks {

    // Tasks for USUS 1.0

    void nicole_makeMetricsPluggable() {
        // TODO Metriken pluggable machen: Collectors zufŸügen, Werte in RawData Struktur werden in einer Map
        // gehalten, pro Metrik ein gespeichertes Werteobjekt.
    }

    void namensgebung() {
        // TODO Namensgebung geradeziehen: Was?
    }

    void documentationOfCalculations() {
        // TODO Doku der Usus-Interna. Welche Schwellwerte? Was sind die Level? etc.
    }

    void maximizeTablesInCockpitAndHotspots() {
        // TODO Tabellen sollten ab einer gewissen Fensterbreite "magnetisch" die ganze Breite des Fensters einnehmen.
    }

    void useEclipseDisplayInPackagenames() {
        // TODO Packagenamen üŸberall mit Eclipse Appearance Regeln darstellen (Package View, Hotspots, ...).
    }

    void createMudHoleHotspots() {
        // TODO Aggregierte Hotspots = "Schlammlšöcher"
    }

    void createExplanatoryDocumentation() {
        // TODO Doku: Warum soll ich meinen Code äŠndern? Wo kann ich weiterlesen?
        // - Position Paper: Usus vs. Checkstyle, PMD, ...
    }

    void bugACDAnalysis() {
        // TODO static fields are not regarded. fix it.
    }

    void createPackageView() {
        // TODO PackageView: Packages im Package Graph auswäŠhlen, die enthaltenen Klassen im Class View darstellen
    }

    void improveGraphLayoutsWithZest() {
        // TODO ZEST Layouting verbessern.
    }

    // ///////// Tasks for versions after USUS 1.0

    void pimpDependencyGraphForRemoving() {
        // TODO DependencyGraph: Man kann ausgewäŠhlte Knoten aus den Graphen entfernen
    }

    void createShadowForMarc() {
        // TODO Schatten am InfoPresenter (extra fŸür Marc :-)
    }

    void createTrends() {
        // TODO Wertehistorie: Verbesserte und verschlechterte Werte ausweisen und danach sortieren.
    }

    void makeJobCancellable() {
        // TODO UsusModel: Berechnung abbrechen, wenn der Benutzer dies verlangt. Danach voller Rebuild.
    }

    void createNewMetrics() {
        // TODO Metriken: # SuppressWarnings
        // Code Smells
        // Feature Envy
        // Useless Comments
    }

    void createRealAcd() {
        // TODO RealACD ("Metriken ohne Augenwischerei"): Interface und Implementierung unifizieren.
    }

    void createDifferentCohesionMetrics() {
        // TODO KohäŠsionsmetriken werfen alle Arten von Referenzen in einen Topf. Unterscheiden!
        // - Parameter sind keine Referenzen in unserem Sinne
        // - Attribute, die State machen, schon.
        // Verschiedene ACD-Klassen machen und schauen, wie sich die Geflechte verhalten.
    }

    void repairAndExtendCheatsheet() {
        // TODO CheatSheet reparieren und erweitern
    }

    void createPackagecycleHotspots() {
        // TODO Hotspots fŸür Package Cycles?? Hotspot ^=^ Zyklus-Objekt. Relevant: # Packages im Zyklus
    }

    void createNatureInsteadSeperateView() {
        // TODO Projektaktivierung in den Project Prefs statt in einem eigenen View?
        // Decorator am Projekt. Eigene Nature zufŸügen.
    }

    void createRefactorings() {
        // TODO Refactorings einbauen: Feature Envy Code an die richtige Stelle verschieben.
    }

    void createRemoveUselessComments() {
        // TODO "Remove useless comments" entfernt alle nutzlosen Kommentare (oder alle? ;-)
    }

    void createCancelInGraphUpdates() {
        // TODO Graph Views: Canceln eines wartenden Updates (durch Verschieben des Sliders ausgelšöst)?
    }
}
