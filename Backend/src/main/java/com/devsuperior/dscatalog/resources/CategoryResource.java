package com.devsuperior.dscatalog.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.services.CategoryService;

@RestController
@RequestMapping(value ="/categories")//rota rest
public class CategoryResource {// metodo 
	@Autowired //para injeção automatizada
	private CategoryService service;
	@GetMapping
	public ResponseEntity<Page<CategoryDTO>> findAll(
			
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy	
			){
		PageRequest pageRequest = PageRequest.of(page,linesPerPage,Direction.valueOf(direction), orderBy);
		
		
		//retorno do metodo de pesponsit encapsulamenla a resposta http 
		//List<Category> list = new ArrayList<>();//declara e instaciar uma lista
		//list.add(new Category(1L,"Books")); // adicionando um novo objeto na categoria na lista (l de long|)
		//list.add( new Category(2L,"eletronicos"));//inclui 
		
		Page<CategoryDTO> list = service.findAllPaged(pageRequest);
	
		
		return ResponseEntity.ok().body(list);// 
	}
	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> findById(@PathVariable Long id){//retorno do metodo de pesponsit encapsulamenla a resposta http 
		
		CategoryDTO dto = service.findById(id);
	return ResponseEntity.ok().body(dto);// 
	}
	
	@PostMapping
	public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
		    		.buildAndExpand(dto.getId()).toUri();	
	  return ResponseEntity.created(uri).body(dto);
		
	}
	@PutMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> update(@PathVariable Long id,@RequestBody CategoryDTO dto){
		dto = service.update (id,dto);
		return ResponseEntity.ok().body(dto);// 
	}
	@DeleteMapping(value = "/{id}")//buscar so pelo id
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.delete (id);
		return ResponseEntity.noContent().build();// respsosta não precisa de corpo ,,corpo da resposta 204
	}
	
}
