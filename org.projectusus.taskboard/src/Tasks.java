public class Tasks {

    // ///////// Tasks to complete before Eclipse Magazin 6.10 comes out (27. September 2010)

    void evtl_andreas_tooltipsForStatistics() {
        // TODO Tooltips für Statistiken im Cockpit
    }

    void marc_dynamicHelpForStatistics() {
        // TODO Dynamische Hilfe des Cockpits und des Hotspot Views sollten Infos zur Berechnung
        // der Metriken und Statistiken zur Verfügung stellen
    }

    void nicole_documentationOfCalculations() {
        // TODO Doku der Usus-Interna. Welche Schwellwerte? Was sind die Level? etc.
        // geht ein in dynamic help
    }

    void marc_statusHeaderzeileInDenGraphenVerbessern() {
        // TODO die Statuszeile wird leicht zu lang. Wie damit umgehen?
        // Tooltip? Zusammenfassung?
        // keine kryptischen Zeichen unter Windows
        // Hilfebutton mit dahinterliegendem InfoPresenter, der alle Filterregeln auflistet
    }

    void nicole_statusHeaderzeileInDenHotspotViewsVerbessern() {
        // TODO Texte in den Statistics etwas aussagekräftiger formulieren
    }

    void nicole_evtl_marc_bugACDAnalysis() {
        // TODO static fields are not regarded. fix it.
    }

    void inprogress_nicole_makeMetricsPluggable() {
        // TODO Metriken pluggable machen: Collectors zufŸügen, Werte in RawData Struktur werden in einer Map
        // gehalten, pro Metrik ein gespeichertes Werteobjekt.
    }

    // ///////// Tasks for USUS 1.0

    void usabilityFuerDieGraphenPolieren() {
        // TODO - öffnet man einen Class Graph aus dem Package Graphen, sind nur zyklische aktiv
        // TODO - die beiden Class Graph Fenster unifizieren? Mit Filter auf externe Kanten
    }

    void codeWeiterOrganisieren() {
        // TODO Package Zyklen raus
        // TODO Warnings wegen internal packages entfernen
    }

    void checkContextMenuInCockpit() {
        // TODO soll das so bleiben?
    }

    void andreas_marc_maximizeTablesInCockpitAndHotspots() {
        // TODO Tabellen sollten ab einer gewissen Fensterbreite "magnetisch" die ganze Breite des Fensters einnehmen.
    }

    void nicole_marc_createExplanatoryDocumentation() {
        // TODO Doku: Warum soll ich meinen Code äŠndern? Wo kann ich weiterlesen?
        // - Position Paper: Usus vs. Checkstyle, PMD, ...
        // Sollen wir noch mehr schreiben oder genügt der Eclipse Magazin Artikel 6.10?
    }

    void rightAlignValueColumnInHotspotsView() {
        // TODO Spalte rechtsbündig machen
    }

    void removeUseLessTreeNodeInCockpit() {
        // TODO Momentan gibt es nur eine Art von Eintrag im Cockpit -> Unnötiger Wurzelknoten
    }

    // ///////// Tasks for versions after USUS 1.0

    void addLayoutCacheForZest() {
        // TODO "Layout Cache", der sich die Positionen der Knoten in den Graphen
        // merkt, auch über ein Ein- und Ausblenden der Knoten hinweg
    }

    void statisticsForRelations() {
        // TODO Statistics für die Relation Metrics bauen. Daraus ein Pattern extrahieren.
    }

    void createMudHoleHotspots() {
        // TODO Aggregierte Hotspots = "Schlammlšöcher"
        // Dazu brauchen wir post-visit-hooks für den Baumdurchlauf
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

    void ususEinschraenkenAufBearbeitetes() {
        // TODO wie genau soll diese Einschränkung aussehen? -> Leif
    }

    void testabdeckung() {
        // TODO was davon soll eigentlich in Usus hinein?
        // Evtl. EclEmma um eine projektübergreifende Summe ergänzen?
        // Erfassung der Testabdeckung + Summenbildung einschränken auf die Usus-Projekte?
    }
}
