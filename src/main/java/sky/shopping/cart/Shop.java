package sky.shopping.cart;

import sky.shopping.cart.model.Item;
import sky.shopping.cart.model.ItemType;
import sky.shopping.cart.model.Promotion;
import sky.shopping.cart.model.PromotionType;

import java.util.ArrayList;
import java.util.List;

public class Shop {

    public static List<Item> availableProducts = new ArrayList<Item>(){{
        Item product = new Item();
        product.setName("Headphones");
        product.setPrice(150.00);
        product.setType(ItemType.AUDIO);
        add(product);

        product = new Item();
        product.setName("Speakers");
        product.setPrice(85.00);
        product.setType(ItemType.AUDIO);
        add(product);

        product = new Item();
        product.setName("AAA Batteries");
        product.setPrice(0.85);
        product.setType(ItemType.POWER);
        add(product);

        product = new Item();
        product.setName("Protein Bars (Box)");
        product.setPrice(25.00);
        product.setType(ItemType.FOOD);
        add(product);
    }};

    public static List<Promotion> promotions = new ArrayList<Promotion>(){{
        Promotion promotion = new Promotion();
        promotion.setItemType(ItemType.AUDIO);
        promotion.setPromotionType(PromotionType.DISCOUNT_ON_PRICE);
        promotion.setValue(30);
        add(promotion);

        promotion = new Promotion();
        promotion.setItemType(ItemType.POWER);
        promotion.setItemName("AAA Batteries");
        promotion.setPromotionType(PromotionType.THREE_FOR_TWO);
        add(promotion);
    }};

    public static Item findProduct(String name){
        return availableProducts.stream().filter(i -> i.getName().equals(name)).findFirst().orElse(null);
    }

    public static void main(String[] args) throws Exception {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setPromotions(promotions);
        shoppingCart.setAvailableProducts(availableProducts);

        shoppingCart.add(findProduct("Speakers"), 1);
        shoppingCart.add(findProduct("AAA Batteries"), 5);
        shoppingCart.add(findProduct("Protein Bars (Box)"), 2);

        shoppingCart.show();
    }

}
