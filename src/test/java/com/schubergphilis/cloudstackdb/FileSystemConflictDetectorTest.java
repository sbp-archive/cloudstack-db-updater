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
    private FileContentsChangeDetector fileContentsChangeDetectorMock;

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
