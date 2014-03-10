package com.schubergphilis.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class FileUtilsTest {

    private File file1;
    private File file2;
    private final String filename = "file";

    @Before
    public void setup() throws Exception {
        file1 = FileUtils.createTemporaryFile(filename);
        file2 = FileUtils.createTemporaryFile(filename);
    }

    @Test
    public void tetsWriteToFile() throws Exception {
        String contents = "contents";
        FileUtils.writeToFile(contents, file1);

        String expected = contents;
        BufferedReader fileReader = new BufferedReader(new FileReader(file1));
        String actual = fileReader.readLine();

        assertEquals(expected, actual);
    }

    @Test
    public void tetsWriteToFileWhenContentsIsEmpty() throws Exception {
        String contents = "";
        FileUtils.writeToFile(contents, file1);

        BufferedReader fileReader = new BufferedReader(new FileReader(file1));
        String actual = fileReader.readLine();

        assertNull(actual);
    }

    @Test
    public void testReadLinesWhenFileIsEmpty() throws Exception {
        List<String> lines = FileUtils.readLines(file1);

        assertNotNull(lines);
        assertEquals(0, lines.size());
    }

    @Test
    public void testReadLinesWhenFileHasTrailingNewLine() throws Exception {
        FileUtils.writeToFile("foo foo foo\nbar bar bar\n\n", file1);

        List<String> lines = FileUtils.readLines(file1);

        assertNotNull(lines);
        assertEquals(3, lines.size());
    }

    @Test
    public void testReadLinesWithoutTraillingWhiteSpaceWhenFileHasTrailingNewLine() throws Exception {
        FileUtils.writeToFile("foo foo foo\nbar bar bar\n\n", file1);

        List<String> lines = FileUtils.readLines(file1, true);

        assertNotNull(lines);
        assertEquals(2, lines.size());
    }

    @Test
    public void testReadLinesWithoutTraillingWhiteSpaceWhenFileHasLinesWithTraillingWhiteSpace() throws Exception {
        FileUtils.writeToFile("foo foo foo      \nbar bar bar", file1);

        List<String> lines = FileUtils.readLines(file1, true);

        assertNotNull(lines);
        assertEquals(2, lines.size());
        assertEquals("foo foo foo", lines.get(0));
    }

    @Test
    public void testFilesHaveSameContentsNotConsideringTraillingWhiteSpaceWhenOneLineHasChanged() throws Exception {
        FileUtils.writeToFile("foo", file1);
        FileUtils.writeToFile("bar", file2);

        assertFalse(FileUtils.filesHaveSameContentsNotConsideringTraillingWhiteSpace(file1, file2));
    }

    @Test
    public void testFilesHaveSameContentsNotConsideringTraillingWhiteSpaceWhenOneLineWasRemoved() throws Exception {
        FileUtils.writeToFile("foo", file1);

        assertFalse(FileUtils.filesHaveSameContentsNotConsideringTraillingWhiteSpace(file1, file2));
    }

    @Test
    public void testFilesHaveSameContentsNotConsideringTraillingWhiteSpaceWhenFormattingChanged() throws Exception {
        FileUtils.writeToFile("foo", file1);
        FileUtils.writeToFile("\tfoo  \t\n\n\n", file2);

        assertTrue(FileUtils.filesHaveSameContentsNotConsideringTraillingWhiteSpace(file1, file2));
    }

    @Test
    public void testCreateTemporaryFile() throws Exception {
        File temporaryFile = FileUtils.createTemporaryFile();

        assertTrue(temporaryFile.exists());
        assertTrue(temporaryFile.isFile());
    }

}
