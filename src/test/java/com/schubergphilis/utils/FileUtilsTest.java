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
}
