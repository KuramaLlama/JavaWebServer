package disk.tests;

import disk.NIOFileReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;

public class FilesOnReadTest {

    @Test
    public void readFile() throws IOException {
        System.out.println("---------------- TESTING FILE READER ----------------");
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        String filePath = System.getProperty("user.dir") + "/src/disk/tests/testresources/test";
        NIOFileReader fileReader = new NIOFileReader(filePath);
        String dataTxt = fileReader.readString(byteBuffer);
        System.out.println("Test file locale: " + filePath);
        System.out.println("Test file data: " + dataTxt);
        Assert.assertEquals("This is a test file for the Java Web Server! :D", dataTxt);
    }
}
