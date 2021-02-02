package tr.com.kafein.uaaserver.accessor;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tr.com.kafein.uaaserver.dto.UserDto;

@FeignClient(value = "user-service", url = "${feign.client.url.user-service}")
public interface UserServiceAccessor  {

    @GetMapping(value = "/username/{username}")
    UserDto findByUsername(@PathVariable String username);
}
