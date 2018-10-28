package sky.shopping.cart;

import sky.shopping.cart.exception.ItemDoesNotExist;
import sky.shopping.cart.model.Item;
import sky.shopping.cart.model.Promotion;

import java.util.*;

public class ShoppingCart {

    private Map<Item, Integer> items = new HashMap<>();
    private List<Item> availableProducts = new ArrayList<>();
    private List<Promotion> promotions = new ArrayList<>();

    private void validation(String name) throws ItemDoesNotExist {
        Optional<Item> item = availableProducts.stream().filter(i -> i.getName().equals(name)).findFirst();
        if (!item.isPresent()) {
            throw new ItemDoesNotExist();
        }
    }

    private Double getPriceAfterPromotion(Item item, Integer quantity) {
        return promotions.stream().filter(p -> {
            switch (p.getPromotionType()) {
                case DISCOUNT_ON_PRICE:
                    return p.getItemType().equals(item.getType());
                case THREE_FOR_TWO:
                    return p.getItemType().equals(item.getType()) && p.getItemName().equals(item.getName());
                default:
                    return false;
            }
        }).map(p -> {
            switch (p.getPromotionType()) {
                case DISCOUNT_ON_PRICE:
                    Double priceBeforeDiscount = item.getPrice() * quantity;
                    Integer percentageAfterDiscount = 100 - p.getValue();
                    return priceBeforeDiscount * percentageAfterDiscount / 100;
                case THREE_FOR_TWO:
                    Integer quantityAfterDiscount = quantity - new Integer(quantity / 3);
                    return item.getPrice() * quantityAfterDiscount;
                default:
                    return item.getPrice() * quantity;
            }
        }).mapToDouble(Double::doubleValue).findFirst().orElse(getPriceBeforePromotion(item, quantity));
    }

    private Double getPriceBeforePromotion(Item item, Integer quantity) {
        return item.getPrice() * quantity;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public void setAvailableProducts(List<Item> availableProducts) {
        this.availableProducts = availableProducts;
    }

    public void add(Item item, Integer quantity) throws ItemDoesNotExist {
        validation(item.getName());

        Integer itemQuantity = items.get(item);
        if (itemQuantity != null) {
            items.put(item, quantity + itemQuantity);
        } else {
            items.put(item, quantity);
        }
    }

    public int getTotalItems() {
        return items.values().stream().reduce(0, Integer::sum);
    }

    public Double getTotalPrice() {
        return items.keySet().stream().map(i -> getPriceAfterPromotion(i, items.get(i))).mapToDouble(Double::doubleValue).sum();
    }

    public Double getTotalPriceWithoutDiscount() {
        return items.keySet().stream().map(i -> getPriceBeforePromotion(i, items.get(i))).mapToDouble(Double::doubleValue).sum();
    }

    public void show() {
        System.out.println("Shopping cart -> ");
        items.keySet().stream().forEach(i -> {
            String name = i.getName();
            Integer quantity = items.get(i);
            Double price = getPriceBeforePromotion(i, quantity);
            Double priceAfterDiscount = getPriceAfterPromotion(i, quantity);
            System.out.println(String.format("-- %s x %d, price: %f after discount %f", name, quantity, price, priceAfterDiscount));
        });
        System.out.println(String.format("Total: %f after discount %f", getTotalPriceWithoutDiscount(), getTotalPrice()));
        System.out.println("#################");
    }

}
