package app.udala.pos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import app.udala.pos.controller.dto.ProductDto;

@Entity
@JsonInclude(Include.NON_NULL)
public class Promotion {
	@Id
	@Length(min = 1, max = 10)
	@JsonProperty("id")
	private String id;

	@Enumerated(EnumType.STRING)
	@JsonProperty("type")
	private PromotionType type;

	@JsonProperty("required_qty")
	private int requiredQty;

	@JsonProperty("free_qty")
	private int freeQty;

	@JsonProperty("amount")
	private int amount;

	@Column(nullable = false)
	@JsonProperty("price")
	private int price;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PromotionType getType() {
		return type;
	}

	public void setType(PromotionType type) {
		this.type = type;
	}

	public int getRequiredQty() {
		return requiredQty;
	}

	public void setRequiredQty(int requiredQty) {
		this.requiredQty = requiredQty;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getFreeQty() {
		return freeQty;
	}

	public void setFreeQty(int freeQty) {
		this.freeQty = freeQty;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return String.format("Promotion{id: %s, type: %s, required_qty: %d, free_qty: %d, amount: %d, price: %d}",
				this.id, this.type, this.requiredQty, this.freeQty, this.amount, this.price);
	}

	public int calculateValueWithPromo(ProductDto product, int qty) {
		int total = 0;

		switch (type) {
		case QTY_BASED_PRICE_OVERRIDE:
			total = qtyBasedPriceOverride(product, qty);
			break;

		case BUY_X_GET_Y_FREE:
			total = buyXGetYFree(product, qty);
			break;

		case FLAT_PERCENT:
			total = flatPercent(product, qty);
			break;
		}

		return total;
	}

	private int flatPercent(ProductDto product, int qty) {
		int totalPrice = product.getPrice() * qty;
		return totalPrice - totalPrice / this.amount;
	}

	private int buyXGetYFree(ProductDto product, int qty) {
		if (qty >= this.requiredQty + this.freeQty) {
			int groups = qty / (this.requiredQty + this.freeQty);
			int needPay = groups * requiredQty;
			return needPay * product.getPrice();
		}
		
		return 0;
	}

	private int qtyBasedPriceOverride(ProductDto product, int qty) {
		if (requiredQty <= qty) {
			int outOfDiscount = qty % this.requiredQty;
			int qtyOverride = (qty - outOfDiscount) / this.requiredQty;
			return (product.getPrice() * outOfDiscount) + (this.price * qtyOverride);
		}

		return 0;
	}
	
	public Promotion() {
		
	}
	public Promotion(@Length(min = 1, max = 10) String id, PromotionType type, int requiredQty, int freeQty, int amount,
			int price) {
		super();
		this.id = id;
		this.type = type;
		this.requiredQty = requiredQty;
		this.freeQty = freeQty;
		this.amount = amount;
		this.price = price;
	}
	
	

}
