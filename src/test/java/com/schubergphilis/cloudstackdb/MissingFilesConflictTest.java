package com.schubergphilis.cloudstackdb;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class MissingFilesConflictTest {

    @Test
    public void testConstructorWhenSetsAreEmpty() throws Exception {
        Set<String> filesInPreviousVersion = new HashSet<>();
        Set<String> filesInCurrentVersion = new HashSet<>();

        MissingFilesConflict missingFilesConflict = new MissingFilesConflict(filesInPreviousVersion, filesInCurrentVersion);

        List<String> missingFiles = missingFilesConflict.getMissingFiles();
        assertNotNull(missingFiles);
        assertEquals(0, missingFiles.size());
    }

    @Test
    public void testConstructorWhenNofileIsMissing() throws Exception {
        Set<String> filesInPreviousVersion = new HashSet<>(Arrays.asList(new String[] {"a", "b", "c"}));
        Set<String> filesInCurrentVersion = new HashSet<>(Arrays.asList(new String[] {"a", "b", "c"}));

        MissingFilesConflict missingFilesConflict = new MissingFilesConflict(filesInPreviousVersion, filesInCurrentVersion);

        List<String> missingFiles = missingFilesConflict.getMissingFiles();
        assertNotNull(missingFiles);
        assertEquals(0, missingFiles.size());
    }

    @Test
    public void testConstructorWhenSomefileAreMissing() throws Exception {
        Set<String> filesInPreviousVersion = new HashSet<>(Arrays.asList(new String[] {"a", "b", "c"}));
        Set<String> filesInCurrentVersion = new HashSet<>(Arrays.asList(new String[] {"c"}));

        MissingFilesConflict missingFilesConflict = new MissingFilesConflict(filesInPreviousVersion, filesInCurrentVersion);

        List<String> missingFiles = missingFilesConflict.getMissingFiles();
        assertNotNull(missingFiles);
        assertEquals(2, missingFiles.size());
        assertThat(missingFiles, hasItems(new String[] {"a", "b"}));
    }
}
