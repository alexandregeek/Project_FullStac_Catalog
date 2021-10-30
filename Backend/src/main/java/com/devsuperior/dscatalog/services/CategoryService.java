package com.devsuperior.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

@Service // registrar classe CategoryService como um componente, que partisipa no sistema
			// de injeção ,o spring qu eautomatiza a injeç~çao de depondencias

	public class CategoryService {
	@Autowired //para gerenciar a auto injeção de dependencia
	private CategoryRepository repository; // para injeção de dependencia,variavel repository
	@Transactional (readOnly = true)
	public Page<CategoryDTO> findAllPaged(PageRequest pageRequest) { // metodo precisa d dependencia Reposy
		Page<Category> list = repository.findAll(pageRequest);//super tipo 
		//expresão lambda
		return list.map(x-> new CategoryDTO(x));
	}
	
	
@Transactional(readOnly =true)
	public CategoryDTO findById(Long id) {
	//objeto optinal para trabalhar com valor nulo
	Optional <Category> obj = repository.findById(id);
	Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Not Found Exception"));
	return new CategoryDTO(entity);
	}
@Transactional
   public CategoryDTO insert(CategoryDTO dto) {
	Category entity = new Category();
	entity.setName(dto.getName());
	entity = repository.save(entity);
	return new CategoryDTO(entity);
   }
@Transactional
public CategoryDTO update(Long id,CategoryDTO dto) {
	 try {
		 Category entity = repository.getOne(id);
		 entity.setName(dto.getName());
		 entity = repository.save(entity);
		 return new CategoryDTO(entity);
		 
	 }
	 catch (EntityNotFoundException e) {
		 throw new ResourceNotFoundException("Id not found" + id);

    }
	
  }

public void delete(Long id) {
	
	try {
		repository.deleteById(id);
	  	}
	catch(EmptyResultDataAccessException e){
		throw new ResourceNotFoundException("Id not found" + id);
		}
	catch(DataIntegrityViolationException e){
		throw new DatabaseException("Integrity violation");
		}
    }
}


