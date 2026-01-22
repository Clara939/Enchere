package fr.eni.enchere.repository;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.repository.rowMapper.ArticleRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleRepositorySQL implements ArticleRepository{
@Autowired
JdbcTemplate jdbcTemplate;
@Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Article> readAll() {
        return List.of();
    }

    @Override
    public void create(Article article) {

    }

    @Override
    public Categorie readById(long id) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void update(Article article) {

    }
}
