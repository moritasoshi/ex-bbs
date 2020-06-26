package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;

@Repository
public class ArticleRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Article> ARTICLE_ROW_MAPPER = (rs, i) -> {
		Article article = new Article();
		article.setId(rs.getInt("id"));
		article.setName(rs.getString("name"));
		article.setContent(rs.getString("content"));
		return article;
	};

	/**
	 * loadメソッド
	 * 
	 * @param id
	 * @return
	 */
	public Article load(Integer id) {
		String sql = "SELECT * FROM articles WHERE id=:id ORDER BY id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		return template.queryForObject(sql, param, ARTICLE_ROW_MAPPER);
	}

	/**
	 * findAllメソッド
	 * 
	 * @return
	 */
	public List<Article> findAll() {
		String sql = "SELECT * FROM articles ORDER BY id";
		List<Article> articleList = template.query(sql, ARTICLE_ROW_MAPPER);
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

}
