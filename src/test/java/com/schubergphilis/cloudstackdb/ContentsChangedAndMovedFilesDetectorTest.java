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

public class ContentsChangedAndMovedFilesDetectorTest extends AbstractFileSystemConflictDetectorTest {

    private ContentsChangedAndMovedFilesDetector detector;

    @Test
    public void testDetectWhenNoFileWasMoved() throws Exception {
        detector = new ContentsChangedAndMovedFilesDetector(currentVersion, nextVersion);

        List<Conflict> conflicts = detector.detect();

        assertNotNull(conflicts);
        assertEquals(0, conflicts.size());

    }

    @Test
    public void testDetectWhenOneFileWasMoved() throws Exception {
        String nameOfFileThatWillBeMovedAndContentsWillChange = "nameOfFileThatWillBeMovedAndContentsWillChange";
        File fileThatWillBeMovedAndContentsWillChange = rootFolderCurrentVersion.newFile(nameOfFileThatWillBeMovedAndContentsWillChange);
        File fileThatHasBeenMovedAndContentsHaveChanged = rootFolderNewVersion.newFile(newDir + "/" + nameOfFileThatWillBeMovedAndContentsWillChange);
        FileUtils.writeToFile("foo", fileThatWillBeMovedAndContentsWillChange);
        FileUtils.writeToFile("bar", fileThatHasBeenMovedAndContentsHaveChanged);
        currentVersion.addFiles(Arrays.asList(new File[] {fileThatWillBeMovedAndContentsWillChange}));
        nextVersion.addFiles(Arrays.asList(new File[] {fileThatHasBeenMovedAndContentsHaveChanged}));
        detector = new ContentsChangedAndMovedFilesDetector(currentVersion, nextVersion);

        List<Conflict> conflicts = detector.detect();

        assertNotNull(conflicts);
        assertEquals(1, conflicts.size());
        assertThat(conflicts.get(0).print(), containsString(nameOfFileThatWillBeMovedAndContentsWillChange));
    }

}
