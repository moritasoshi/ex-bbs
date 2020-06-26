package com.example.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.repository.ArticleRepository;
import com.example.repository.CommentRepository;

@Service
@Transactional
public class ArticleService {

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private CommentRepository commentRepository;

	public Map<Article, List<Comment>> findAll() {
		List<Article> articleList = articleRepository.findAll();
		Map<Article, List<Comment>> tableMap = new LinkedHashMap<>();
		for (Article article : articleList) {
			Integer articleId = article.getId();
			List<Comment> commentList = commentRepository.findByArticleId(articleId);
			tableMap.put(article, commentList);
		}
		return tableMap;
	}

	public List<Comment> findByArticleId(Integer articleId) {
		return commentRepository.findByArticleId(articleId);
	}

	public Article articleInsert(Article article) {
		return articleRepository.insert(article);
	}

	public Comment commentInsert(Comment comment) {
		return commentRepository.insert(comment);
	}
	
	public void deleteArticleAndComment(Integer articleId) {
		commentRepository.deleteByArticleId(articleId);
		articleRepository.deleteById(articleId);
	}
}
