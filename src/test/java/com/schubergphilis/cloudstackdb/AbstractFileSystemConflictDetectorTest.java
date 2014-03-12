package com.schubergphilis.cloudstackdb;

import java.io.File;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

public abstract class AbstractFileSystemConflictDetectorTest {

    @Rule
    public TemporaryFolder rootFolderCurrentVersion = new TemporaryFolder();
    @Rule
    public TemporaryFolder rootFolderNewVersion = new TemporaryFolder();

    protected SourceCodeVersion currentVersion;
    protected SourceCodeVersion nextVersion;

    protected final String filename = "file";
    protected final String nameOfFileThatWillBeMissing = "fileThatWillBeMissing";
    protected final String nameOfFileThatWillBeMoved = "fileThatWillBeMoved";
    protected File fileCurrentVersion;
    protected File fileNewVersion;

    @Before
    public void setup() throws Exception {
        fileCurrentVersion = rootFolderCurrentVersion.newFile(filename);
        fileNewVersion = rootFolderNewVersion.newFile(filename);
        File fileThatWillBeMissing = rootFolderCurrentVersion.newFile(nameOfFileThatWillBeMissing);
        File fileThatWillBeMoved = rootFolderCurrentVersion.newFile(nameOfFileThatWillBeMoved);
        String newDir = "newDir";
        rootFolderNewVersion.newFolder(newDir);
        File fileThatHasBeenMoved = rootFolderNewVersion.newFile(newDir + "/" + nameOfFileThatWillBeMoved);

        currentVersion = new SourceCodeVersion(rootFolderCurrentVersion.getRoot());
        nextVersion = new SourceCodeVersion(rootFolderNewVersion.getRoot());

        currentVersion.addFiles(Arrays.asList(new File[] {fileCurrentVersion, fileThatWillBeMissing, fileThatWillBeMoved}));
        nextVersion.addFiles(Arrays.asList(new File[] {fileNewVersion, fileThatHasBeenMoved}));
    }

}
