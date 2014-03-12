package com.schubergphilis.cloudstackdb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class MissingFilesDetectorTest extends AbstractFileSystemConflictDetectorTest {

    private MissingFilesDetector detector;

    @Override
    public void setup() throws Exception {
        super.setup();

        detector = new MissingFilesDetector(currentVersion, nextVersion);
    }

    @Test
    public void testGetMissingFilesFilteringOutMovedFilesWhenAllFilesAreMissing() throws Exception {
        List<String> missingFiles = detector.getMissingFilesFilteringOutMovedFiles();

        assertNotNull(missingFiles);
        assertEquals(1, missingFiles.size());
        assertEquals(missingFiles.get(0), "/" + nameOfFileThatWillBeMissing);
    }

    @Test
    public void testDetectAppliesGetMissingFilesFilteringOutMovedFiles() throws Exception {
        List<String> missingFiles = detector.getMissingFilesFilteringOutMovedFiles();
        List<Conflict> conflicts = detector.detect();

        assertNotNull(conflicts);
        assertEquals(1, conflicts.size());
        assertTrue(conflicts.get(0) instanceof MissingFilesConflict);
        assertEquals(((MissingFilesConflict)conflicts.get(0)).getFiles(), missingFiles);
    }
}
