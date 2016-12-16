package ro.ucv.ace;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Geo on 08.12.2016.
 */
public class TreeView {

    public static List<String> permittedExtensions = Arrays.asList("java", "c", "cpp", "cs", "json", "xml");

    private static List<String> contents = new ArrayList<>();

    public static void main(String[] args) throws JsonProcessingException, FileNotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.setVisibilityChecker(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));

        TreeView treeView = new TreeView();
        Node node = new Node("1", "D:\\wad_team2\\5\\1\\1\\1", false, "");

        treeView.listFiles(new File("D:\\wad_team2\\5\\1\\1\\1"), node);

        System.out.println(objectMapper.writeValueAsString(node));
        contents.forEach(System.out::println);
    }

    public void listFiles(File root, Node parent) {
        File[] files = root.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            String extension = FilenameUtils.getExtension(file.getAbsolutePath());
            if (!file.isDirectory() && !TreeView.permittedExtensions.contains(extension)) {
                continue;
            }

            if (file.isDirectory()) {
                Node node = new Node(file.getName(), file.getPath(), false, extension);
                parent.addChild(node);
                listFiles(file, node);
            } else {
                parent.addChild(new Node(file.getName(), file.getPath(), true, extension));
            }
        }
    }
}
