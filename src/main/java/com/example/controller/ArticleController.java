package com.example.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.form.ArticleForm;
import com.example.service.ArticleService;

@Controller
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private ArticleService service;

	@ModelAttribute
	public ArticleForm setUpArticleForm() {
		return new ArticleForm();
	}

	@RequestMapping("")
	public String index(Model model) {
		List<Article> articleList = service.findAll();
		model.addAttribute("articleList", articleList);
		return "index";
	}

	@RequestMapping("/receive")
	public String receive(@Validated ArticleForm articleForm, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return index(model);
		}
		Article article = new Article();
		BeanUtils.copyProperties(articleForm, article);
		// DBへのInsert
		article = service.insert(article);
		return "redirect:/article";
	}
}
