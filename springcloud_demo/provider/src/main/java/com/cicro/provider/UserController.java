package com.cicro.provider;

import com.cicro.common.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @GetMapping("/user/{ids}")//假设 consumer 传过来的多个 id 的格式是 1,2,3,4....
    public List<User> getUsers(@PathVariable String ids) {
        System.out.println(ids);
        String[] split = ids.split(",");
        ArrayList<User> list = new ArrayList<>(split.length + 1);
        for (String s : split) {
            User user = new User();
            user.setId(Integer.parseInt(s));
            list.add(user);
        }
        return list;
    }
}
