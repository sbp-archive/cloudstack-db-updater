package com.schubergphilis.cloudstackdb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class MissingFilesDetectorTest extends AbstractFileSystemConflictDetectorTest {

    private MissingFilesDetector detector;
    private FileLists fileLists;

    @Override
    public void setup() throws Exception {
        super.setup();

        fileLists = new FileLists();
        detector = new MissingFilesDetector(currentVersion, nextVersion, fileLists);
    }

    @Test
    public void testGetMissingFilesFilteringOutMovedFilesWhenAllFilesAreMissing() throws Exception {
        List<SourceCodeFile> filteredMissingFiles = MissingFilesDetector.getMissingFilesFilteringOutMovedFiles(fileLists.getMissingFiles(), fileLists.getMovedFiles());

        assertNotNull(filteredMissingFiles);
        assertEquals(1, filteredMissingFiles.size());
        assertEquals(new SourceCodeFile("/" + nameOfFileThatWillBeMissing), filteredMissingFiles.get(0));
    }

    @Test
    public void testGetMissingFilesFilteringOutMovedFilesWhenMoreThanOneFileIsFiltered() throws Exception {
        SourceCodeFile sourceCodeFile = new SourceCodeFile("file3");
        List<SourceCodeFile> missingFiles = Arrays.asList(new SourceCodeFile[] {new SourceCodeFile("file1"), new SourceCodeFile("file2"), sourceCodeFile});
        List<MovedSourceCodeFile> movedFiles = Arrays.asList(new MovedSourceCodeFile[] {new MovedSourceCodeFile("file1", "fileA"), new MovedSourceCodeFile("file2", "fileB")});

        List<SourceCodeFile> filteredMissingFiles = MissingFilesDetector.getMissingFilesFilteringOutMovedFiles(missingFiles, movedFiles);

        assertNotNull(filteredMissingFiles);
        assertEquals(1, filteredMissingFiles.size());
        assertEquals(sourceCodeFile, filteredMissingFiles.get(0));
    }

    @Test
    public void testDetectAppliesGetMissingFilesFilteringOutMovedFiles() throws Exception {
        List<SourceCodeFile> filteredMissingFiles = MissingFilesDetector.getMissingFilesFilteringOutMovedFiles(fileLists.getMissingFiles(), fileLists.getMovedFiles());
        List<Conflict> conflicts = detector.detect();

        assertNotNull(conflicts);
        assertEquals(1, conflicts.size());
        assertTrue(conflicts.get(0) instanceof MissingFilesConflict);
        assertEquals(filteredMissingFiles, ((MissingFilesConflict)conflicts.get(0)).getFiles());
    }

}
