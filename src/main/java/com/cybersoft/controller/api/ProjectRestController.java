package com.cybersoft.controller.api;


import com.cybersoft.service.impl.ProjectServiceImpl;
import com.cybersoft.model.admin.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalTime.now;

@RestController
@RequestMapping("/api")
public class ProjectRestController {
    @Autowired
    private ProjectServiceImpl projectService;

    @GetMapping("/projects")
    private ResponseEntity<List<Project>> listProjects(){
        List<Project> projects= projectService.findAll ();

        return new ResponseEntity<List<Project>>(projects, HttpStatus.OK);
    }
    @GetMapping("/hotprojects")
    private ResponseEntity<List<Project>> hostProjects(){
        List<Project> projects= projectService.findHotest ();
        return new ResponseEntity<List<Project>>(projects, HttpStatus.OK);
    }

    @GetMapping(value = "/projects/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Project> getProject(@PathVariable("id") Long id) {
        Project project = projectService.findById(id);
        if (project == null) {

            return new ResponseEntity<Project>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @PostMapping(value = "/projects")
    public ResponseEntity<Project> createProject(@RequestBody Project project, UriComponentsBuilder ucBuilder) {
        try {
            project.setCheckin (LocalDateTime.now().toString ());
            projectService.save(project);
            return new ResponseEntity<Project>(project, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<Project>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/projects/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable("id") Long id, @RequestBody Project project) {

        Project currentProject = projectService.findById(id);

        if (currentProject == null) {
            System.out.println("Project with id " + id + " not found");
            return new ResponseEntity<Project>(HttpStatus.NOT_FOUND);
        }

        currentProject.setDescription (project.getDescription ());
        currentProject.setImage (project.getImage ());
        currentProject.setName (project.getName ());
        currentProject.setStatus (project.getStatus ());

        try {
            projectService.save(currentProject);
        }catch (Exception ex){
            return new ResponseEntity<Project>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Project>(currentProject, HttpStatus.OK);
    }
    @DeleteMapping(value = "/projects/{id}")
    public ResponseEntity<Project> deleteProject(@PathVariable("id") Long id){
        Project currentProject = projectService.findById(id);
        if (currentProject == null) {
            System.out.println("Project with id " + id + " not found");
            return new ResponseEntity<Project>(HttpStatus.NOT_FOUND);
        }
        try {
            projectService.remove (id);
        } catch (Exception e) {
            return new ResponseEntity<Project> (HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Project>(currentProject, HttpStatus.OK);
    }
}