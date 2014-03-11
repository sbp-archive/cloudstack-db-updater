package com.schubergphilis.cloudstackdb;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;

import java.io.File;
import java.util.Set;

import com.schubergphilis.utils.FileUtils;

public class SourceCodeFileGatherer {

    private SourceCodeFileGatherer() {
    }

    public static Set<File> gatherDbRelatedFiles(File sourceCodeBaseDir) {
        return FileUtils.gatherFilesThatMatchCriteria(sourceCodeBaseDir, anyOf(containsString("/db/"), endsWith("VO.java")));
    }

}
