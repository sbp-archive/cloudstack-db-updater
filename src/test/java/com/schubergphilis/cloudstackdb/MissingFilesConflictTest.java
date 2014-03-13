package com.schubergphilis.cloudstackdb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class MissingFilesConflictTest {

    private final List<SourceCodeFile> sortedFiles = Arrays.asList(new SourceCodeFile[] {new SourceCodeFile("a"), new SourceCodeFile("b"), new SourceCodeFile("c")});

    @Test
    public void testConstructorWhenFilesAreSorted() throws Exception {
        MissingFilesConflict missingFilesConflict = new MissingFilesConflict(sortedFiles);

        List<SourceCodeFile> expected = sortedFiles;
        List<SourceCodeFile> actual = missingFilesConflict.getFiles();

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testConstructorWhenFilesAreSortedInReverseOrder() throws Exception {
        List<SourceCodeFile> reverseSortedFiles = new ArrayList<>(sortedFiles);
        Collections.sort(reverseSortedFiles, Collections.reverseOrder());
        MissingFilesConflict missingFilesConflict = new MissingFilesConflict(sortedFiles);

        List<SourceCodeFile> expected = sortedFiles;
        List<SourceCodeFile> actual = missingFilesConflict.getFiles();

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testConstructorWhenFilesAreInRandomOrder() throws Exception {
        List<SourceCodeFile> rendomOrderFiles = Arrays.asList(new SourceCodeFile[] {new SourceCodeFile("c"), new SourceCodeFile("a"), new SourceCodeFile("d"),
                new SourceCodeFile("b")});
        MissingFilesConflict missingFilesConflict = new MissingFilesConflict(rendomOrderFiles);

        List<SourceCodeFile> expected = Arrays.asList(new SourceCodeFile[] {new SourceCodeFile("a"), new SourceCodeFile("b"), new SourceCodeFile("c"), new SourceCodeFile("d")});
        List<SourceCodeFile> actual = missingFilesConflict.getFiles();

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testConstructorWhenThereAreNoFiles() throws Exception {
        List<SourceCodeFile> noFiles = new ArrayList<>();

        MissingFilesConflict missingFilesConflict = new MissingFilesConflict(noFiles);
        List<SourceCodeFile> actual = missingFilesConflict.getFiles();

        assertNotNull(actual);
        assertEquals(0, actual.size());
    }
}
