package app.udala.pos.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.validation.constraints.Min;

@Entity
public class BasketProduct {

	@EmbeddedId
	private BasketProductId id = new BasketProductId();

	@ManyToOne
	@MapsId("productId")
	private Product product;

	@ManyToOne
	@MapsId("basketId")
	private Basket basket;

	@Min(1)
	@Column(nullable = false)
	private int qty = 1;
	
	public BasketProduct() {
	}

	public BasketProduct(Product product, Basket basket) {
		this.product = product;
		this.basket = basket;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int addOne() {
		return ++this.qty;
	}

	public Basket getBasket() {
		return basket;
	}

	public void setBasket(Basket basket) {
		this.basket = basket;
	}

	public BasketProductId getId() {
		return id;
	}

	public void setId(BasketProductId id) {
		this.id = id;
	}

}
