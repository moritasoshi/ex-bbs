package com.example.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Article;
import com.example.domain.ArticleAndComment;
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

		List<ArticleAndComment> articleAndComments = articleRepository.findAll();

		Map<Article, List<Comment>> articleMap = new LinkedHashMap<>();

		List<Comment> commentList = new ArrayList<>();
		for (ArticleAndComment articleAndComment : articleAndComments) {
			Article article = new Article();
			article.setId(articleAndComment.getId());
			article.setName(articleAndComment.getName());
			article.setContent(articleAndComment.getContent());

			// すでに同じキーが存在する場合はmapから削除
			for (Article element : articleMap.keySet()) {
				if (element.getId() == article.getId()) {
					articleMap.remove(element);
				}
			}

			Comment comment = new Comment();
			comment.setId(articleAndComment.getCommentId());
			comment.setName(articleAndComment.getCommentName());
			comment.setContent(articleAndComment.getCommentContent());
			comment.setArticleId(articleAndComment.getArticleId());

			// 記事IDごとにcommentListを初期化
			if (!commentList.isEmpty()) {
				if (commentList.get(0).getArticleId() != comment.getArticleId()) {
					commentList = new ArrayList<>();
				}
			}

			if(comment.getId() != null) {
				commentList.add(comment);
			}
			articleMap.put(article, commentList);
		}

		return articleMap;
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