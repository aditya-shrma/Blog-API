package com.scaler.blogapi.articles;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
public class ArticlesController {

    private final ArticlesService articleService;

    public ArticlesController(ArticlesService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("")
    public String getArticles()
    {
        return "articles";
    }

    @GetMapping("/private")
    public String getPrivateArticles(@AuthenticationPrincipal Integer userId)
    {
        return "private articles for user : "+userId;
    }

}
