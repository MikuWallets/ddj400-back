package kr.mikuwallets.djyurika400;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/"})
public class RootController {
    @RequestMapping({"/"})
    String home() {
        return "It works!";
    }
}
