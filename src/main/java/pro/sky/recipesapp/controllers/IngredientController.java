package pro.sky.recipesapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.recipesapp.model.Ingredient;
import pro.sky.recipesapp.model.Recipe;
import pro.sky.recipesapp.services.IngredientService;

import java.util.Collection;

/**
 * Контроллер для ингредиентов.
 */

@RestController
@RequestMapping("/ingredients")
@Tag(name = "Ингредиенты", description = "CRUD-операции и другие эндпоинты для работы с ингредиентами")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @Operation(
            summary = "Создаем новый ингредиент."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент был добавлен"
            )})
    @PostMapping
    public ResponseEntity<Long> addNewIngredient(@RequestBody Ingredient ingredient) { //Создаем новый ингредиент.
        long id = ingredientService.addNewIngredient(ingredient);
        return ResponseEntity.ok(id);
    }

    @Operation(
            summary = "Получаем ингредиент по его id."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент был найден"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ингредиент не найден"
            )})
    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable long id) { //Получаем ингредиент по его id.
        Ingredient ingredient = ingredientService.getIngredientById(id);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }

    @Operation(
            summary = "Получаем список всех ингредиентов."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиенты были найдены"
            )})
    @GetMapping
    public ResponseEntity<Collection<Ingredient>> getAllIngredients() { //Получаем список всех ингредиентов.
        Collection<Ingredient> ingredients = ingredientService.getAllIngredients();
        return ResponseEntity.ok(ingredients);
    }

    @Operation(
            summary = "Редактируем ингредиент по его id."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент был отредактирован"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ингредиент не отредактирован"
            )})
    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> editIngredient(@PathVariable long id,
                                                     @RequestBody Ingredient ingredient) { //Редактируем ингредиент по его id.
        ingredient = ingredientService.editIngredient(id, ingredient);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }

    @Operation(
            summary = "Удаляем ингредиент по его id."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент был удален"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ингредиент не удален"
            )})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable long id) { //Удаляем ингредиент по его id.
        if (ingredientService.deleteIngredient(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
