import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Использование: java FileDownloader <URL> <выходной файл>");
            return;
        }

        String fileUrl = args[0];
        String outputPath = args[1];

        try {
            downloadFile(fileUrl, outputPath);
            System.out.println("Файл успешно скачан!");
        } catch (IOException e) {
            System.err.println("Ошибка при скачивании файла: " + e.getMessage());
        }
    }

    public static void downloadFile(String fileUrl, String outputPath) throws IOException {
        URL url = new URL(fileUrl);
        try (InputStream in = url.openStream()) {
            Files.copy(in, Paths.get(outputPath), StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
