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
    protected File fileCurrentVersion;
    protected File fileNewVersion;

    @Before
    public void setup() throws Exception {
        currentVersion = new SourceCodeVersion(rootFolderCurrentVersion.getRoot());
        nextVersion = new SourceCodeVersion(rootFolderNewVersion.getRoot());

        fileCurrentVersion = rootFolderCurrentVersion.newFile(filename);
        currentVersion.addFiles(Arrays.asList(new File[] {fileCurrentVersion}));

        fileNewVersion = rootFolderNewVersion.newFile(filename);
        nextVersion.addFiles(Arrays.asList(new File[] {fileNewVersion}));
    }
}
