package pro.sky.recipesapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.recipesapp.services.FileService;

import java.io.*;

@RestController
@RequestMapping("/files")
@Tag(name = "Файлы", description = "Загрузка и выгрузка файлов в формате .json")
public class FilesController {

    private final FileService fileRecipeService;
    private final FileService fileIngredientService;

    public FilesController(@Qualifier("fileRecipeServiceImpl") FileService fileRecipeService,
                           @Qualifier("fileIngredientServiceImpl") FileService fileIngredientService) {
        this.fileRecipeService = fileRecipeService;
        this.fileIngredientService = fileIngredientService;
    }


    @GetMapping(value = "/export", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Выгрузка файлов рецептов"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Файл успешно создан"
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Файл не создан"
            )})
    public ResponseEntity<InputStreamResource> downloadDataFile() throws FileNotFoundException { //Выгрузка файлов
        File file = fileRecipeService.getDataFile();

        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON) //Задаем тип файла
                    .contentLength(file.length()) //Узнаем длину файла
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"RecipesLog.json\"") //Задаем название файла
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build(); //Статус 204
        }
    }
    @PostMapping(value = "/import/recipe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Загрузка файлов рецептов"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Файл рецептов загружен"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Файл рецептов не загружен"
            )})
    public ResponseEntity<Void> upLoadDataRecipeFile(@RequestParam MultipartFile file) { //Генерация файла, загрузка
        fileRecipeService.cleanDataFile(); //Удаляем dataRecipe, создаем новый
        try {
            fileRecipeService.upLoadDataRecipeFile(file);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.getStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping(value = "/import/ingredient", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Загрузка файлов ингредиентов"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Файл ингредиентов загружен"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Файл ингредиентов не загружен"
            )})
    public ResponseEntity<Void> upLoadDataIngredientFile(@RequestParam MultipartFile file) { //Генерация файла, загрузка
        fileIngredientService.cleanDataFile(); //Удаляем dataIngredient, создаем новый
        try {
            fileIngredientService.upLoadDataRecipeFile(file);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.getStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
