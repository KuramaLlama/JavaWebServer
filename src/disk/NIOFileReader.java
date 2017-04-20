package disk;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class NIOFileReader {

    private RandomAccessFile file;

    public NIOFileReader(RandomAccessFile file) {
        this.file = file;
    }

    public NIOFileReader(String filePath) throws FileNotFoundException {
        this.file = new RandomAccessFile(filePath, "r");
    }

    public void readIntoBuffer(ByteBuffer byteBuffer) throws IOException {
        byteBuffer.clear();
        FileChannel fileChannel = file.getChannel();
        fileChannel.read(byteBuffer);
        fileChannel.close();
    }

    public String readString(ByteBuffer byteBuffer) throws IOException {
        readIntoBuffer(byteBuffer);
        return new String(byteBuffer.array()).trim();
    }
}
