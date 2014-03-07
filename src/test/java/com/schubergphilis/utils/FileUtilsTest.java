package com.schubergphilis.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class FileUtilsTest {

    private File tempFile;

    @Before
    public void setup() throws Exception {
        tempFile = File.createTempFile("tempFile", Long.toString(System.currentTimeMillis()));
    }

    @Test
    public void testReadLinesWhenFileIsEmpty() throws Exception {
        List<String> lines = FileUtils.readLines(tempFile);

        assertNotNull(lines);
        assertEquals(0, lines.size());
    }

    @Test
    public void testReadLinesWhenFileHasTrailingNewLine() throws Exception {
        FileWriter fileWriter = new FileWriter(tempFile);
        fileWriter.write("foo foo foo\nbar bar bar\n\n");
        fileWriter.close();

        List<String> lines = FileUtils.readLines(tempFile);

        assertNotNull(lines);
        assertEquals(3, lines.size());
    }

    @Test
    public void testReadLinesWithoutTraillingWhiteSpaceWhenFileHasTrailingNewLine() throws Exception {
        FileWriter fileWriter = new FileWriter(tempFile);
        fileWriter.write("foo foo foo\nbar bar bar\n\n");
        fileWriter.close();

        List<String> lines = FileUtils.readLines(tempFile, true);

        assertNotNull(lines);
        assertEquals(2, lines.size());
    }

    @Test
    public void testReadLinesWithoutTraillingWhiteSpaceWhenFileHasLinesWithTraillingWhiteSpace() throws Exception {
        FileWriter fileWriter = new FileWriter(tempFile);
        fileWriter.write("foo foo foo      \nbar bar bar");
        fileWriter.close();

        List<String> lines = FileUtils.readLines(tempFile, true);

        assertNotNull(lines);
        assertEquals(2, lines.size());
        assertEquals("foo foo foo", lines.get(0));
    }

}
