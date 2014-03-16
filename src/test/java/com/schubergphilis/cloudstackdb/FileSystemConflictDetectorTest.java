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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

@RunWith(PowerMockRunner.class)
public class FileSystemConflictDetectorTest {

    @Mock
    private MissingFilesDetector missingFilesDetectorMock;

    @Mock
    private ContentsChangedFilesDetector fileContentsChangeDetectorMock;

    @Mock
    private SourceCodeVersion currentVersionMock;

    @Mock
    private SourceCodeVersion nextVersionMock;

    @Test
    public void testDetect() throws Exception {
        FileSystemConflictDetector detector = new FileSystemConflictDetector(currentVersionMock, nextVersionMock);
        Whitebox.setInternalState(detector, "detectors", Arrays.asList(new ConflictDetector[] {missingFilesDetectorMock, fileContentsChangeDetectorMock}));

        detector.detect();

        verify(missingFilesDetectorMock, times(1)).detect();
        verify(fileContentsChangeDetectorMock, times(1)).detect();
    }
}
