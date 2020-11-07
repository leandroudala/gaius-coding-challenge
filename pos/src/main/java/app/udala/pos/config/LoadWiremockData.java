package app.udala.pos.config;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.udala.pos.model.Product;
import app.udala.pos.repository.ProductRepository;
import app.udala.pos.repository.PromotionRepository;
import app.udala.pos.service.ProductService;

@Component
public class LoadWiremockData {
	Logger log = LoggerFactory.getLogger(LoadWiremockData.class);

	@Autowired
	private ProductService service;

	@Autowired
	private PromotionRepository promotionRepository;

	@Autowired
	private ProductRepository repository;

	@PostConstruct
	@Transactional
	public void init() throws InterruptedException {
		log.info("===== Loading data from Wiremock ======");

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
		Thread.sleep(5000);
	}
}
