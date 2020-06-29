package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;
import com.example.domain.ArticleAndComment;

@Repository
public class ArticleRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<ArticleAndComment> ARTICLEANDCOMMENT_ROW_MAPPER = (rs, i) -> {
		ArticleAndComment articleAndComment = new ArticleAndComment();
		articleAndComment.setId(rs.getInt("id"));
		articleAndComment.setName(rs.getString("name"));
		articleAndComment.setContent(rs.getString("content"));
		articleAndComment.setCommentId(rs.getInt("comment_id"));
		articleAndComment.setCommentName(rs.getString("comment_name"));
		articleAndComment.setCommentContent(rs.getString("comment_content"));
		articleAndComment.setArticleId(rs.getInt("article_id"));
		return articleAndComment;
	};

	/**
	 * findAllメソッド
	 * 
	 * @return
	 */
	public List<ArticleAndComment> findAll() {
		//
		String sql = "SELECT a.id AS id, a.name AS name, a.content AS content,"
				+ " c.id AS comment_id, c.name AS comment_name, c.content AS comment_content, c.article_id AS article_id"
				+ " FROM articles AS a" + " LEFT JOIN comments AS c ON  a.id = c.article_id"
				+ " ORDER BY comment_id DESC";
		List<ArticleAndComment> articleList = template.query(sql, ARTICLEANDCOMMENT_ROW_MAPPER);
		articleList.forEach(System.out::println);
		return articleList;
	}

	/**
	 * Insertメソッド
	 * 
	 * @param article
	 */
	public Article insert(Article article) {
		String sql = "INSERT INTO articles(name, content) VALUES(:name, :content)";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", article.getName()).addValue("content",
				article.getContent());
		template.update(sql, param);
		return article;
	}

	/**
	 * deleteByIdメソッド
	 * 
	 * @param article
	 */
	public void deleteById(Integer id) {
		String sql = "DELETE FROM articles WHERE id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}

}
