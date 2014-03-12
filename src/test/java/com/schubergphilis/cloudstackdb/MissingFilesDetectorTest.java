package com.schubergphilis.cloudstackdb;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class MissingFilesDetectorTest extends AbstractFileSystemConflictDetectorTest {

    @Test
    public void testCheckForMissingFilesWhenNoFileIsMissing() throws Exception {
        Set<String> filesInPreviousVersion = new HashSet<>(Arrays.asList(new String[] {"a", "b", "c"}));
        Set<String> filesInCurrentVersion = new HashSet<>(Arrays.asList(new String[] {"a", "b", "c"}));

        List<Conflict> conflicts = MissingFilesDetector.checkForMissingFiles(filesInPreviousVersion, filesInCurrentVersion);

        assertNotNull(conflicts);
        assertEquals(0, conflicts.size());
    }

    @Test
    public void testCheckForMissingFilesWhenSomeFileIsMissing() throws Exception {
        Set<String> filesInPreviousVersion = new HashSet<>(Arrays.asList(new String[] {"a", "b", "c"}));
        Set<String> filesInCurrentVersion = new HashSet<>(Arrays.asList(new String[] {"c"}));

        List<Conflict> conflicts = MissingFilesDetector.checkForMissingFiles(filesInPreviousVersion, filesInCurrentVersion);

        assertNotNull(conflicts);
        assertEquals(1, conflicts.size());
        assertThat(conflicts.get(0).print(), allOf(containsString("- a"), containsString("- b")));
    }

    @Test
    public void testCheckForMissingFilesWhenAllFilesAreMissing() throws Exception {
        Set<String> filesInPreviousVersion = new HashSet<>(Arrays.asList(new String[] {"a", "b", "c"}));
        Set<String> filesInCurrentVersion = new HashSet<>(Arrays.asList(new String[] {}));

        List<Conflict> conflicts = MissingFilesDetector.checkForMissingFiles(filesInPreviousVersion, filesInCurrentVersion);

        assertNotNull(conflicts);
        assertEquals(1, conflicts.size());
        assertThat(conflicts.get(0).print(), allOf(containsString("- a"), containsString("- b"), containsString("- a")));
    }

    @Test
    public void testDetectAppliesCheckForMissingFiles() throws Exception {
        MissingFilesDetector detector = new MissingFilesDetector(currentVersion, nextVersion);

        List<Conflict> expected = MissingFilesDetector.checkForMissingFiles(currentVersion.getFilenames(), nextVersion.getFilenames());
        List<Conflict> actual = detector.detect();

        assertEquals(expected, actual);
    }
}
