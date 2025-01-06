package com.example.finalproject.Service;

import com.example.finalproject.DTO.TagODTO;
import com.example.finalproject.Model.Tag;
import com.example.finalproject.Repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public List<TagODTO> getAllTags() {
        return convertTagToODTO(tagRepository.findAll());
    }

    public List<TagODTO> convertTagToODTO(List<Tag> tags) {
        List<TagODTO> tagDTOs = new ArrayList<>();
        for (Tag tag : tags) {
            tagDTOs.add(new TagODTO(tag.getName()));
        }
        return tagDTOs;
    }

}
