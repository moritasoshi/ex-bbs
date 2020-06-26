package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Comment;

@Repository
public class CommentRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Comment> COMMENT_ROW_MAPPER = (rs, i) -> {
		Comment comment = new Comment();
		comment.setId(rs.getInt("id"));
		comment.setName(rs.getString("name"));
		comment.setContent(rs.getString("content"));
		comment.setArticleId(rs.getInt("article_id"));
		return comment;
	};

	/**
	 * findByArticleIdメソッド
	 * 
	 * @return
	 */
	public List<Comment> findByArticleId(Integer articleId) {
		String sql = "SELECT * FROM comments WHERE article_id=:articleId ORDER BY id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
		List<Comment> commentList = template.query(sql, param, COMMENT_ROW_MAPPER);
		return commentList;
	}

//	/**
//	 * Insertメソッド
//	 * 
//	 * @param article
//	 */
//	public Article insert(Article article) {
//		String sql = "INSERT INTO articles(name, content) VALUES(:name, :content)";
//		SqlParameterSource param = new MapSqlParameterSource().addValue("name", article.getName()).addValue("content",
//				article.getContent());
//		template.update(sql, param);
//		return article;
//	}

}