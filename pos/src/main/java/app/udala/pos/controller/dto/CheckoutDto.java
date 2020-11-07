package app.udala.pos.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import app.udala.pos.model.Basket;
import app.udala.pos.model.User;

public class CheckoutDto {
	private Long id;
	private User client;
	private List<ProductDto> products;
	private double rawTotal;
	private double totalPromo;
	private double totalPayable;

	public CheckoutDto(Basket basket) {
		this.id = basket.getId();
		this.client = basket.getUser();
		this.products = basket.getProducts().stream().map(ProductDto::new).collect(Collectors.toList());
	}

	public Long getId() {
		return id;
	}

	public User getClient() {
		return client;
	}

	public List<ProductDto> getProducts() {
		return products;
	}

	public double getRawTotal() {
		return rawTotal;
	}

	public double getTotalPromo() {
		return totalPromo;
	}

	public double getTotalPayable() {
		return totalPayable;
	}

	public void doCheckout() {
		products.stream().forEach(product -> {
			int qty = product.getQtd();
			int gross = product.getPrice() * qty;

			this.rawTotal += product.getPrice() * qty;
			this.totalPayable += gross;
			
			product.getPromotions().stream().forEach(promo -> {
				// get total value with discount
				int net = promo.calculateValueWithPromo(product, qty);
				int discount = 0;
				
				if (net > 0)
					discount = gross - net;

				this.totalPromo += discount;
				this.totalPayable -= discount;
			});
		});
	}

}
