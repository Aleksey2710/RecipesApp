package pro.sky.recipesapp.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Сервис по работе с файлами
 */
public interface FileService {

    /**
     * Сохранение ингредиента в файл
     *
     * @param json файл для сохранения (записи)
     * @return подтверждение сохранения ингредиента в файл
     */
    boolean saveToFile(String json);

    /**
     * Читаем (получаем) ингредиент из файла
     *
     * @return Получаем прочитанный файл
     */
    String readFromFile();

    boolean cleanDataFile();

    File getDataFile();

    void upLoadDataRecipeFile(MultipartFile file) throws IOException;

    Path createTempFile(String suffix);
}
