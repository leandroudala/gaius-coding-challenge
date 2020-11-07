package app.udala.pos.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.udala.pos.config.ResourceNotFoundException;
import app.udala.pos.controller.dto.ProductListDto;
import app.udala.pos.model.Product;
import app.udala.pos.repository.ProductRepository;
import app.udala.pos.repository.PromotionRepository;
import app.udala.pos.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	private Logger log = LoggerFactory.getLogger(ProductController.class);
	
	// retrieve products from api
	@Autowired
	private ProductService service;
	
	// retrieve products from database
	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private PromotionRepository promotionRepository;
	
	private static final String PRODUCT_NOT_FOUND = "Product not found";
	
	@GetMapping
	public ResponseEntity<List<ProductListDto>> getItems() {
		List<ProductListDto> products = repository.findAll().stream().map(ProductListDto::new).collect(Collectors.toList());
		return ResponseEntity.ok(products);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Product> getItem(@PathVariable String id) throws ResourceNotFoundException {
		Optional<Product> item = repository.findById(id);
		if (!item.isPresent()) {
			log.error("Item not found: '{}'", id);
			throw new ResourceNotFoundException(PRODUCT_NOT_FOUND);
		}
		
		return ResponseEntity.ok(item.get());
	}
	
	// load products from API
	@PostMapping
	public ResponseEntity<List<Product>> loadFromApi() {
		log.info("Retrieving products from external API");
		List<Product> products = List.of(service.listProducts());
		
		log.info("Products found: {}", products.size());
		products.stream().forEach(product -> {
			Product productDetails = service.getProduct(product.getId());
			log.info("Saving promotions");
			promotionRepository.saveAll(productDetails.getPromotions());
			
			product.setPromotions(productDetails.getPromotions());
			log.info("Saving product: {}", product);
			repository.save(product);
		});
		
		return ResponseEntity.ok(products);
	}
}
