package tr.com.kafein.uaaserver.accessor;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tr.com.kafein.uaaserver.dto.UserDto;

@FeignClient(value = "user-service", url = "${feign.client.url.user-service}")
public interface UserServiceAccessor  {


    @RequestMapping(method = RequestMethod.GET, value = "/username/{username}", produces = "application/json")
    UserDto findByUsername(@PathVariable String username);
}
