package seedu.cardcollector;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardsListTest {

    @Test
    public void addCard_card_success() {
        CardsList cardsList = new CardsList();

        Card card = new Card.Builder()
                .name("Pikachu")
                .price(5.50f)
                .quantity(1)
                .build();
        cardsList.addCard(card);

        assertEquals(1, cardsList.getSize());
        assertEquals(card, cardsList.getCard(0));
    }

    @Test
    public void findCards_byName_success() {
        CardsList cardsList = new CardsList();

        cardsList.addCard(new Card.Builder()
                .name("Pikachu")
                .price(5.50f)
                .quantity(1)
                .build());
        cardsList.addCard(new Card.Builder()
                .name("Charizard")
                .price(15.00f)
                .quantity(2)
                .build());
        cardsList.addCard(new Card.Builder()
                .name("Pikachu VMAX")
                .price(20.00f)
                .quantity(3)
                .build());

        // Case-insensitive and partial match for "pika"
        ArrayList<Card> results = cardsList.findCards("pika", null, null);
        
        assertEquals(2, results.size());
        assertEquals("Pikachu", results.get(0).getName());
        assertEquals("Pikachu VMAX", results.get(1).getName());
    }

    @Test
    public void findCards_byPrice_success() {
        CardsList cardsList = new CardsList();

        cardsList.addCard(new Card.Builder()
                .name("Pikachu")
                .price(5.50f)
                .quantity(1)
                .build());
        cardsList.addCard(new Card.Builder()
                .name("Charizard")
                .price(10.00f)
                .quantity(2)
                .build());

        // Exact price match
        ArrayList<Card> results = cardsList.findCards(null, 10.00f, null);
        
        assertEquals(1, results.size());
        assertEquals("Charizard", results.get(0).getName());
    }

    @Test
    public void findCards_byQuantity_success() {
        CardsList cardsList = new CardsList();

        cardsList.addCard(new Card.Builder()
                .name("Bulbasaur")
                .price(2.00f)
                .quantity(5)
                .build());
        cardsList.addCard(new Card.Builder()
                .name("Squirtle")
                .price(3.00f)
                .quantity(1)
                .build());

        // Exact quantity match
        ArrayList<Card> results = cardsList.findCards(null, null, 5);
        
        assertEquals(1, results.size());
        assertEquals("Bulbasaur", results.get(0).getName());
    }

    @Test
    public void findCards_multipleAttributes_success() {
        CardsList cardsList = new CardsList();

        cardsList.addCard(new Card.Builder()
                .name("Mewtwo")
                .price(20.00f)
                .quantity(3)
                .build());
        cardsList.addCard(new Card.Builder()
                .name("Mewtwo")
                .price(5.00f)
                .quantity(1)
                .build());
        cardsList.addCard(new Card.Builder()
                .name("Mew")
                .price(15.00f)
                .quantity(3)
                .build());

        // Matches both Name (contains "Mew") AND Quantity (exactly 3)
        ArrayList<Card> results = cardsList.findCards("Mew", null, 3);
        
        assertEquals(2, results.size());
        assertEquals(20.00f, results.get(0).getPrice());
        assertEquals(15.00f, results.get(1).getPrice());
    }

    @Test
    public void findCards_noMatch_returnsEmptyList() {
        CardsList cardsList = new CardsList();

        cardsList.addCard(new Card.Builder()
                .name("Eevee")
                .price(4.00f)
                .quantity(1)
                .build());

        // Searching for attributes that don't exist
        ArrayList<Card> results = cardsList.findCards("Snorlax", 100.00f, null);
        
        assertEquals(0, results.size());
    }

    @Test
    public void getSortedCards_byPrice_success() {
        CardsList cardsList = new CardsList();

        Card cheapCard = new Card.Builder()
                .name("Cheap card")
                .price(2.00f)
                .quantity(1)
                .build();
        Card expensiveCard = new Card.Builder()
                .name("Expensive card")
                .price(888.00f)
                .quantity(2)
                .build();
        Card moderateCard = new Card.Builder()
                .name("Moderate card")
                .price(50)
                .quantity(3)
                .build();

        cardsList.addCard(cheapCard);
        cardsList.addCard(expensiveCard);
        cardsList.addCard(moderateCard);

        ArrayList<Card> resultsDescending = cardsList.getSortedCards(
                CardSortCriteria.PRICE, false, -1, Integer.MAX_VALUE);
        assertEquals(expensiveCard, resultsDescending.get(0));
        assertEquals(moderateCard, resultsDescending.get(1));
        assertEquals(cheapCard, resultsDescending.get(2));


        ArrayList<Card> resultsAscending = cardsList.getSortedCards(
                CardSortCriteria.PRICE, true, -1, Integer.MAX_VALUE);
        assertEquals(expensiveCard, resultsAscending.get(2));
        assertEquals(moderateCard, resultsAscending.get(1));
        assertEquals(cheapCard, resultsAscending.get(0));
    }

    @Test
    public void editCard_partialAndFullEdit_success() {
        CardsList cardsList = new CardsList();

        Card original = new Card.Builder()
                .name("Pikachu")
                .price(5.50f)
                .quantity(1)
                .build();
        cardsList.addCard(original);

        // partial edit (only name + quantity)
        cardsList.editCard(0, "Pikachu VMAX", 5, null);
        assertEquals("Pikachu VMAX", cardsList.getCard(0).getName());
        assertEquals(5, cardsList.getCard(0).getQuantity());
        assertEquals(5.50f, cardsList.getCard(0).getPrice()); // price unchanged

        // full edit
        cardsList.editCard(0, "Charizard", 10, 25.0f);
        assertEquals("Charizard", cardsList.getCard(0).getName());
        assertEquals(10, cardsList.getCard(0).getQuantity());
        assertEquals(25.0f, cardsList.getCard(0).getPrice());
    }

    @Test
    public void inventoryAndWishlistAreIndependent_success() {
        CardsList inventory = new CardsList();
        CardsList wishlist = new CardsList();

        // Add to inventory only
        Card invCard = new Card.Builder()
                .name("Pikachu")
                .price(5.5f)
                .quantity(1)
                .build();
        inventory.addCard(invCard);

        // Add to wishlist only
        Card wishCard = new Card.Builder()
                .name("Charizard")
                .price(99.99f)
                .quantity(1)
                .build();
        wishlist.addCard(wishCard);

        // Verify separation
        assertEquals(1, inventory.getSize());
        assertEquals(1, wishlist.getSize());
        assertEquals("Pikachu", inventory.getCard(0).getName());
        assertEquals("Charizard", wishlist.getCard(0).getName());

        // Remove from one doesn't affect the other
        inventory.removeCardByIndex(0);
        assertEquals(0, inventory.getSize());
        assertEquals(1, wishlist.getSize());
    }
}
