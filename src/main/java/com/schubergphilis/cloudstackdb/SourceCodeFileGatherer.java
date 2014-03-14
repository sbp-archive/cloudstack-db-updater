package com.schubergphilis.cloudstackdb;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.not;

import java.io.File;
import java.util.Set;

import org.apache.log4j.Logger;

import com.schubergphilis.utils.FileUtils;

public class SourceCodeFileGatherer {

    private static final Logger log = Logger.getLogger(SourceCodeFileGatherer.class);

    private SourceCodeFileGatherer() {
    }

    public static Set<File> gatherDbRelatedFiles(File sourceCodeBaseDir) {
        log.info("Gathering DB related files from " + sourceCodeBaseDir.getPath());
        return FileUtils.gatherFilesThatMatchCriteria(sourceCodeBaseDir,
                allOf(anyOf(containsString("/db/"), endsWith("VO.java")), not(containsString("/test/")), not(containsString("/target/"))));
    }
}
