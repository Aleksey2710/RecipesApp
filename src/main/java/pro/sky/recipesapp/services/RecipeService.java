package pro.sky.recipesapp.services;

import pro.sky.recipesapp.model.Recipe;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

/**
 * Сервис по работе с рецептами
 */
public interface RecipeService {

    long addNewRecipe(Recipe recipe);

    Recipe getRecipeById(long idRecipe);

    Collection<Recipe> getAllRecipes();

    Recipe editRecipeById(long idRecipe, Recipe recipe);

    boolean deleteRecipe(long idRecipe);

    Path createAllRecipes() throws IOException;
}
