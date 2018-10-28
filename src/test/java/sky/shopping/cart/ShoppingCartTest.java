package sky.shopping.cart;

import org.junit.Before;
import org.junit.Test;
import sky.shopping.cart.exception.ItemDoesNotExist;
import sky.shopping.cart.model.Item;
import sky.shopping.cart.model.ItemType;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ShoppingCartTest {

    ShoppingCart cart;

    @Before
    public void setUp() throws Exception {
        cart = new ShoppingCart();
        cart.setAvailableProducts(Shop.availableProducts);
        cart.setPromotions(Shop.promotions);
    }

    @Test
    public void getTotalItems() throws ItemDoesNotExist {
        Item item = Shop.findProduct("Speakers");
        cart.add(item, 1);
        item = Shop.findProduct("AAA Batteries");
        cart.add(item, 5);
        item = Shop.findProduct("Protein Bars (Box)");
        cart.add(item, 2);
        assertThat(cart.getTotalItems(), is(8));
    }

    @Test(expected = ItemDoesNotExist.class)
    public void addItemToCartWhichDoesNotExist() throws ItemDoesNotExist {
        Item item = new Item();
        item.setName("Test");
        item.setPrice(10.00);
        cart.add(item, 10);
    }

    @Test
    public void getTotalPriceAfterDiscount() throws ItemDoesNotExist {
        Item item = Shop.findProduct("Speakers");
        cart.add(item, 1);
        item = Shop.findProduct("AAA Batteries");
        cart.add(item, 5);
        item = Shop.findProduct("Protein Bars (Box)");
        cart.add(item, 2);
        assertThat(cart.getTotalPrice(), is(112.9));
    }

    @Test
    public void getTotalPriceBeforeDiscount() throws ItemDoesNotExist {
        Item item = Shop.findProduct("Speakers");
        cart.add(item, 1);
        item = Shop.findProduct("AAA Batteries");
        cart.add(item, 5);
        item = Shop.findProduct("Protein Bars (Box)");
        cart.add(item, 2);
        assertThat(cart.getTotalPriceWithoutDiscount(), is(139.25));
    }

}