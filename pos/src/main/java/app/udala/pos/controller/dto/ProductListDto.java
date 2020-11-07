package app.udala.pos.controller.dto;

import app.udala.pos.model.Product;

public class ProductListDto {
	private String id;
	private String name;
	private Integer price;

	public ProductListDto(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.price = product.getPrice();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getPrice() {
		return price;
	}
}
