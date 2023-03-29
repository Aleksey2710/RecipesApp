package pro.sky.recipesapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.recipesapp.model.Recipe;
import pro.sky.recipesapp.services.RecipeService;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;

/**
 * Контроллер для рецептов.
 */

@RestController
@RequestMapping("/recipes")
@Tag(name = "Рецепты", description = "CRUD-операции и другие эндпоинты для работы с рецептами")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Operation(
            summary = "Создаем новый рецепт."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт был добавлен"
            )})
    @PostMapping
    public ResponseEntity<Long> addNewRecipe(@RequestBody Recipe recipe) { //Создаем новый рецепт.
        long id = recipeService.addNewRecipe(recipe);
        return ResponseEntity.ok(id);
    }

    @Operation(
            summary = "Получаем рецепт по его id."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт найден"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Рецепт не найден"
            )})
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable long id) { //Получаем рецепт по его id.
        Recipe recipe = recipeService.getRecipeById(id);
        if (ObjectUtils.isEmpty(recipe)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @Operation(
            summary = "Получаем список всех рецептов."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепты были найдены"
            )})
    @GetMapping
    public ResponseEntity<Collection<Recipe>> getAllRecipes() { //Получаем список всех рецептов.
        Collection<Recipe> allRecipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(allRecipes);
    }

    @Operation(
            summary = "Редактируем рецепт по его id."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт был отредактирован"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Рецепт не отредактирован"
            )})
    @PutMapping("/{id}")
    public ResponseEntity<Recipe> editRecipe(@PathVariable long id,
                                             @RequestBody Recipe recipe) {//Редактируем рецепт по его id.
        recipe = recipeService.editRecipeById(id, recipe);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @Operation(
            summary = "Удаляем рецепт по его id."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт был удален"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Рецепт не удален"
            )})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable long id) { //Удаляем рецепт по его id.
        if (recipeService.deleteRecipe(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    @Operation(
            summary = "Загружаем список рецептов в формате txt"
    )
    @GetMapping(value = "/getAllRecipe")
    public ResponseEntity<Object> getAllRecipesPrint() {

        try {
            Path path = recipeService.createAllRecipes();
            if (Files.size(path) == 0) { //Если файл пустой
                return ResponseEntity.noContent().build(); //Статус 204
            }
            InputStreamResource resource = new InputStreamResource(new FileInputStream(path.toFile())); //Берем у файла входной поток, заворачиваем его в ресурс
            return ResponseEntity.ok() //Формируем и возвращаем HTTP ответ
                    .contentType(MediaType.TEXT_PLAIN) //Задаем тип файла
                    .contentLength(Files.size(path)) //Узнаем длину файла
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + LocalDateTime.now() + "-report.txt\"") //Задаем название файла
                    .body(resource);

        } catch (IOException e) { //При исключении отправляем код...
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.toString());// код 500
        }
    }
}
