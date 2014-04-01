/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.schubergphilis.utils;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class FileUtilsTest {

    @Rule
    public TemporaryFolder rootFolder = new TemporaryFolder();

    @Rule
    public TemporaryFolder dstFolder = new TemporaryFolder();

    private File file1;
    private File file2;
    private final String filename = "file";

    @Before
    public void setup() throws Exception {
        file1 = FileUtils.createTemporaryFile(filename);
        file2 = FileUtils.createTemporaryFile(filename);
    }

    @Test
    public void tetsWriteToFileMultipleLines() throws Exception {
        List<String> lines = Arrays.asList(new String[] {"contents", "more contents"});
        FileUtils.writeToFile(lines, file1);

        List<String> expected = lines;
        List<String> actual = FileUtils.readLines(file1);

        assertEquals(expected, actual);
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

    @Test
    public void testGatherFilesThatMatchCriteriaWhenBaseDirIsEmpty() throws Exception {
        Set<File> gatheredFiles = FileUtils.gatherFilesThatMatchCriteria(rootFolder.getRoot(), containsString("somename"));

        assertNotNull(gatheredFiles);
        assertEquals(0, gatheredFiles.size());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGatherFilesThatMatchCriteriaWhenBaseDirOnlyHasFiles() throws Exception {
        String someName = "somename";
        String otherName = "othername";
        String yetAnotherName = "yetanothername";
        rootFolder.newFile(someName);
        rootFolder.newFile(otherName);
        rootFolder.newFile(yetAnotherName);

        Set<File> gatheredFiles = FileUtils.gatherFilesThatMatchCriteria(rootFolder.getRoot(), anyOf(containsString(someName), containsString("yet")));

        assertNotNull(gatheredFiles);
        assertEquals(2, gatheredFiles.size());
        assertThat(gatheredFiles, IsIterableContainingInAnyOrder.<File> containsInAnyOrder(hasProperty("name", is(someName)), hasProperty("name", is(yetAnotherName))));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGatherFilesThatMatchCriteriaWhenBaseDirHasFilesAndDirs() throws Exception {
        String someName = "somename";
        String otherName = "othername";
        String yetAnotherName = "yetanothername";
        File folderA = rootFolder.newFolder("a");
        File folderB = rootFolder.newFolder("a/b");
        File folderC = rootFolder.newFolder("c");

        new File(folderA, someName).createNewFile();
        new File(folderB, otherName).createNewFile();
        new File(folderC, yetAnotherName).createNewFile();

        Set<File> gatheredFiles = FileUtils.gatherFilesThatMatchCriteria(rootFolder.getRoot(), anyOf(containsString(someName), containsString("yet")));

        assertNotNull(gatheredFiles);
        assertEquals(2, gatheredFiles.size());
        assertThat(gatheredFiles, IsIterableContainingInAnyOrder.<File> containsInAnyOrder(hasProperty("name", is(someName)), hasProperty("name", is(yetAnotherName))));
    }

    @Test
    public void testCopyDirectory() throws Exception {
        String someName = "somename";
        String otherName = "othername";
        String yetAnotherName = "yetanothername";
        rootFolder.newFile(someName);
        rootFolder.newFile(otherName);
        String folder = "dir";
        rootFolder.newFolder(folder);
        rootFolder.newFile(folder + "/" + yetAnotherName);

        FileUtils.copyDirectory(rootFolder.getRoot(), dstFolder.getRoot());

        assertEquals(3, dstFolder.getRoot().list().length);
    }

    @Test
    public void testGetPatch() throws Exception {
        FileUtils.writeToFile("foo", file1);
        FileUtils.writeToFile("bar", file2);

        List<String> patch = FileUtils.getPatch(file1, file2);

        assertNotNull(patch);
        assertEquals(1, patch.size());
    }

    @Test
    public void testGetPatchWhenOriginalIsEmpty() throws Exception {
        FileUtils.writeToFile("bar", file2);

        List<String> patch = FileUtils.getPatch(file1, file2);

        assertNotNull(patch);
        assertEquals(1, patch.size());
    }

    @Test(expected = NullPointerException.class)
    public void testGetPatchWhenOriginalIsNull() throws Exception {
        FileUtils.writeToFile("bar", file2);

        List<String> patch = FileUtils.getPatch(null, file2);

        assertNotNull(patch);
        assertEquals(1, patch.size());
    }

    @Test
    public void testGetPatchWhenRevisedIsEmpty() throws Exception {
        FileUtils.writeToFile("foo", file1);

        List<String> patch = FileUtils.getPatch(file1, file2);

        assertNotNull(patch);
        assertEquals(1, patch.size());
    }

    @Test(expected = NullPointerException.class)
    public void testGetPatchWhenRevisedIsNull() throws Exception {
        FileUtils.writeToFile("foo", file1);

        List<String> patch = FileUtils.getPatch(file1, null);

        assertNotNull(patch);
        assertEquals(1, patch.size());
    }

    @Test
    public void testGetPatchWhenLinesAreAdded() throws Exception {
        FileUtils.writeToFile("foo", file1);
        FileUtils.writeToFile("foo\nbar", file2);

        List<String> patch = FileUtils.getPatch(file1, file2);

        assertNotNull(patch);
        assertEquals(1, patch.size());
        assertThat(patch.get(0), allOf(containsString("Insert"), containsString("bar")));
    }

    @Test
    public void testGetPatchWhenLinesAreRemoved() throws Exception {
        FileUtils.writeToFile("foo\nbar", file1);
        FileUtils.writeToFile("bar", file2);

        List<String> patch = FileUtils.getPatch(file1, file2);

        assertNotNull(patch);
        assertEquals(1, patch.size());
        assertThat(patch.get(0), allOf(containsString("Delete"), containsString("foo")));
    }

    @Test
    public void testReplaceFileSeparatorWhenPathIsEmpty() throws Exception {
        String expected = "";
        String actual = FileUtils.replaceFileSeparator("", "/", "_");

        assertEquals(expected, actual);
    }

    @Test
    public void testReplaceFileSeparatorWhenPathHasNoSeparator() throws Exception {
        String path = "path";

        String expected = path;
        String actual = FileUtils.replaceFileSeparator(path, "/", "_");

        assertEquals(expected, actual);
    }

    @Test
    public void testReplaceFileSeparatorWhenPathHasSeparator() throws Exception {
        String path = "path/path/path";

        String expected = "path_path_path";
        String actual = FileUtils.replaceFileSeparator(path, "/", "_");

        assertEquals(expected, actual);
    }

    @Test
    public void testReplaceFileSeparatorWhenSeparatorIsEmpty() throws Exception {
        String path = "path/path/path";

        String expected = "_p_a_t_h_/_p_a_t_h_/_p_a_t_h_";
        String actual = FileUtils.replaceFileSeparator(path, "", "_");

        assertEquals(expected, actual);
    }

    @Test
    public void testReplaceFileSeparatorWhenReplacementIsEmpty() throws Exception {
        String path = "path/path/path";

        String expected = "pathpathpath";
        String actual = FileUtils.replaceFileSeparator(path, "/", "");

        assertEquals(expected, actual);
    }

    @Test(expected = IOException.class)
    public void testDeleteDirectoryNotRecursivelyWhenDirDoesNotExist() throws Exception {
        FileUtils.deleteDirectory(new File("non existent"), false);
    }

    @Test(expected = IOException.class)
    public void testDeleteDirectoryNotRecursivelyWhenDirIsFile() throws Exception {
        FileUtils.deleteDirectory(file1, false);
    }

    @Test(expected = IOException.class)
    public void testDeleteDirectoryRecursivelyWhenDirDoesNotExist() throws Exception {
        FileUtils.deleteDirectory(new File("non existent"), true);
    }

    @Test(expected = IOException.class)
    public void testDeleteDirectoryRecursivelyWhenDirIsFile() throws Exception {
        FileUtils.deleteDirectory(file1, true);
    }

    @Test
    public void testDeleteDirectoryNotRecursivelyWhenDirIsEmpty() throws Exception {
        File dir = rootFolder.newFolder("dir");

        FileUtils.deleteDirectory(dir, false);

        assertFalse(dir.exists());
    }

    @Test(expected = IOException.class)
    public void testDeleteDirectoryNotRecursivelyWhenDirIsNotEmpty() throws Exception {
        File dir = rootFolder.newFolder("dir");
        new File(dir, "file").createNewFile();

        FileUtils.deleteDirectory(dir, false);
    }

    @Test
    public void testDeleteDirectoryRecursivelyWhenDirIsEmpty() throws Exception {
        File dir = rootFolder.newFolder("dir");

        FileUtils.deleteDirectory(dir, true);

        assertFalse(dir.exists());
    }

    @Test
    public void testDeleteDirectoryRecursivelyWhenDirIsNotEmpty() throws Exception {
        File dir = rootFolder.newFolder("dir");
        new File(dir, "file").createNewFile();

        FileUtils.deleteDirectory(dir, true);

        assertFalse(dir.exists());
    }

}
