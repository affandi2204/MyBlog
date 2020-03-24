package com.blog.demo.service;

import java.util.List;
import java.util.Optional;

import com.blog.demo.model.Tags;
import com.blog.demo.repository.TagsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TagsService
 */
@Service
public class TagsService {

    private final TagsRepository tagsRepository;

    @Autowired
    public TagsService(TagsRepository tagsRepository){
        this.tagsRepository = tagsRepository;
    }

    public List<Tags> findAll(){
        return tagsRepository.findAll();
    }

    public Optional<Tags> findById(Integer id) {
        return tagsRepository.findById(id);
    }

    public List<Tags> findByName(String name){
        return tagsRepository.findByName(name);
    }

    public Tags save(Tags tags){
        return tagsRepository.save(tags);
    }

    public void deleteById(Integer id) {
        tagsRepository.deleteById(id);
    }
}