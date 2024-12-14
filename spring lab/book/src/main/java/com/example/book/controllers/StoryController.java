package com.example.book.controllers;
import com.example.book.dto.StoryDto;
import com.example.book.services.StoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/stories")
public class StoryController {
    private final StoryService storyService;

    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    @GetMapping
    public List<StoryDto> getStories(){
        return storyService.getAllStories();
    }

    @PostMapping
    public StoryDto addStory(@RequestBody StoryDto story){
        return storyService.addStory(story);
    }
    @PutMapping("/{id}")
    public StoryDto updateStory(@PathVariable("id") UUID id, @RequestBody String story){
        return storyService.updateStory(id, story);
    }

    @DeleteMapping("/{id}")
    ResponseEntity DeleteStory(@PathVariable("id") UUID id){
        return storyService.deleteStory(id);
    }
}

