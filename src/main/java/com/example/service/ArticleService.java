package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Article;
import com.example.repository.ArticleRepository;

@Service
@Transactional
public class ArticleService {

	@Autowired
	private ArticleRepository repository;

	public List<Article> findAll() {
		return repository.findAll();
	}

	public Article insert(Article article) {
		return repository.insert(article);
	}
}
