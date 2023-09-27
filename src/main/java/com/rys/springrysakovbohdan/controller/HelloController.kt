package com.rys.springrysakovbohdan.controller

import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView


@RestController
@RequestMapping("/")
class HelloController {
    @GetMapping("/")
    fun hello(map: ModelMap): ModelAndView {
        map.addAttribute("name", "For fuck's sake")
        return ModelAndView("hello", map)
    }
}
