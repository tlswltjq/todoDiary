package tlswltjq.todoDiary.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/testGetMapping")
    public String testcontroller(){
        return "HelloWorld!";
    }
    @GetMapping("/{id}")
    public String pathVariableTest(@PathVariable(required = false) String id){
        return "V : " + id;
    }
}
