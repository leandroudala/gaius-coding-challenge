package app.udala.pos.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Product {
	@Id
	@Length(max = 10, min = 1)
	@JsonProperty("id")
	private String id;

	@JsonProperty("name")
	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	@Min(1)
	@JsonProperty("price")
	private int price;

	@OneToMany
	@JsonProperty("promotions")
	private List<Promotion> promotions;

	public String toString() {
		return String.format("Item{id: %s, name: %s, price: %d, promotions: [%s]}", id, name, price, promotions);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public List<Promotion> getPromotions() {
		return promotions;
	}

	public void setPromotions(List<Promotion> promotions) {
		this.promotions = promotions;
	}
}
