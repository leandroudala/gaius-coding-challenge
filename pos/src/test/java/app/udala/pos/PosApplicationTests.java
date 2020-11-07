package app.udala.pos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import app.udala.pos.controller.dto.CheckoutDto;
import app.udala.pos.model.Basket;
import app.udala.pos.model.BasketProduct;
import app.udala.pos.model.Product;
import app.udala.pos.model.Promotion;
import app.udala.pos.model.PromotionType;
import app.udala.pos.model.User;

@SpringBootTest
class PosApplicationTests {

	private Product amazingPizza, amazingBurger, boringFries, amazingSalad;

	private Basket basket;
	
	// DELTA is an acceptable variation of result
	private Integer DELTA = 1;

	@BeforeEach
	void setup() {
		amazingPizza = new Product("Dwt5F7KAhi", "Amazing Pizza!", 1099,
				Lists.list(new Promotion("ibt3EEYczW", PromotionType.QTY_BASED_PRICE_OVERRIDE, 2, 0, 0, 1799)));

		amazingBurger = new Product("PWWe3w1SDU", "Amazing Burger!", 999,
				Lists.list(new Promotion("ZRAwbsO2qM", PromotionType.BUY_X_GET_Y_FREE, 2, 1, 0, 0)));

		amazingSalad = new Product("C8GDyLrHJb", "Amazing Salad!", 499,
				Lists.list(new Promotion("Gm1piPn7Fg", PromotionType.FLAT_PERCENT, 0, 0, 10, 0)));

		boringFries = new Product("4MB7UfpTQs", "Boring Fries!", 199, Lists.emptyList());

		basket = new Basket();
		basket.setUser(new User("Test User"));
	}

	@Test
	void testExample01() {
		BasketProduct amazingPizzaX1 = new BasketProduct(amazingPizza, basket);
		BasketProduct amazingBurgerX1 = new BasketProduct(amazingBurger, basket);
		BasketProduct boringFriesX1 = new BasketProduct(boringFries, basket);

		basket.addProduct(amazingPizzaX1);
		basket.addProduct(amazingBurgerX1);
		basket.addProduct(boringFriesX1);

		CheckoutDto checkout = new CheckoutDto(basket);
		checkout.doCheckout();

		assertEquals(2297, checkout.getRawTotal());
		assertEquals(0, checkout.getTotalPromo());
		assertEquals(2297, checkout.getTotalPayable());
	}

	@Test
	void testExample02() {
		BasketProduct amazingPizzaX1 = new BasketProduct(amazingPizza, basket);
		BasketProduct amazingBurgerX1 = new BasketProduct(amazingBurger, basket);
		BasketProduct boringFriesX1 = new BasketProduct(boringFries, basket);
		BasketProduct amazingSaladX1 = new BasketProduct(amazingSalad, basket);

		basket.addProduct(amazingPizzaX1);
		basket.addProduct(amazingBurgerX1);
		basket.addProduct(boringFriesX1);
		basket.addProduct(amazingSaladX1);

		CheckoutDto checkout = new CheckoutDto(basket);
		checkout.doCheckout();

		assertEquals(2796, checkout.getRawTotal());
		assertEquals(49, checkout.getTotalPromo());
		assertEquals(2747, checkout.getTotalPayable());
	}

	@Test
	void testExample03() {
		BasketProduct amazingPizzaX2 = new BasketProduct(amazingPizza, basket);
		amazingPizzaX2.setQty(2);
		BasketProduct boringFriesX1 = new BasketProduct(boringFries, basket);

		basket.addProduct(amazingPizzaX2);
		basket.addProduct(boringFriesX1);

		CheckoutDto checkout = new CheckoutDto(basket);
		checkout.doCheckout();

		assertEquals(2397, checkout.getRawTotal());
		assertEquals(399, checkout.getTotalPromo());
		assertEquals(1998, checkout.getTotalPayable());
	}

	@Test
	void testExample04() {
		BasketProduct amazingBurgerX3 = new BasketProduct(amazingBurger, basket);
		amazingBurgerX3.setQty(3);
		BasketProduct amazingSaladX1 = new BasketProduct(amazingSalad, basket);

		basket.addProduct(amazingBurgerX3);
		basket.addProduct(amazingSaladX1);

		CheckoutDto checkout = new CheckoutDto(basket);
		checkout.doCheckout();

		assertEquals(3496, checkout.getRawTotal());
		assertEquals(1048, checkout.getTotalPromo());
		assertEquals(2448, checkout.getTotalPayable());
	}


