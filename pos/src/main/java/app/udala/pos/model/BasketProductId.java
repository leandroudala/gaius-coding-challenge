package app.udala.pos.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class BasketProductId implements Serializable {
	private static final long serialVersionUID = 1L;

	private String productId;
	private Long basketId;

	public BasketProductId(String productId, Long basketId) {
		super();
		this.productId = productId;
		this.basketId = basketId;
	}

	public BasketProductId() {
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Long getBasketId() {
		return basketId;
	}

	public void setBasketId(Long basketId) {
		this.basketId = basketId;
	}

	public boolean equals(Object obj) {
		if (obj instanceof BasketProductId) {
			BasketProductId other = (BasketProductId) obj;
			return other.basketId == this.basketId && other.productId == this.productId;
		}
		return false;
	}

	public int hashCode() {
		return productId.hashCode() + basketId.hashCode();
	}

}
