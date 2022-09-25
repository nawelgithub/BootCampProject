package com.sip.ams.repositories;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sip.ams.entities.Article;
import com.sip.ams.entities.Provider;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {

	
	@Query("FROM Article a WHERE a.provider.id = ?1")  
	List<Article> findArticlesByProvider(long id);
	
	
	public Page<Provider> findByNameContains(String mc,Pageable pageable);
}