package app.udala.pos.controller.dto;

import java.util.ArrayList;
import java.util.List;

import app.udala.pos.model.Basket;
import app.udala.pos.model.BasketStatus;

public class BasketDto {
	private Long id;
	private BasketStatus status;
	private List<ProductDto> products;

	public Long getId() {
		return id;
	}

	public BasketStatus getStatus() {
		return status;
	}

	public List<ProductDto> getProducts() {
		return products;
	}

	public BasketDto(Basket basket) {
		this.products = new ArrayList<>();
		this.id = basket.getId();
		this.status = basket.getStatus();
		basket.getProducts().stream().forEach(basketProduct -> products.add(new ProductDto(basketProduct)));
	}

}
