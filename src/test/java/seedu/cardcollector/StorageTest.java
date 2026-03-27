package seedu.cardcollector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class StorageTest {
    @TempDir
    Path tempDir;

    @Test
    public void saveLoad_roundTripPreservesCardsHistoryAndSorting() throws Exception {
        CardsList inventory = new CardsList();

        Card activeCard = new Card.Builder()
                .name("Pikachu")
                .price(5.5f)
                .quantity(2)
                .build();
        inventory.addCard(activeCard);
        activeCard.setLastAdded(Instant.parse("2026-03-26T09:00:00Z"));
        activeCard.setLastModified(Instant.parse("2026-03-27T10:00:00Z"));

        Card removedCard = new Card.Builder()
                .name("Charizard")
                .price(99.99f)
                .quantity(1)
                .build();
        inventory.addCard(removedCard);
        removedCard.setLastAdded(Instant.parse("2026-03-25T08:00:00Z"));
        removedCard.setLastModified(Instant.parse("2026-03-25T08:00:00Z"));
        inventory.removeCardByName("Charizard");
        inventory.getRemovedCards().get(0).setLastModified(Instant.parse("2026-03-28T11:00:00Z"));

        CardsList wishlist = new CardsList();
        Card wishlistCard = new Card.Builder()
                .name("Umbreon")
                .price(42.0f)
                .quantity(3)
                .build();
        wishlist.addCard(wishlistCard);
        wishlistCard.setLastAdded(Instant.parse("2026-03-24T07:00:00Z"));
        wishlistCard.setLastModified(Instant.parse("2026-03-24T07:00:00Z"));

        Storage storage = new Storage(tempDir.resolve("cardcollector.txt"));
        storage.save(new AppState(inventory, wishlist));

        AppState loadedState = storage.load();
        CardsList loadedInventory = loadedState.getInventory();
        CardsList loadedWishlist = loadedState.getWishlist();

        assertEquals(1, loadedInventory.getSize());
        assertEquals(2, loadedInventory.getAddedSize());
        assertEquals(1, loadedInventory.getRemovedSize());
        assertEquals(1, loadedWishlist.getSize());

        Card loadedActiveCard = loadedInventory.getCard(0);
        Card loadedAddedActiveCard = loadedInventory.getAddedCards().get(0);
        Card loadedAddedRemovedCard = loadedInventory.getAddedCards().get(1);
        Card loadedRemovedCard = loadedInventory.getRemovedCards().get(0);

        assertSame(loadedActiveCard, loadedAddedActiveCard);
        assertSame(loadedRemovedCard, loadedAddedRemovedCard);
        assertEquals("Pikachu", loadedActiveCard.getName());
        assertEquals(Instant.parse("2026-03-26T09:00:00Z"), loadedActiveCard.getLastAdded());
        assertEquals(Instant.parse("2026-03-28T11:00:00Z"), loadedRemovedCard.getLastModified());
        assertNotNull(loadedWishlist.getCard(0).getLastAdded());

        ArrayList<Card> sortedAdded = loadedInventory.getSortedAddedCards(
                CardSortCriteria.LAST_ADDED, false, -1, Integer.MAX_VALUE);
        ArrayList<Card> sortedRemoved = loadedInventory.getSortedRemovedCards(
                CardSortCriteria.LAST_MODIFIED, false, -1, Integer.MAX_VALUE);

        assertEquals("Pikachu", sortedAdded.get(0).getName());
        assertEquals("Charizard", sortedAdded.get(1).getName());
        assertEquals("Charizard", sortedRemoved.get(0).getName());
    }
}
