package pro.sky.recipesapp.services.impl;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.recipesapp.services.FileService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

/**
 * Бизнес-логика для работы с файлами (рецептов).
 */
@Service
public class FileRecipeServiceImpl implements FileService {

    @Value("${pathToDataRecipeFile}")
    private String dataFilePath;
    @Value("${nameOfDataRecipeFile}")
    private String dataFileName;

    @Override
    public boolean saveToFile(String json) {

        try {
            Files.writeString(Path.of(dataFilePath, dataFileName), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String readFromFile() {

        try {
            return Files.readString(Path.of(dataFilePath, dataFileName));
        } catch (IOException e) {
            e.printStackTrace();
            return "{}";
        }
    }

    @Override
    public boolean cleanDataFile() {

        try {
            Path path = Path.of(dataFilePath, dataFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public File getDataFile() {

        return new File(dataFilePath + "/" + dataFileName);
    }

    @Override
    public void upLoadDataRecipeFile(MultipartFile file) throws IOException {

        Path filePath = Path.of(dataFilePath, file.getOriginalFilename());
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
                InputStream is = file.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
//        try (FileOutputStream fos = new FileOutputStream(getDataFile())) { //Открываем исходящий поток
//            IOUtils.copy(file.getInputStream(), fos); //Копируем входящий поток из запроса и копируем в исходящий поток
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public Path createTempFile(String suffix) {

        try {
            return Files.createTempFile(Path.of(dataFilePath), "tempFile", suffix);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
