package store.util;

import java.io.*;

public class MarkdownLoader {
    public static String loadMarkdown(String fileName) {
        StringBuilder content = new StringBuilder();
        readLine(fileName, content);
        return content.toString();
    }

    private static void readLine(String fileName, StringBuilder content) {
        try (InputStream inputStream = MarkdownLoader.class.getClassLoader().getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
