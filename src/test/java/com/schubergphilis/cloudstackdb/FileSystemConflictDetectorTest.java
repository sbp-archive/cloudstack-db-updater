package com.schubergphilis.cloudstackdb;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;

import org.junit.Before;
import org.junit.Test;

public class FileSystemConflictDetectorTest {

    private File fileV1;
    private File fileV2;

    @Before
    public void setup() throws Exception {
        fileV1 = File.createTempFile("fileV1", Long.toString(System.currentTimeMillis()));
        fileV2 = File.createTempFile("fileV2", Long.toString(System.currentTimeMillis()));
    }

    @Test
    public void testContentHasChangedWhenOneLineHasChanged() throws Exception {
        FileWriter fileV1Writer = new FileWriter(fileV1);
        fileV1Writer.write("foo");
        fileV1Writer.close();

        FileWriter fileV2Writer = new FileWriter(fileV2);
        fileV2Writer.write("boo");
        fileV2Writer.close();

        assertTrue(FileSystemConflictDetector.contentHasChanged(fileV1, fileV2));
    }

    @Test
    public void testContentHasChangedWhenOneLineWasRemoved() throws Exception {
        FileWriter fileV1Writer = new FileWriter(fileV1);
        fileV1Writer.write("foo");
        fileV1Writer.close();

        assertTrue(FileSystemConflictDetector.contentHasChanged(fileV1, fileV2));
    }

    @Test
    public void testContentHasChangedWhenFormattingChanged() throws Exception {
        FileWriter fileV1Writer = new FileWriter(fileV1);
        fileV1Writer.write("foo");
        fileV1Writer.close();

        FileWriter fileV2Writer = new FileWriter(fileV2);
        fileV2Writer.write("\tfoo  \t\n\n\n");
        fileV2Writer.close();

        assertFalse(FileSystemConflictDetector.contentHasChanged(fileV1, fileV2));
    }

}
