package seedu.cardcollector;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardsListTest {

    @Test
    public void addCard_card_success() {
        CardsList cardsList = new CardsList();

        Instant instant = Instant.parse("2026-03-12T18:00:00Z");
        Card card = new Card("Pikachu", 1, 5.50f, instant, instant);
        cardsList.addCard(card);

        assertEquals(1, cardsList.getSize());
        assertEquals(card, cardsList.getCard(0));
    }
}
