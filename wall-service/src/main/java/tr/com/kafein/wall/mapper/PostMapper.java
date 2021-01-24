package tr.com.kafein.wall.mapper;

import org.mapstruct.Mapper;
import tr.com.kafein.wall.data.Post;
import tr.com.kafein.wall.dto.PostDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostDto toDto(Post entity);
    List<PostDto> toDto(List<Post> entities);
    Post toEntity(PostDto dto);
}
