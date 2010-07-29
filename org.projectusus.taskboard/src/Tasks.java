public class Tasks {

    // Tasks for USUS 1.0
	
	void checkContextMenuInCockpit() {
		// TODO soll das so bleiben?
	}

    void nicole_documentationOfCalculations() {
        // TODO Doku der Usus-Interna. Welche Schwellwerte? Was sind die Level? etc.
    }

    void andreas_marc_maximizeTablesInCockpitAndHotspots() {
        // TODO Tabellen sollten ab einer gewissen Fensterbreite "magnetisch" die ganze Breite des Fensters einnehmen.
    }

    void nicole_marc_createExplanatoryDocumentation() {
        // TODO Doku: Warum soll ich meinen Code äŠndern? Wo kann ich weiterlesen?
        // - Position Paper: Usus vs. Checkstyle, PMD, ...
        // Dies fliesst ein in den Eclipse Magazin Artikel 6.10
    }

    void marc_nicole_createPackageView() {
        // TODO PackageView: Packages im Package Graph auswäŠhlen, die enthaltenen Klassen im Class View darstellen
    }

    void andreas_createTrends() {
        // TODO Wertehistorie: Verbesserte und verschlechterte Werte ausweisen und danach sortieren.
    }

    // ----------------

    void nicole_evtl_marc_bugACDAnalysis() {
        // TODO static fields are not regarded. fix it.
    }

    void nicole_makeMetricsPluggable() {
        // TODO Metriken pluggable machen: Collectors zufŸügen, Werte in RawData Struktur werden in einer Map
        // gehalten, pro Metrik ein gespeichertes Werteobjekt.
    }

    // ///////// Tasks for versions after USUS 1.0

    void createMudHoleHotspots() {
        // TODO Aggregierte Hotspots = "Schlammlšöcher"
    }

    void pimpDependencyGraphForRemoving() {
        // TODO DependencyGraph: Man kann ausgewäŠhlte Knoten aus den Graphen entfernen
    }

    void makeJobCancellable() {
        // TODO UsusModel: Berechnung abbrechen, wenn der Benutzer dies verlangt. Danach voller Rebuild.
    }

    void createNewMetrics() {
        // TODO Metriken: # SuppressWarnings
        // Code Smells
        // Feature Envy
        // Useless Comments
        // Data Classes
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

    void createNatureInsteadOfASeparateView() {
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
