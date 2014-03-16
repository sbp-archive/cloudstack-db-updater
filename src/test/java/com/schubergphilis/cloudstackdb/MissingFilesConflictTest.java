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
package com.schubergphilis.cloudstackdb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class MissingFilesConflictTest {

    private final List<SourceCodeFile> sortedFiles = Arrays.asList(new SourceCodeFile[] {new SourceCodeFile("a"), new SourceCodeFile("b"), new SourceCodeFile("c")});

    @Test
    public void testConstructorWhenFilesAreSorted() throws Exception {
        MissingFilesConflict missingFilesConflict = new MissingFilesConflict(sortedFiles);

        List<SourceCodeFile> expected = sortedFiles;
        List<SourceCodeFile> actual = missingFilesConflict.getFiles();

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testConstructorWhenFilesAreSortedInReverseOrder() throws Exception {
        List<SourceCodeFile> reverseSortedFiles = new ArrayList<>(sortedFiles);
        Collections.sort(reverseSortedFiles, Collections.reverseOrder());
        MissingFilesConflict missingFilesConflict = new MissingFilesConflict(sortedFiles);

        List<SourceCodeFile> expected = sortedFiles;
        List<SourceCodeFile> actual = missingFilesConflict.getFiles();

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testConstructorWhenFilesAreInRandomOrder() throws Exception {
        List<SourceCodeFile> rendomOrderFiles = Arrays.asList(new SourceCodeFile[] {new SourceCodeFile("c"), new SourceCodeFile("a"), new SourceCodeFile("d"),
                new SourceCodeFile("b")});
        MissingFilesConflict missingFilesConflict = new MissingFilesConflict(rendomOrderFiles);

        List<SourceCodeFile> expected = Arrays.asList(new SourceCodeFile[] {new SourceCodeFile("a"), new SourceCodeFile("b"), new SourceCodeFile("c"), new SourceCodeFile("d")});
        List<SourceCodeFile> actual = missingFilesConflict.getFiles();

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testConstructorWhenThereAreNoFiles() throws Exception {
        List<SourceCodeFile> noFiles = new ArrayList<>();

        MissingFilesConflict missingFilesConflict = new MissingFilesConflict(noFiles);
        List<SourceCodeFile> actual = missingFilesConflict.getFiles();

        assertNotNull(actual);
        assertEquals(0, actual.size());
    }
}
