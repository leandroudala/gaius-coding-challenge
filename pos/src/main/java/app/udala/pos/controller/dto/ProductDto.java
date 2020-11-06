package app.udala.pos.controller.dto;

import java.util.List;

import app.udala.pos.model.BasketProduct;
import app.udala.pos.model.Product;
import app.udala.pos.model.Promotion;

public class ProductDto {
	private String id;
	private String name;
	private int price;
	private List<Promotion> promotions;
	private int qtd;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public List<Promotion> getPromotions() {
		return promotions;
	}

	public int getQtd() {
		return qtd;
	}

	public ProductDto(BasketProduct basket) {
		Product product = basket.getProduct();
		this.id = product.getId();
		this.name = product.getName();
		this.price = product.getPrice();
		this.promotions = product.getPromotions();
		this.qtd = basket.getQty();
	}

}
