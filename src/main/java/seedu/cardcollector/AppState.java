package seedu.cardcollector;

public class AppState {
    private final CardsList inventory;
    private final CardsList wishlist;

    public AppState(CardsList inventory, CardsList wishlist) {
        this.inventory = inventory;
        this.wishlist = wishlist;
    }

    public CardsList getInventory() {
        return inventory;
    }

    public CardsList getWishlist() {
        return wishlist;
    }
}
