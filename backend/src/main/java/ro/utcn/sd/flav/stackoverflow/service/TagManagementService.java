package ro.utcn.sd.flav.stackoverflow.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.utcn.sd.flav.stackoverflow.dto.TagDTO;
import ro.utcn.sd.flav.stackoverflow.entity.Tag;
import ro.utcn.sd.flav.stackoverflow.exception.TagNotFoundException;
import ro.utcn.sd.flav.stackoverflow.repository.TagRepository;
import ro.utcn.sd.flav.stackoverflow.repository.RepositoryFactory;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagManagementService {

    private final RepositoryFactory repositoryFactory;


    @Transactional
    private TagDTO addTag(String title)
    {
        return TagDTO.ofEntity(repositoryFactory.createTagRepository().save(new Tag(title)));
    }

    @Transactional
    public void removeTag(Integer id)
    {
        TagRepository tagRepository = repositoryFactory.createTagRepository();
        Tag tag = tagRepository.findById(id).orElseThrow(TagNotFoundException::new);
        tagRepository.remove(tag);
    }

    @Transactional
    public List<TagDTO> listTags()
    {
        return repositoryFactory.createTagRepository().findAll()
                .stream().map(TagDTO::ofEntity).collect(Collectors.toList());
    }

    @Transactional
    public void updateTag(int id, String title)
    {
        TagRepository tagRepository = repositoryFactory.createTagRepository();
        Tag tag = tagRepository.findById(id).orElseThrow(TagNotFoundException::new);
        tag.setTitle(title);
        tagRepository.save(tag);
    }

    @Transactional
    public TagDTO lookForTag(String tag)
    {
        for (TagDTO i: listTags())
        {
            if(tag.equals(i.getTitle().toLowerCase()))
                return i;
        }

        return addTag(tag);
    }
}
