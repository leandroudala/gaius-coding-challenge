package app.udala.pos.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Basket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private User user;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BasketStatus status = BasketStatus.PENDING;

	@OneToMany(mappedBy = "basket")
	private List<BasketProduct> products = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BasketStatus getStatus() {
		return status;
	}

	public void setStatus(BasketStatus status) {
		this.status = status;
	}

	public List<BasketProduct> getProducts() {
		return products;
	}

	public void setProducts(List<BasketProduct> products) {
		this.products = products;
	}

	public void addProduct(BasketProduct product) {
		this.products.add(product);
	}

}
