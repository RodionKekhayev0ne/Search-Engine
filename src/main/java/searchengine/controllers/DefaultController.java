package searchengine.controllers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import searchengine.Application;
import searchengine.model.SiteModel;

import java.util.Date;

@RestController
@RequestMapping("/api")
public class DefaultController {







    /**
     * Метод формирует страницу из HTML-файла index.html,
     * который находится в папке resources/templates.
     * Это делает библиотека Thymeleaf.
     */




    @RequestMapping("/")
    public String index(Model model) {

        model.addAttribute("count", 10);
        return "index";
    }


    @GetMapping
    public void getUsers() {

    }



}
