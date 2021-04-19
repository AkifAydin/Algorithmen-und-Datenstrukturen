package de.hawhamburg.hamann.ad.sortingworkbench;

import de.hawhamburg.hamann.ad.sortingworkbench.sorter.Sorter;

import java.util.List;

public class WorkbenchConfig {

    private final int elementsStart;

    private final int elementsIncrement;

    private final List<Sorter> sorter;

    private final RunInfo[] runs;

    private int currentRun = -1;

    public WorkbenchConfig(List<Sorter> sorter, int numRuns, int elementsStart, int elementsIncrement) {
        this.runs = new RunInfo[numRuns];
        this.elementsStart = elementsStart;
        this.elementsIncrement = elementsIncrement;
        this.sorter = sorter;
    }

    public RunInfo getNextRunConfig() {
        currentRun++;
        runs[currentRun] = new RunInfo(this.sorter, currentRun * elementsIncrement + elementsStart);

        return runs[currentRun];
    }

    public boolean isFinished() {
        return currentRun >= runs.length - 1;
    }

    public int getCurrentRun() {
        return currentRun + 1;
    }
}
