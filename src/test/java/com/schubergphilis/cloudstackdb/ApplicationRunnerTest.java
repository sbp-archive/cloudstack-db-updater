package com.schubergphilis.cloudstackdb;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kohsuke.args4j.CmdLineException;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

@PrepareForTest({ApplicationRunner.class})
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
}
