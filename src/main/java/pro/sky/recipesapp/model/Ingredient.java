package pro.sky.recipesapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Описание ингредиентов.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {

    private String nameIngredient;
    private int countIngredients;
    private String countMeasurement;
}
