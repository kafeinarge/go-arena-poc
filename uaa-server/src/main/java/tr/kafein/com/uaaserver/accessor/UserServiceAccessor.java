package tr.kafein.com.uaaserver.accessor;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tr.kafein.com.uaaserver.dto.UserDto;

@FeignClient(value = "user-service", url = "http://localhost:8766/")
public interface UserServiceAccessor  {

    @RequestMapping(method = RequestMethod.GET, value = "/username/{username}", produces = "application/json")
    UserDto findByUsername(@PathVariable String username);
}
