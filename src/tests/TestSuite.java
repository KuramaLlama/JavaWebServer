package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import disk.tests.FilesOnReadTest;
import nioserver.tests.ServerTests;
import webserver.tests.HTTPMessageTests;
import webserver.tests.HTTPStringBuilderTests;
import webserver.tests.RouterTests;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        ServerTests.class,
        FilesOnReadTest.class,
        HTTPMessageTests.class,
        HTTPStringBuilderTests.class,
        RouterTests.class
})

public class TestSuite {}
