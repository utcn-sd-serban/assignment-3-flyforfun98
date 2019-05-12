package ro.utcn.sd.flav.stackoverflow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.utcn.sd.flav.stackoverflow.dto.TagDTO;
import ro.utcn.sd.flav.stackoverflow.service.TagManagementService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagManagementService tagService;

    @GetMapping("/tags")
    public List<TagDTO> listAll() {

        return tagService.listTags();
    }

    @PostMapping("/add-tags")
    public List<TagDTO> addTags(@RequestBody String tags) {

        int tagsLength = tags.length() - 2;
        String tagTitles = tags.substring(10, tagsLength);
        String  commaTags = tagTitles.replaceAll("\"", "");
        String[] splitTags = commaTags.split(",");
        List<String> tagsList = new ArrayList<>(Arrays.asList(splitTags));

        for(String tagTitle: tagsList) {
           tagService.lookForTag(tagTitle);
        }

        return  tagService.listTags();
    }
}
