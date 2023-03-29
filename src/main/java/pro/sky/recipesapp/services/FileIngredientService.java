package pro.sky.recipesapp.services;

public interface FileIngredientService {
    boolean saveToFile(String json);

    String readFromFile();
}
