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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kohsuke.args4j.CmdLineException;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

@RunWith(PowerMockRunner.class)
public class ApplicationRunnerTest {

    @Mock
    private Logger logMock;

    @Test
    public void testMainWithoutArgs() throws Exception {
        Whitebox.setInternalState(ApplicationRunner.class, "log", logMock);

        ApplicationRunner.main(new String[] {});

        verify(logMock, times(1)).error(anyString(), notNull(CmdLineException.class));
        verify(logMock, times(1)).info(anyString());
    }

    @Test
    public void testMainWithValidArguments() throws Exception {
        Whitebox.setInternalState(ApplicationRunner.class, "log", logMock);

        File currentVersionDir = new File(Thread.currentThread().getContextClassLoader().getResource("versions/v1").getFile());
        File nextVersionDir = new File(Thread.currentThread().getContextClassLoader().getResource("versions/v2").getFile());

        ApplicationRunner.main(new String[] {"-cv", currentVersionDir.getAbsolutePath(), "-nv", nextVersionDir.getAbsolutePath()});

        verify(logMock, times(0)).error(anyString(), notNull(CmdLineException.class));
        verify(logMock).info(anyString());
    }

    @Test
    public void testPrintFindings() throws Exception {
        List<Conflict> conflicts = Arrays.asList(new Conflict[] {new ContentsChangedAndMovedFilesConflict(new ArrayList<ChangedSourceCodeFile>())});
        String findings = ApplicationRunner.printFindings(conflicts);

        assertNotNull(findings);
        assertFalse(findings.isEmpty());
    }

}
