package tr.com.kafein.wall.accessor;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tr.com.kafein.wall.dto.UserDto;

@FeignClient(value = "user-service", url = "${feign.client.url.user-service}")
public interface UserServiceAccessor {

    @GetMapping(value = "/{id}")
    UserDto getById(@PathVariable("id") Long id);

    @GetMapping(value = "/username/{username}")
    UserDto findByUsername(@PathVariable("username") String username);
}
