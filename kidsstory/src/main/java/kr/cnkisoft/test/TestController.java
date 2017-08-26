package kr.cnkisoft.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by PureMaN on 2017-05-10.
 */
@Controller
public class TestController {

    @RequestMapping("test")
    @ResponseBody
    public String test() {
        return "test";
    }
}