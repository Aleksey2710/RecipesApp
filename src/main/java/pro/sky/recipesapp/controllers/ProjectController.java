package pro.sky.recipesapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.recipesapp.services.ProjectService;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping()
    public String starting() {
        return projectService.getStarting();
    }

    @GetMapping("/info")
    public String info(/*@RequestParam String studName,
                         @RequestParam String projName,
                         @RequestParam String date,
                         @RequestParam String descript*/) throws FileNotFoundException {

        return projectService.getInfoProject();
    }

}