	@Test
	void testQtyBasedPriceOverrideNoDiscount() {
		BasketProduct amazingPizzaX1 = new BasketProduct(amazingPizza, basket);
		amazingPizzaX1.setQty(1);

		basket.addProduct(amazingPizzaX1);

		CheckoutDto checkout = new CheckoutDto(basket);
		checkout.doCheckout();

		assertEquals(1099, checkout.getRawTotal());
		assertEquals(0, checkout.getTotalPromo());
		assertEquals(1099, checkout.getTotalPayable());
	}

	@Test
	void testQtyBasedPriceOverrideDiscount() {
		BasketProduct amazingPizzaX2 = new BasketProduct(amazingPizza, basket);
		amazingPizzaX2.setQty(2);

		basket.addProduct(amazingPizzaX2);

		CheckoutDto checkout = new CheckoutDto(basket);
		checkout.doCheckout();

		assertEquals(2198, checkout.getRawTotal());
		assertEquals(399, checkout.getTotalPromo());
		assertEquals(1799, checkout.getTotalPayable());
	}

	@Test
	void testQtyBasedPriceOverrideOddDiscount() {
		BasketProduct amazingPizzaX3 = new BasketProduct(amazingPizza, basket);
		amazingPizzaX3.setQty(3);

		basket.addProduct(amazingPizzaX3);

		CheckoutDto checkout = new CheckoutDto(basket);
		checkout.doCheckout();

		assertEquals(3297, checkout.getRawTotal());
		assertEquals(399, checkout.getTotalPromo());
		assertEquals(2898, checkout.getTotalPayable());
	}

	@Test
	void testBuyXGetYFree() {
		BasketProduct amazingBurgerX3 = new BasketProduct(amazingBurger, basket);
		amazingBurgerX3.setQty(3);

		basket.addProduct(amazingBurgerX3);

		CheckoutDto checkout = new CheckoutDto(basket);
		checkout.doCheckout();

		assertEquals(2997, checkout.getRawTotal());
		assertEquals(999, checkout.getTotalPromo());
		assertEquals(1998, checkout.getTotalPayable());
	}

	@Test
	void testBuyXGetYFreeOdd() {
		BasketProduct amazingBurgerX3 = new BasketProduct(amazingBurger, basket);
		amazingBurgerX3.setQty(4);

		basket.addProduct(amazingBurgerX3);

		CheckoutDto checkout = new CheckoutDto(basket);
		checkout.doCheckout();

		assertEquals(3996, checkout.getRawTotal());
		assertEquals(999, checkout.getTotalPromo());
		assertEquals(2997, checkout.getTotalPayable());
	}

	@Test
	void testBuyXGetYFreeDoubleEven() {
		BasketProduct amazingBurgerX3 = new BasketProduct(amazingBurger, basket);
		amazingBurgerX3.setQty(6);

		basket.addProduct(amazingBurgerX3);

		CheckoutDto checkout = new CheckoutDto(basket);
		checkout.doCheckout();

		assertEquals(5994, checkout.getRawTotal());
		assertEquals(1998, checkout.getTotalPromo());
		assertEquals(3996, checkout.getTotalPayable());
	}

	@Test
	void testFlatPercent() {
		BasketProduct amazingSaladX1 = new BasketProduct(amazingSalad, basket);
		amazingSaladX1.setQty(1);

		basket.addProduct(amazingSaladX1);

		CheckoutDto checkout = new CheckoutDto(basket);
		checkout.doCheckout();

		assertEquals(499, checkout.getRawTotal());
		assertEquals(49, checkout.getTotalPromo(), DELTA);
		assertEquals(449, checkout.getTotalPayable(), DELTA);
	}
	
	@Test
	void testFlatPercentX2() {
		BasketProduct amazingSaladX2 = new BasketProduct(amazingSalad, basket);
		amazingSaladX2.setQty(2);

		basket.addProduct(amazingSaladX2);

		CheckoutDto checkout = new CheckoutDto(basket);
		checkout.doCheckout();

		assertEquals(998, checkout.getRawTotal());
		assertEquals(99, checkout.getTotalPromo(), DELTA);
		assertEquals(898, checkout.getTotalPayable(), DELTA);
	}
	
	@Test
	void testProductWithoutDiscount() {
		BasketProduct boringFriesX5 = new BasketProduct(boringFries, basket);
		boringFriesX5.setQty(5);

		basket.addProduct(boringFriesX5);

		CheckoutDto checkout = new CheckoutDto(basket);
		checkout.doCheckout();

		assertEquals(995, checkout.getRawTotal());
		assertEquals(0, checkout.getTotalPromo(), DELTA);
		assertEquals(995, checkout.getTotalPayable(), DELTA);
	}
}
