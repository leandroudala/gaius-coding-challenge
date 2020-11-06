package app.udala.pos.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	@GetMapping
	public ResponseEntity<Product[]> getItems() {
		Product[] items = service.listProducts();

		return ResponseEntity.ok(items);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Product> getItem(@PathVariable String id) {
		Product item = service.getProduct(id);
		if (item == null) {
			log.error("Item not found: '{}'", id);
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(item);
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
