package tr.com.kafein.wall.accessor;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tr.com.kafein.wall.dto.UserDto;

@FeignClient(value = "user-service", url = "http://localhost:8766/")
public interface UserServiceAccessor {

    @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = "application/json")
    UserDto getById(@PathVariable("id") Long id);

    @RequestMapping(method = RequestMethod.GET, value = "/username/{username}", produces = "application/json")
    UserDto findByUsername(@PathVariable("username") String username);
}
