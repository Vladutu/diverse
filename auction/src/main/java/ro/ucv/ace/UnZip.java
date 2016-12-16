package ro.ucv.ace;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class UnZip {
    List<String> fileList;
    private static final String INPUT_ZIP_FILE = "D:\\bank_deposit.zip";
    private static final String OUTPUT_FOLDER = "D:\\test";

    public static void main(String[] args) throws ZipException, Base64DecodingException, IOException {
        byte[] data = Base64.decode("UEsDBBQAAAAAAHWWfUkAAAAAAAAAAAAAAAAPAAAAbGV2ZWwxL2xldmVsIDIvUEsDBBQAAAAAAHeWfUnKZYZGDAAAAAwAAAAXAAAAbGV2ZWwxL2xldmVsIDIvdGVzdC50eHR0ZXN0IGxldmVsIDJQSwMEFAAAAAAAb5Z9SWDXKRoOAAAADgAAAA8AAABsZXZlbDEvdGVzdC50eHR0ZXN0IGxldmVsIDENClBLAQIUABQAAAAAAHWWfUkAAAAAAAAAAAAAAAAPAAAAAAAAAAAAEAAAAAAAAABsZXZlbDEvbGV2ZWwgMi9QSwECFAAUAAAAAAB3ln1JymWGRgwAAAAMAAAAFwAAAAAAAAABACAAAAAtAAAAbGV2ZWwxL2xldmVsIDIvdGVzdC50eHRQSwECFAAUAAAAAABvln1JYNcpGg4AAAAOAAAADwAAAAAAAAABACAAAABuAAAAbGV2ZWwxL3Rlc3QudHh0UEsFBgAAAAADAAMAvwAAAKkAAAAAAA==");
        try (OutputStream stream = new FileOutputStream("D:\\test2.zip")) {
            stream.write(data);
        }

        try {
            ZipFile zipFile = new ZipFile("D:\\test2.zip");
            zipFile.extractAll("D:\\");
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

}