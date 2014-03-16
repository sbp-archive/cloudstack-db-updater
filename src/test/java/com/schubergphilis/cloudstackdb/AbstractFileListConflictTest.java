package com.schubergphilis.cloudstackdb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import com.schubergphilis.utils.FileUtils;

public class AbstractFileListConflictTest extends AbstractFileSystemConflictDetectorTest {

    @Test
    public void testGetPatch() throws Exception {
        FileUtils.writeToFile("foo\nbar\n", fileCurrentVersion);
        FileUtils.writeToFile("foo\nfoo foo\nbar\nbar bar", fileNextVersion);

        List<String> patch = AbstractFileListConflict.getPatch(fileCurrentVersion, fileNextVersion);

        assertNotNull(patch);
        assertEquals(2, patch.size());
    }

}
