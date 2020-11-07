package app.udala.pos.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import app.udala.pos.controller.dto.BasketDto;
import app.udala.pos.controller.dto.CheckoutDto;
import app.udala.pos.model.Basket;
import app.udala.pos.model.BasketProduct;
import app.udala.pos.model.Product;
import app.udala.pos.model.User;
import app.udala.pos.repository.BasketProductRepository;
import app.udala.pos.repository.BasketRepository;
import app.udala.pos.repository.ProductRepository;
import app.udala.pos.repository.UserRepository;

@RestController
@RequestMapping("/basket")
public class BasketController {
	Logger log = LoggerFactory.getLogger(BasketController.class);

	@Autowired
	private BasketRepository repository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BasketProductRepository basketProductRepository;

	@PostMapping
	@Transactional
	public ResponseEntity<Basket> createBasket(UriComponentsBuilder uriBuilder) {
		// creating a default user and adding to a new basket
		User user = new User("Client");
		userRepository.save(user);

		Basket basket = new Basket();
		basket.setUser(user);

		repository.save(basket);

		// send basket URI to client
		URI uri = uriBuilder.path("/basket/{id}").buildAndExpand(basket.getId()).toUri();

		return ResponseEntity.created(uri).body(basket);
	}

	@GetMapping("/{id}")
	public ResponseEntity<BasketDto> getBasket(@PathVariable Long id) {
		Optional<Basket> optionalBasket = repository.findById(id);

		if (optionalBasket.isEmpty()) {
			log.error("Basket not found! Basket id: {}", id);
			return ResponseEntity.notFound().build();
		}
		BasketDto basketDto = new BasketDto(optionalBasket.get());

		return ResponseEntity.ok(basketDto);
	}

	@Transactional
	@PostMapping("/{basketId}/{productId}")
	public ResponseEntity<BasketDto> addItems(@PathVariable Long basketId, @PathVariable String productId) {
		log.info("Checking if basket exists");
		Optional<Basket> optBasket = repository.findById(basketId);
		if (!optBasket.isPresent()) {
			log.error("Basket '{}' not found!", basketId);
			return ResponseEntity.notFound().build();
		}

		log.info("Check if product id exists");
		Optional<Product> optProduct = productRepository.findById(productId);
		if (optProduct.isEmpty()) {
			log.error("Product not registered! Id: '{}'", productId);
			return ResponseEntity.notFound().build();
		}

		Product product = optProduct.get();
		Basket basket = optBasket.get();

		log.info("Check if item '{}' was already added (Id: {})", product.getName(), product.getId());
		Optional<BasketProduct> productFound = basket.getProducts().stream()
				.filter(basketProduct -> basketProduct.getProduct().getId().equals(productId)).findFirst();

		if (productFound.isPresent()) {
			productFound.get().addOne();
		} else {
			BasketProduct basketProduct = new BasketProduct(product, basket);
			basketProductRepository.save(basketProduct);
			basket.addProduct(basketProduct);
		}

		return ResponseEntity.ok(new BasketDto(basket));
	}

	@PostMapping("/{basketId}/checkout")
	public ResponseEntity<CheckoutDto> checkout(@PathVariable Long basketId) {
		log.info("Checking if basket exists");
		Optional<Basket> optBasket = repository.findById(basketId);
		if (!optBasket.isPresent()) {
			log.error("Basket '{}' not found", basketId);
			return ResponseEntity.notFound().build();
		}
		Basket basket = optBasket.get();
		CheckoutDto checkout = new CheckoutDto(basket);
		checkout.doCheckout();

		return ResponseEntity.ok(checkout);
	}
}
