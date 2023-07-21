package pro.sky.recipesapp.services;

import java.io.FileNotFoundException;

public interface ProjectService {

    String getInfoProject() throws FileNotFoundException;

    String getStarting();

    String getProjectDescription() throws FileNotFoundException;
}
