package com.blog.demo.controller;

import java.util.List;
import java.util.Optional;

import com.blog.demo.model.Tags;
import com.blog.demo.model.response.ResponseBaseDTO;
import com.blog.demo.service.TagsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthorController
 */
@RestController
@RequestMapping(path = "/api")
public class TagsController {
    
    @Autowired
    private TagsService tagsService; 

    @GetMapping(value = "/tags")
    public ResponseEntity<ResponseBaseDTO<Iterable<Tags>>> listUser(@RequestParam(required = false) String name){ 
        ResponseBaseDTO<Iterable<Tags>> response = new ResponseBaseDTO<Iterable<Tags>>(); 

        try {         
            if(name == null){
                Iterable<Tags> tagslist = tagsService.findAll();
                response.setStatus(true);
                response.setCode("200");
                response.setMessage("success");
                response.setData(tagslist);  
            }else{
                List<Tags> tagslist = tagsService.findByName(name);
                response.setStatus(true);
                response.setCode("200");
                response.setMessage("success");
                response.setData(tagslist);  
            }
                  
            return new ResponseEntity<>(response ,HttpStatus.OK);
        }
        catch(Exception e)
        {
            // catch error when get user
            response.setStatus(false);
            response.setCode("500");
            response.setMessage(e.getMessage());
        }
        
        return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
    }

    @GetMapping(value = "/tags/{id}")
    public ResponseEntity<ResponseBaseDTO<Optional<Tags>>> getTagsById(@PathVariable("id") Integer id) {

        ResponseBaseDTO<Optional<Tags>> response = new ResponseBaseDTO<Optional<Tags>>(); 

        try
        {     
            Optional<Tags> tags = tagsService.findById(id);
            
            if (tags.isPresent()) {           
                response.setStatus(true);
                response.setCode("200");
                response.setMessage("success");
                response.setData(tags);     
                
            }
            return new ResponseEntity<>( response, HttpStatus.OK);
        }
        catch(Exception e)
        {
            // catch error when get user
            response.setStatus(false);
            response.setCode("500");
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping(value = "/tags")
    public ResponseEntity<ResponseBaseDTO<Tags>> create(@RequestBody Tags tags){
        
        ResponseBaseDTO<Tags> response = new ResponseBaseDTO<Tags>(); 

        if(tags.getName().isEmpty() )
        {
            response.setMessage("name is null");
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
        
        try{
            Tags result =  tagsService.save(tags);
            response.setStatus(true);
            response.setCode("200");
            response.setMessage("success");
            response.setData(result);           
            return new ResponseEntity<>(response ,HttpStatus.OK);
        }catch(Exception e){
            response.setStatus(false);
            response.setCode("500");
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
       
    }

    @DeleteMapping(value = "/tags/{id}")
    public  ResponseEntity<ResponseBaseDTO<Iterable<Tags>>> delete(@PathVariable(value = "id") Integer id) {
       
        ResponseBaseDTO<Iterable<Tags>> response = new ResponseBaseDTO<Iterable<Tags>>(); 

        try{         
            tagsService.deleteById(id);
            response.setStatus(true);
            response.setCode("200");
            response.setMessage("success");    
            return new ResponseEntity<>(response ,HttpStatus.OK);
        }catch(Exception e){
            response.setStatus(false);
            response.setCode("500");
            response.setMessage( "id " + id + " not exists! " );
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
      
    }

    @PutMapping(value = "/tags/{id}")
    public ResponseEntity<ResponseBaseDTO<Tags>> updateUser(@PathVariable("id") Integer id, @RequestBody Tags tags) {
     
        ResponseBaseDTO<Tags> response = new ResponseBaseDTO<Tags>();
        try {
            Optional<Tags> tagsData = tagsService.findById(id);
            if (tagsData.isPresent()) {
                Tags _tags = tagsData.get();
                _tags.setName(tags.getName());
                response.setStatus(true);
                response.setCode("200");
                response.setMessage("success");  
                response.setData(tagsService.save(_tags));            
                
            }
            return new ResponseEntity<>( response, HttpStatus.OK);
          
        } catch (Exception e) {
            response.setStatus(false);
            response.setCode("500");
            response.setMessage( "id " + id + " not exists! " );
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
       
    }
}