package com.schubergphilis.cloudstackdb;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

public class MovedFilesDetectorTest extends AbstractFileSystemConflictDetectorTest {

    private MovedFilesDetector detector;

    @Override
    public void setup() throws Exception {
        super.setup();

        detector = new MovedFilesDetector(currentVersion, nextVersion);
    }

    @Test
    public void testDetectWhenOneFileWasMoved() throws Exception {
        List<Conflict> conflicts = detector.detect();

        assertNotNull(conflicts);
        assertEquals(1, conflicts.size());
        assertThat(conflicts.get(0).print(), containsString(nameOfFileThatWillBeMoved));
    }

}
