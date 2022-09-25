package com.sip.ams.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;

import com.sip.ams.entities.Article;


public interface ArticleRepository extends JpaRepository<Article, Long>{

	
	public Page<Article> findByLabelContains(String mc,Pageable pageable);
}
