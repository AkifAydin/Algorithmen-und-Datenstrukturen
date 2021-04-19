package de.hawhamburg.hamann.ad.sortingworkbench.util;

import de.hawhamburg.hamann.ad.sortingworkbench.RunInfo;

/**
 * Event mit Informationen über einen einzelnen
 * Durchlauf der Workbench mit einer Anzahl von Elementen
 * mit allen Sortern.
 */
public class ProgressEvent {
    private final String message;

    private final RunInfo runConfig;

    public ProgressEvent(RunInfo runConfig, String message) {
        this.message   = message;
        this.runConfig = runConfig;
    }

    public String getMessage() {
        return message;
    }

    public RunInfo getRunConfig() {
        return runConfig;
    }
}
