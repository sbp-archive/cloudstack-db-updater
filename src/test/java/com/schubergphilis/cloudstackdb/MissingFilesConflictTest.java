package com.schubergphilis.cloudstackdb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class MissingFilesConflictTest {

    @Test
    public void testConstructorWhenFilesAreSorted() throws Exception {
        List<String> missingFiles = Arrays.asList(new String[] {"a", "b", "c"});

        MissingFilesConflict missingFilesConflict = new MissingFilesConflict(missingFiles);
        List<String> expected = missingFiles;
        List<String> actual = missingFilesConflict.getMissingFiles();

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testConstructorWhenFilesAreSortedInReverseOrder() throws Exception {
        List<String> missingFiles = Arrays.asList(new String[] {"c", "b", "a"});

        MissingFilesConflict missingFilesConflict = new MissingFilesConflict(missingFiles);
        List<String> expected = Arrays.asList(new String[] {"a", "b", "c"});
        List<String> actual = missingFilesConflict.getMissingFiles();

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testConstructorWhenFilesAreInRandomOrder() throws Exception {
        List<String> missingFiles = Arrays.asList(new String[] {"c", "a", "d", "b"});

        MissingFilesConflict missingFilesConflict = new MissingFilesConflict(missingFiles);
        List<String> expected = Arrays.asList(new String[] {"a", "b", "c", "d"});
        List<String> actual = missingFilesConflict.getMissingFiles();

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testConstructorWhenThereAreNoFiles() throws Exception {
        List<String> missingFiles = new ArrayList<>();

        MissingFilesConflict missingFilesConflict = new MissingFilesConflict(missingFiles);
        List<String> actual = missingFilesConflict.getMissingFiles();

        assertNotNull(actual);
        assertEquals(0, actual.size());
    }
}
