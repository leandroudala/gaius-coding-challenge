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

	public int calculatePromo(Product product) {
		int total = 0;
		int qty = product.getQty();

		switch (type) {
		case QTY_BASED_PRICE_OVERRIDE:
			if (qty >= 2)
				total = this.price * qty;
			else
				total = product.getPrice() * qty;
			break;

		case BUY_X_GET_Y_FREE:
			int totalItemsDiscount = this.requiredQty + this.freeQty;
			total = product.getPrice() * (qty - totalItemsDiscount);
			break;

		case FLAT_PERCENT:
			total = (product.getPrice() * qty) / this.amount;
			break;
		}

		return total;
	}

}
