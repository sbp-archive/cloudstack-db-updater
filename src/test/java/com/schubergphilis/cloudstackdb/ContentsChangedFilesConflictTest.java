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

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ContentsChangedFilesConflictTest {

    @Test
    public void testPrint() throws Exception {
        String file1 = "file1";
        String file2 = "file2";
        List<ChangedSourceCodeFile> filenames = Arrays.asList(new ChangedSourceCodeFile[] {new ContentsChangedSourceCodeFile(file1, ""),
                new ContentsChangedSourceCodeFile(file2, "")});

        ContentsChangedFilesConflict fileContentHasChangedConflict = new ContentsChangedFilesConflict(filenames);

        assertThat(fileContentHasChangedConflict.print(), allOf(containsString(file1), containsString(file2)));
    }

}
