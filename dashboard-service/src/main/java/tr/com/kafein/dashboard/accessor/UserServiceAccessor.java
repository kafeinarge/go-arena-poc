package tr.com.kafein.dashboard.accessor;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tr.com.kafein.dashboard.dto.UserDto;

@FeignClient(value = "user-service", url = "http://localhost:8766/")
public interface UserServiceAccessor {

    @RequestMapping(method = RequestMethod.GET, value = "/get-one", produces = "application/json")
    UserDto getOne();
}
