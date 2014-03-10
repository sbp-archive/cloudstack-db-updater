package com.schubergphilis.cloudstackdb;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.schubergphilis.utils.FileUtils;

public class FileSystemConflictDetectorTest {

    @Rule
    public TemporaryFolder rootFolderCurrentVersion = new TemporaryFolder();
    @Rule
    public TemporaryFolder rootFolderNewVersion = new TemporaryFolder();

    private SourceCodeVersion currentVersion;
    private SourceCodeVersion newVersion;

    private final String filename = "file";
    private File fileCurrentVersion;
    private File fileNewVersion;

    @Before
    public void setup() throws Exception {
        currentVersion = new SourceCodeVersion(rootFolderCurrentVersion.getRoot());
        newVersion = new SourceCodeVersion(rootFolderNewVersion.getRoot());

        fileCurrentVersion = rootFolderCurrentVersion.newFile(filename);
        currentVersion.addFiles(Arrays.asList(new File[] {fileCurrentVersion}));

        fileNewVersion = rootFolderNewVersion.newFile(filename);
        newVersion.addFiles(Arrays.asList(new File[] {fileNewVersion}));
    }

    @Test
    public void testCheckForMissingFilesWhenNoFileIsMissing() throws Exception {
        Set<String> filesInPreviousVersion = new HashSet<>(Arrays.asList(new String[] {"a", "b", "c"}));
        Set<String> filesInCurrentVersion = new HashSet<>(Arrays.asList(new String[] {"a", "b", "c"}));

        List<Conflict> conflicts = FileSystemConflictDetector.checkForMissingFiles(filesInPreviousVersion, filesInCurrentVersion);

        assertNotNull(conflicts);
        assertEquals(0, conflicts.size());
    }

    @Test
    public void testCheckForMissingFilesWhenOneFileIsMissing() throws Exception {
        Set<String> filesInPreviousVersion = new HashSet<>(Arrays.asList(new String[] {"a", "b", "c"}));
        Set<String> filesInCurrentVersion = new HashSet<>(Arrays.asList(new String[] {"a", "b"}));

        List<Conflict> conflicts = FileSystemConflictDetector.checkForMissingFiles(filesInPreviousVersion, filesInCurrentVersion);

        assertNotNull(conflicts);
        assertEquals(1, conflicts.size());
        assertThat(conflicts.get(0).print(), containsString("- c"));
    }

    @Test
    public void testCheckForMissingFilesWhenallFilesAreMissing() throws Exception {
        Set<String> filesInPreviousVersion = new HashSet<>(Arrays.asList(new String[] {"a", "b", "c"}));
        Set<String> filesInCurrentVersion = new HashSet<>(Arrays.asList(new String[] {}));

        List<Conflict> conflicts = FileSystemConflictDetector.checkForMissingFiles(filesInPreviousVersion, filesInCurrentVersion);

        assertNotNull(conflicts);
        assertEquals(1, conflicts.size());
        assertThat(conflicts.get(0).print(), allOf(containsString("- a"), containsString("- b"), containsString("- a")));
    }

    @Test
    public void testCheckForChangesInFileContentsWhenNoFileHasChanged() throws Exception {
        FileUtils.writeToFile("foo", fileCurrentVersion);
        FileUtils.writeToFile("foo", fileNewVersion);

        List<Conflict> conflicts = FileSystemConflictDetector.checkForChangesInFileContents(currentVersion, newVersion);

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
        newVersion.addFiles(Arrays.asList(new File[] {otherFileNewVersion}));

        List<Conflict> conflicts = FileSystemConflictDetector.checkForChangesInFileContents(currentVersion, newVersion);

        assertNotNull(conflicts);
        assertEquals(1, conflicts.size());
        assertThat(conflicts.get(0).print(), containsString(filename));
    }

    @Test
    public void testDetectWhenThereAreNoConflicts() throws Exception {
        FileUtils.writeToFile("foo", fileCurrentVersion);
        FileUtils.writeToFile("foo", fileNewVersion);
        FileSystemConflictDetector fileSystemConflictDetector = new FileSystemConflictDetector(currentVersion, newVersion);

        List<Conflict> conflicts = fileSystemConflictDetector.detect();

        assertNotNull(conflicts);
        assertEquals(0, conflicts.size());
    }

    @Test
    public void testDetectWhenOneFileWasRemoved() throws Exception {
        newVersion.getFiles().remove("/" + filename);
        FileSystemConflictDetector fileSystemConflictDetector = new FileSystemConflictDetector(currentVersion, newVersion);

        List<Conflict> conflicts = fileSystemConflictDetector.detect();

        assertNotNull(conflicts);
        assertEquals(1, conflicts.size());
        assertThat(conflicts.get(0), instanceOf(MissingFilesConflict.class));
    }

    @Test
    public void testDetectWhenOneFileChanged() throws Exception {
        FileUtils.writeToFile("foo", fileCurrentVersion);
        FileUtils.writeToFile("bar", fileNewVersion);
        FileSystemConflictDetector fileSystemConflictDetector = new FileSystemConflictDetector(currentVersion, newVersion);

        List<Conflict> conflicts = fileSystemConflictDetector.detect();

        assertNotNull(conflicts);
        assertEquals(1, conflicts.size());
        assertThat(conflicts.get(0), instanceOf(FileContentHasChangedConflict.class));
    }

    @Test
    public void testDetectWhenOneFileChangedAndAnotherIsMissing() throws Exception {
        String otherFilename = "otherFile";
        File otherFileCurrentVersion = rootFolderCurrentVersion.newFile(otherFilename);
        currentVersion.addFiles(Arrays.asList(new File[] {otherFileCurrentVersion}));
        FileUtils.writeToFile("foo", fileCurrentVersion);
        FileUtils.writeToFile("bar", fileNewVersion);
        FileSystemConflictDetector fileSystemConflictDetector = new FileSystemConflictDetector(currentVersion, newVersion);

        List<Conflict> conflicts = fileSystemConflictDetector.detect();

        assertNotNull(conflicts);
        assertEquals(2, conflicts.size());
        assertThat(conflicts.get(0), anyOf(instanceOf(MissingFilesConflict.class), instanceOf(FileContentHasChangedConflict.class)));
        assertThat(conflicts.get(1), anyOf(instanceOf(MissingFilesConflict.class), instanceOf(FileContentHasChangedConflict.class)));
    }
}
