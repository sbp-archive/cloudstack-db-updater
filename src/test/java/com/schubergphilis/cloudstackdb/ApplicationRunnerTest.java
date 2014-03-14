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
