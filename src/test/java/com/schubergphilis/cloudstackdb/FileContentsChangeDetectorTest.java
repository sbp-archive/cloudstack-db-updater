package com.schubergphilis.cloudstackdb;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.schubergphilis.utils.FileUtils;

public class FileContentsChangeDetectorTest extends AbstractFileSystemConflictDetectorTest {

    @Test
    public void testCheckForChangesInFileContentsWhenNoFileHasChanged() throws Exception {
        FileUtils.writeToFile("foo", fileCurrentVersion);
        FileUtils.writeToFile("foo", fileNewVersion);

        List<Conflict> conflicts = FileContentsChangeDetector.checkForChangesInFileContents(currentVersion, nextVersion);

        assertNotNull(conflicts);
        assertEquals(0, conflicts.size());
    }

    @Test
    public void testCheckForChangesInFileContentsWhenOneFileHasChanged() throws Exception {
        FileUtils.writeToFile("foo", fileCurrentVersion);
        FileUtils.writeToFile("bar", fileNewVersion);
        String otherFilename = "otherFile";
        File otherFileCurrentVersion = rootFolderCurrentVersion.newFile(otherFilename);
        File otherFileNewVersion = rootFolderNewVersion.newFile(otherFilename);
        FileUtils.writeToFile("foobar", otherFileCurrentVersion);
        FileUtils.writeToFile("foobar", otherFileNewVersion);
        currentVersion.addFiles(Arrays.asList(new File[] {otherFileCurrentVersion}));
        nextVersion.addFiles(Arrays.asList(new File[] {otherFileNewVersion}));

        List<Conflict> conflicts = FileContentsChangeDetector.checkForChangesInFileContents(currentVersion, nextVersion);

        assertNotNull(conflicts);
        assertEquals(1, conflicts.size());
        assertThat(conflicts.get(0).print(), containsString(filename));
    }

    @Test
    public void testDetectAppliesCheckForMissingFiles() throws Exception {
        FileContentsChangeDetector detector = new FileContentsChangeDetector(currentVersion, nextVersion);

        List<Conflict> expected = FileContentsChangeDetector.checkForChangesInFileContents(currentVersion, nextVersion);
        List<Conflict> actual = detector.detect();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetFilesThatChangedInNewVersionWhenOneFileChanged() throws Exception {
        FileUtils.writeToFile("foo", fileCurrentVersion);
        FileUtils.writeToFile("bar", fileNewVersion);

        List<ChangedSourceCodeFile> expected = Arrays.asList(new ChangedSourceCodeFile[] {new ContentsChangedSourceCodeFile("/" + filename, "/" + filename)});
        List<ChangedSourceCodeFile> actual = FileContentsChangeDetector.getFilesThatChangedInNewVersion(currentVersion, nextVersion);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetFilesThatChangedInNewVersionWhenNoFileChanged() throws Exception {
        List<ChangedSourceCodeFile> actual = FileContentsChangeDetector.getFilesThatChangedInNewVersion(currentVersion, nextVersion);

        assertNotNull(actual);
        assertEquals(0, actual.size());
    }

}
