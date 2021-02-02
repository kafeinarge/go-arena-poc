package tr.com.kafein.dashboard.accessor;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tr.com.kafein.dashboard.dto.UserDto;

@FeignClient(value = "user-service", url = "${feign.client.url.user-service}")
public interface UserServiceAccessor {

    @GetMapping(value = "/get-one")
    UserDto getOne();

    @GetMapping(value = "/{id}")
    UserDto getById(@PathVariable("id") Long id);
}
