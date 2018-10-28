package sky.shopping.cart.exception;

public class ItemDoesNotExist extends Exception {

    public  ItemDoesNotExist() {
        super("Item does not exist in available product list");
    }
}
