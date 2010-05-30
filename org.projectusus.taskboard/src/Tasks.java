public class Tasks {
    void namensgebung() {
        // TODO Namensgebung geradeziehen: Kind -> Metrics. Was noch?
    }

    void introduceNullObjects() {
        // TODO Null-Objekte in die RawData-Hierarchie einfŸühren.
    }

    void maximizeTablesInCockpitAndHotspots() {
        // TODO Tabellen sollten ab einer gewissen Fensterbreite "magnetisch" die ganze Breite des Fensters einnehmen.
    }

    void useEclipseDisplayInPackagenames() {
        // TODO Packagenamen üŸberall mit Eclipse Appearance Regeln darstellen (Package View, Hotspots, ...).
    }

    void changesForCockpit() {
        // TODO Cockpit: Ein Trend-Pfeil pro Metrik.
        // Werte seit letztem CheckIn / CheckOut? Oder seit dem Start? Oder mit Reset-Button?
        // Dazu Level sehr exakt bestimmen.
    }

    void makeCalculationsPluggable() {
        // TODO Berechnungsalgorithmen fŸür die Violations pluggable machen. Dazu Implementierung eines
        // Visitor-Collectors, der den RawData Baum besucht und sich aus allen Knoten das GewŸünschte
        // zusammensucht: Hotspots, Violations, Metrikwerte etc.
    }

    void convertViolationFunction() {
        // TODO Violations: Linear statt konstant. Ergibt sich ganz einfach aus dem vorherigen.
    }

    void makeJobCancellable() {
        // TODO UsusModel: Berechnung abbrechen, wenn der Benutzer dies verlangt. Danach voller Rebuild.
    }

    void improveGraphLayoutsWithZest() {
        // TODO ZEST Layouting verbessern.
    }

    void createMudHoleHotspots() {
        // TODO Aggregierte Hotspots = "Schlammlšöcher"
    }

    void makeMetricsPluggable() {
        // TODO Metriken pluggable machen: Collectors zufŸügen, Werte in RawData Struktur werden in einer Map
        // gehalten, pro Metrik ein gespeichertes Werteobjekt.
    }

    void createNewMetrics() {
        // TODO Metriken: # SuppressWarnings
        // Code Smells
        // Feature Envy
        // Useless Comments
        // Klassenzyklen innerhalb / zwischen Paketen
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

    void pimpDependencyGraphForRemoving() {
        // TODO DependencyGraph: Man kann ausgewäŠhlte Knoten aus den Graphen entfernen
    }

    void createPackageView() {
        // TODO PackageView: Packages auswäŠhlen, die enthaltenen Klassen im Class View darstellen
    }

    void createTrends() {
        // TODO Wertehistorie: Verbesserte und verschlechterte Werte ausweisen und danach sortieren.
    }

    void createExplanatoryDocumentation() {
        // TODO Doku: Warum soll ich meinen Code äŠndern? Wo kann ich weiterlesen?
        // - Position Paper: Usus vs. Checkstyle, PMD, ...
    }

    void repairAndExtendCheatsheet() {
        // TODO CheatSheet reparieren und erweitern
    }

    void createProjectYellowHotspots() {
        // TODO Hotspots füŸr YellowCount am Projekt
    }

    void createPackagecycleHotspots() {
        // TODO Hotspots fŸür Package Cycles?? Hotspot ^=^ Zyklus-Objekt. Relevant: # Packages im Zyklus
    }

    void createShadowForMarc() {
        // TODO Schatten am InfoPresenter (extra fŸür Marc :-)
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
