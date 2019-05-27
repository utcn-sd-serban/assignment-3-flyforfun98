package ro.utcn.sd.flav.stackoverflow.dto;


import lombok.Data;
import ro.utcn.sd.flav.stackoverflow.entity.Tag;

@Data
public class TagDTO {

    private String title;
    //private Integer tagId;

    public static TagDTO ofEntity(Tag tag)
    {
        TagDTO tagDTO = new TagDTO();
        //tagDTO.setTagId(tag.getTagId());
        tagDTO.setTitle(tag.getTitle());

        return tagDTO;
    }
}
