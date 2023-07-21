package pro.sky.recipesapp.services;

import pro.sky.recipesapp.model.Ingredient;

import java.util.Collection;

/**
 * Сервис по работе с ингредиентами
 */
public interface IngredientService {

    long addNewIngredient(Ingredient ingredient);

    Ingredient getIngredientById(long idIngredient);

    Collection<Ingredient> getAllIngredients();

    Ingredient editIngredient(long idIngredient, Ingredient ingredient);

    boolean deleteIngredient(long idIngredient);
}
