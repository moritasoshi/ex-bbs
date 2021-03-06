package com.example.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;
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
	
	@ModelAttribute
	public CommentForm setUpCommentForm() {
		return new CommentForm();
	}
	

	@RequestMapping("")
	public String index(Model model) {
		Map<Article, List<Comment>> tableMap = service.findAll();
		model.addAttribute("tableMap", tableMap);
		
		return "index";
	}

	@RequestMapping("/article-receive")
	public String articleReceive(@Validated ArticleForm articleForm, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return index(model);
		}
		
		// もとのコード
		Article article = new Article();
		BeanUtils.copyProperties(articleForm, article);
		// DBへのInsert
		article = service.articleInsert(article);
		return "redirect:/article";
	}
	
	@RequestMapping("/comment-receive")
	public String commentReceive(@Validated CommentForm commentForm, BindingResult result, Integer articleId, Model model) {
		System.out.println(result);
		if(result.hasErrors()) {
			commentForm.setArticleId(articleId);
			return index(model);
		}
		Comment comment = new Comment();
		BeanUtils.copyProperties(commentForm, comment);
		comment.setArticleId(articleId);
		// DBへのInsert
		comment = service.commentInsert(comment);
		return "redirect:/article";
	}
	
	@RequestMapping("/delete")
	public String delete(Integer id) {
		service.deleteArticleAndComment(id);
		return "redirect:/article";
	}
}
