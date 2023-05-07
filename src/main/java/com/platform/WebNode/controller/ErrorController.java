package com.platform.WebNode.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping
    public String error(Model model) {
        model.addAttribute("title","WebNode - Error");
        return "error";
    }

    /*
    @RequestMapping("/error")
    public String error(HttpServletRequest request, Model model) {

        model.addAttribute("title","WebNode - Error");
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error-404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error-500";
            }
        }

        return "error";
    }*/
}
