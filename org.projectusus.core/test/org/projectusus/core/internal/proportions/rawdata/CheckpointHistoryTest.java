package org.projectusus.core.internal.proportions.rawdata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.CodeProportionKind;
import org.projectusus.core.internal.proportions.modelupdate.checkpoints.CheckpointHistory;

public class CheckpointHistoryTest {

    private CheckpointHistory checkpointHistory;
    private CodeProportion testProportion;
    private List<CodeProportion> entries;

    @Before
    public void setup() {
        checkpointHistory = new CheckpointHistory();
        testProportion = new CodeProportion( CodeProportionKind.TA );
        entries = new ArrayList<CodeProportion>();
    }

    @Test
    public void initiallyEmpty() {
        assertTrue( checkpointHistory.getCheckpoints().isEmpty() );
    }

    @Test
    public void emptyAfterComputationRun() {
        checkpointHistory.addComputationResult( entries );

        assertTrue( checkpointHistory.getCheckpoints().isEmpty() );
    }

    @Test
    public void emptyAfterTwoComputationRuns() {
        checkpointHistory.addComputationResult( entries );
        checkpointHistory.addComputationResult( entries );

        assertTrue( checkpointHistory.getCheckpoints().isEmpty() );
    }

    @Test
    public void emptyAfterInitialTestRun() {
        checkpointHistory.addTestResult( testProportion );

        assertTrue( checkpointHistory.getCheckpoints().isEmpty() );
    }

    @Test
    public void emptyAfterTwoInitialTestRuns() {
        checkpointHistory.addTestResult( testProportion );

        assertTrue( checkpointHistory.getCheckpoints().isEmpty() );
    }

    @Test
    public void oneEntryAfterComputationAndTest() {
        checkpointHistory.addComputationResult( entries );
        checkpointHistory.addTestResult( testProportion );

        assertEquals( 1, checkpointHistory.getCheckpoints().size() );
    }

    @Test
    public void oneEntryAfterComputationAndTwoTests() {
        checkpointHistory.addComputationResult( entries );
        checkpointHistory.addTestResult( testProportion );
        checkpointHistory.addTestResult( testProportion );

        assertEquals( 1, checkpointHistory.getCheckpoints().size() );
    }

    @Test
    public void oneEntryAfterComputationTestComputation() {
        checkpointHistory.addComputationResult( entries );
        checkpointHistory.addTestResult( testProportion );
        checkpointHistory.addComputationResult( entries );

        assertEquals( 1, checkpointHistory.getCheckpoints().size() );
    }

    @Test
    public void twoEntriesAfterComputationTestComputationTest() {
        checkpointHistory.addComputationResult( entries );
        checkpointHistory.addTestResult( testProportion );
        checkpointHistory.addComputationResult( entries );
        checkpointHistory.addTestResult( testProportion );

        assertEquals( 2, checkpointHistory.getCheckpoints().size() );
    }
}
