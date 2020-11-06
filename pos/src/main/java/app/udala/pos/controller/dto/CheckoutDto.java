package app.udala.pos.controller.dto;

import java.util.HashMap;
import java.util.Map;

import app.udala.pos.model.Basket;
import app.udala.pos.model.Product;
import app.udala.pos.model.User;

public class CheckoutDto {
	private Long id;
	private User client;
	private Map<String, Product> products;
	private int rawTotal;
	private int totalPromo;
	private int totalPayable;

	public CheckoutDto() {
		products = new HashMap<>();
		rawTotal = 0;
		totalPromo = 0;
		totalPayable = 0;
	}

	public CheckoutDto(Basket basket) {
		super();
		this.id = basket.getId();
		this.products = basket.getProducts();
		this.client = basket.getUser();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getClient() {
		return client;
	}

	public void setClient(User client) {
		this.client = client;
	}

	public Map<String, Product> getProducts() {
		return products;
	}

	public void setProducts(Map<String, Product> products) {
		this.products = products;
	}

	public double getRawTotal() {
		return rawTotal;
	}

	public int getTotalPromo() {
		return totalPromo;
	}

	public void setTotalPromo(int totalPromo) {
		this.totalPromo = totalPromo;
	}

	public int getTotalPayable() {
		return totalPayable;
	}

	public void setTotalPayable(int totalPayable) {
		this.totalPayable = totalPayable;
	}

	public void setRawTotal(int rawTotal) {
		this.rawTotal = rawTotal;
	}

	public void doCheckout() {
		products.forEach((productId, product) -> {
			this.rawTotal += product.getPrice() * product.getQty();
			product.getPromotions().stream().forEach(promo -> {
				this.totalPromo = promo.calculatePromo(product);
				this.totalPayable = this.rawTotal - this.totalPromo;
			});
		});
	}

}
