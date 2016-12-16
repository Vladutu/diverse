package ro.ucv.ace;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Geo on 30.11.2016.
 */
public class Folder {
    public static void main(String[] args) {
        String folderRoot = "D:\\wad_team2";
        String profSsn = "1940826160041";
        String topicName = "FLA";
        String task = "Homework1";

        String stringPath = String.format("%s%s%s%s%s%s%s", folderRoot, File.separator, profSsn,
                File.separator, topicName, File.separator, task);
        Path path = Paths.get(stringPath);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            byte[] input = Base64.decode("dGVzdA0KdGVzdHRlc3QNCnRlc3R0ZXN0dA0Kc2UNCnRlc3RzZXQNCnNldHNlDQp0cw0KZXQNCnNldGVzDQp0ZXN0c2V0");
            byte[] output = Base64.decode("dGVzdA0KdGVzdHRlc3QNCnRlc3R0ZXN0dA0Kc2UNCnRlc3RzZXQNCnNldHNlDQp0cw0KZXQNCnNldGVzDQp0ZXN0c2V0");
            try (OutputStream stream = new FileOutputStream(stringPath + File.separator + "data.in")) {
                stream.write(input);
            }
            try (OutputStream stream = new FileOutputStream(stringPath + File.separator + "data.expected")) {
                stream.write(input);
            }
        } catch (Base64DecodingException | IOException e) {
            e.printStackTrace();
        }
    }
}
