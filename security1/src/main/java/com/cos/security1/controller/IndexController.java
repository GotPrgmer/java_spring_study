package com.cos.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // returning view
public class IndexController {

    //localhost:8080/
    ///localhost:8080
    @GetMapping({"","/"})
    public String index(){
        //mustache , default folder location : src/main/resources/
        //settings for view resolver : templates (prefix) .mustache (suffix) could be omitted!
        return "index"; // This code will call the file located in src/main/resources/templates/index.mastache
    }
}
