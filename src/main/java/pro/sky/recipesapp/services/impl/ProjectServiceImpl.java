package pro.sky.recipesapp.services.impl;

import org.springframework.stereotype.Service;
import pro.sky.recipesapp.services.ProjectService;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;

@Service
public class ProjectServiceImpl implements ProjectService {

    public static final String STUDENT_NAME = "Aleksey";
    public static final String PROJECT_NAME = "Рецепты";
    public static final String START_VALUE = "<h1 style=\"text-align: center\">Приложение запущено!</h1>";

    public static final String DATE = LocalDate.of(2023, 2, 3).toString();

            /*"Рецепты содержат в себе следующую информацию:\n" +
            " - описание приготовляемого блюда;\n" +
            " - ингредиенты для данного блюда;\n" +
            " - пропорции;\n" +
            " - способ приготовления.";*/


    @Override
    public String getInfoProject() {
        return "Имя студента: " + STUDENT_NAME + "</br>" +
                "Название проекта: " + PROJECT_NAME + "</br>" +
                "Дата создания: " + DATE + "</br>" +
                "Описание проекта: </br>" + getProjectDescription();
    }

    @Override
    public String getStarting() {
        return START_VALUE;
    }

    @Override
    public String getProjectDescription() {
        String separator = File.separator;
        String path = "C:" + separator + "Users" + separator + "User" + separator +
                "IdeaProjects" + separator + "RecipesApp" + separator + "src" + separator + "main"
                + separator + "java" + separator + "pro" + separator + "sky" + separator + "recipesapp"
                + separator + "ReadMe.md";
        return readFile(path);
    }

    private static String readFile(String nameFile) {
        File file = new File(nameFile);
        try (Scanner scanner = new Scanner(file)) {
            String s = "";
            while (scanner.hasNextLine()) {
                s += scanner.nextLine();
            }
            return s;
        } catch (FileNotFoundException e) {
            return "Файл не найден!";
        }
    }
}
