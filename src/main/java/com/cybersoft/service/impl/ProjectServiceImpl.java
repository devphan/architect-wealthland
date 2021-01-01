package com.cybersoft.service.impl;

import com.cybersoft.repository.ProjectRepository;
import com.cybersoft.model.admin.Project;
import com.cybersoft.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProjectServiceImpl implements IService<Project> {
    @Autowired
    private ProjectRepository projectRepository;

    public Page<Project> findAllPage(Pageable pageable){
        return projectRepository.findAllPage(pageable);
    }


    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public void save(Project object) {
        projectRepository.save(object);
    }

    @Override
    public Project findById(Long id) {
        return projectRepository.findById(id).get ();
    }

    @Override
    public void remove(Long id) {
        projectRepository.deleteById(id);
    }

    public List<Project> findHotest() {
        return projectRepository.findHotest();
    }
}
