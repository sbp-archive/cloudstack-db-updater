package com.schubergphilis.cloudstackdb;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Test;

public class FilePathConflictDetectorTest extends AbstractFileSystemConflictDetectorTest {

    @Test
    public void testGetMissingFilesWhenNoFileIsMissing() throws Exception {
        Set<String> filesInPreviousVersion = new HashSet<>(Arrays.asList(new String[] {"a", "b", "c"}));
        Set<String> filesInCurrentVersion = new HashSet<>(Arrays.asList(new String[] {"a", "b", "c"}));

        List<String> missingFiles = FilePathConflictDetector.getMissingFiles(filesInPreviousVersion, filesInCurrentVersion);

        assertNotNull(missingFiles);
        assertEquals(0, missingFiles.size());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetMissingFilesWhenSomeFileIsMissing() throws Exception {
        Set<String> filesInPreviousVersion = new HashSet<>(Arrays.asList(new String[] {"a", "b", "c"}));
        Set<String> filesInCurrentVersion = new HashSet<>(Arrays.asList(new String[] {"c"}));

        List<String> missingFiles = FilePathConflictDetector.getMissingFiles(filesInPreviousVersion, filesInCurrentVersion);

        assertNotNull(missingFiles);
        assertEquals(2, missingFiles.size());
        assertThat(missingFiles, IsIterableContainingInAnyOrder.<String> containsInAnyOrder(is("a"), is("b")));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetMissingFilesWhenAllFilesAreMissing() throws Exception {
        Set<String> filesInPreviousVersion = new HashSet<>(Arrays.asList(new String[] {"a", "b", "c"}));
        Set<String> filesInCurrentVersion = new HashSet<>(Arrays.asList(new String[] {}));

        List<String> missingFiles = FilePathConflictDetector.getMissingFiles(filesInPreviousVersion, filesInCurrentVersion);

        assertNotNull(missingFiles);
        assertEquals(3, missingFiles.size());
        assertThat(missingFiles, IsIterableContainingInAnyOrder.<String> containsInAnyOrder(is("a"), is("b"), is("c")));
    }

    @Test
    public void testGetMovedFilesWhenNoFilesAreMissing() throws Exception {
        List<String> missingFiles = new ArrayList<>();
        Collection<SourceCodeFile> filesInNextVersion = Arrays.asList(new SourceCodeFile[] {new SourceCodeFile("z/c")});

        List<String> expected = new ArrayList<>();
        Map<String, String> actual = FilePathConflictDetector.getMovedFiles(missingFiles, filesInNextVersion);

        assertEquals(expected, new ArrayList<>(actual.keySet()));
    }

    @Test
    public void testGetMovedFilesWhenNextVersionHasNoFiles() throws Exception {
        List<String> missingFiles = Arrays.asList(new String[] {"a/b/c", "d/e/f"});
        Collection<SourceCodeFile> filesInNextVersion = new ArrayList<>();

        List<String> expected = new ArrayList<>();
        Map<String, String> actual = FilePathConflictDetector.getMovedFiles(missingFiles, filesInNextVersion);

        assertEquals(expected, new ArrayList<>(actual.keySet()));
    }

    @Test
    public void testGetMovedFilesWhenOneFileMoved() throws Exception {
        List<String> missingFiles = Arrays.asList(new String[] {"a/b/c", "d/e/f"});
        Collection<SourceCodeFile> filesInNextVersion = Arrays.asList(new SourceCodeFile[] {new SourceCodeFile("z/c")});

        List<String> expected = Arrays.asList(new String[] {"a/b/c"});
        Map<String, String> actual = FilePathConflictDetector.getMovedFiles(missingFiles, filesInNextVersion);

        assertEquals(expected, new ArrayList<>(actual.keySet()));
    }

}
