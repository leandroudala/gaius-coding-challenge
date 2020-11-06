package app.udala.pos.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import app.udala.pos.model.Product;

@Service
public class ProductService {
	@Value("${app.wiremock.url}")
	private String wiremockUrl;

	private Logger log = LoggerFactory.getLogger(ProductService.class);

	public Product[] listProducts() {
		log.info("Listing all items");
		String url = wiremockUrl + "/products";
		
		RestTemplate restTemplate = new RestTemplate();
		Product[] items = restTemplate.getForObject(url, Product[].class);

		if (items == null) {
			items = new Product[0];
			log.error("No products found at '{}'", url);
		}

		return items;
	}
	
	public Product getProduct(String id) {
		log.info("Get product by id '{}'", id);
		String url = wiremockUrl + "/products/" + id;
		
		RestTemplate restTemplate = new RestTemplate();

		return restTemplate.getForObject(url, Product.class);
	}
}
