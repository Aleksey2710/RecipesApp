package pro.sky.recipesapp.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pro.sky.recipesapp.model.Ingredient;
import pro.sky.recipesapp.model.Recipe;
import pro.sky.recipesapp.services.FileService;
import pro.sky.recipesapp.services.RecipeService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Бизнес-логика для рецептов.
 */

@Service
public class RecipeServiceImpl implements RecipeService {

    private final FileService fileService;

    public RecipeServiceImpl(@Qualifier("fileRecipeServiceImpl") FileService fileService) {
        this.fileService = fileService;
    }

    private long idRecipe = 1L;

    private Map<Long, Recipe> recipeMap = new LinkedHashMap<>();


    @PostConstruct
    private void init() {
        try {
            readFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public long addNewRecipe(Recipe recipe) { //Создаем новый рецепт.
        recipeMap.put(idRecipe, recipe);
        saveToFile();
        return idRecipe++;
    }

    @Override
    public Recipe getRecipeById(long idRecipe) { //Получаем рецепт по его id.
        return recipeMap.get(idRecipe);
    }

    @Override
    public Collection<Recipe> getAllRecipes() { //Получаем список всех рецептов.
        return recipeMap.values();

    }

    @Override
    public Recipe editRecipeById(long idRecipe, Recipe recipe) { //Редактируем рецепт по его id.
        if (recipeMap.containsKey(idRecipe)) {
            recipeMap.put(idRecipe, recipe);
            saveToFile();
            return recipe;
        }
        return null;
    }

    @Override
    public boolean deleteRecipe(long idRecipe) { //Удаляем рецепт по его id.
        if (recipeMap.containsKey(idRecipe)) {
            recipeMap.remove(idRecipe);
            saveToFile();
            return true;
        }
        return false;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipeMap);
            fileService.saveToFile(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile() {
        try {
            String json = fileService.readFromFile();
            recipeMap = new ObjectMapper().readValue(json, new TypeReference<HashMap<Long, Recipe>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Path createAllRecipes() throws IOException {

        Path path = fileService.createTempFile("allRecipes");
        try (Writer writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            String symbol = " - ";

            for (Recipe recipe : recipeMap.values()) {

                writer.append("\n").append(recipe.getNameRecipe()).append("\n");

                writer.append("\n Время приготовления: " + recipe.getCookingTime()
                        + " " + recipe.getTimeMeasurement());

                writer.append("\n Ингредиенты: \n");

                for (Ingredient ingredient : recipe.getIngredients()) {
                    writer.append(symbol).append(ingredient.getNameIngredient() + " "
                            + ingredient.getCountIngredients()
                            + ingredient.getCountMeasurement() + "\n");
                }

                writer.append("\n Инструкция приготовления: \n");

                for (String step : recipe.getSteps()) {
                    writer.append(symbol).append(step).append("\n");
                }

                writer.append("\n");
            }
        }
        return path;
    }

}
