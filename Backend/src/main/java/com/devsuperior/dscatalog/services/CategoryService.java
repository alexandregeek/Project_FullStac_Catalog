package com.devsuperior.dscatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;

@Service // registrar classe CategoryService como um componente, que partisipa no sistema
			// de injeção ,o spring qu eautomatiza a injeç~çao de depondencias

public class CategoryService {
	@Autowired //para gerenciar a auto injeção de dependencia
	private CategoryRepository repository; // para injeção de dependencia,variavel repository

	public List<Category> findAll() { // metodo precisa d dependencia Reposy
		return repository.findAll();
	}
}