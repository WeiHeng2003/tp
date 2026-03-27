package seedu.cardcollector;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Storage {
    private static final String FILE_HEADER = "CARDCOLLECTOR_STORAGE_V1";
    private static final String NULL_VALUE = "-";

    private final Path filePath;

    public Storage(Path filePath) {
        this.filePath = filePath;
    }

    public static Storage createDefault() {
        return new Storage(Path.of("data", "cardcollector.txt"));
    }

    public AppState load() throws IOException {
        if (!Files.exists(filePath)) {
            return new AppState(new CardsList(), new CardsList());
        }

        List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        if (lines.isEmpty() || !FILE_HEADER.equals(lines.get(0))) {
            throw new IOException("Invalid storage file header");
        }

        Map<String, ArrayList<Card>> sections = new HashMap<>();
        Map<UUID, Card> cardsById = new HashMap<>();
        String currentSection = null;

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.startsWith("SECTION ")) {
                currentSection = line.substring("SECTION ".length());
                sections.putIfAbsent(currentSection, new ArrayList<>());
                continue;
            }

            if ("ENDSECTION".equals(line)) {
                currentSection = null;
                continue;
            }

            if (currentSection == null || line.isBlank()) {
                continue;
            }

            sections.get(currentSection).add(parseCard(line, cardsById));
        }

        return new AppState(
                buildList(sections, "inventory", "inventory_removed", "inventory_added"),
                buildList(sections, "wishlist", "wishlist_removed", "wishlist_added"));
    }

    public void save(AppState state) throws IOException {
        Files.createDirectories(filePath.getParent());

        ArrayList<String> lines = new ArrayList<>();
        lines.add(FILE_HEADER);
        appendSection(lines, "inventory", state.getInventory().getCards());
        appendSection(lines, "inventory_removed", state.getInventory().getRemovedCards());
        appendSection(lines, "inventory_added", state.getInventory().getAddedCards());
        appendSection(lines, "wishlist", state.getWishlist().getCards());
        appendSection(lines, "wishlist_removed", state.getWishlist().getRemovedCards());
        appendSection(lines, "wishlist_added", state.getWishlist().getAddedCards());

        Files.write(filePath, lines, StandardCharsets.UTF_8);
    }

    private static CardsList buildList(Map<String, ArrayList<Card>> sections,
                                       String cardsSection,
                                       String removedSection,
                                       String addedSection) {
        return new CardsList(
                sections.getOrDefault(cardsSection, new ArrayList<>()),
                sections.getOrDefault(removedSection, new ArrayList<>()),
                sections.getOrDefault(addedSection, new ArrayList<>())
        );
    }

    private static void appendSection(ArrayList<String> lines, String name, ArrayList<Card> cards) {
        lines.add("SECTION " + name);
        for (Card card : cards) {
            lines.add(serializeCard(card));
        }
        lines.add("ENDSECTION");
    }

    private static String serializeCard(Card card) {
        return String.join("\t",
                card.getUid().toString(),
                Base64.getEncoder().encodeToString(card.getName().getBytes(StandardCharsets.UTF_8)),
                String.valueOf(card.getQuantity()),
                String.valueOf(card.getPrice()),
                serializeInstant(card.getLastAdded()),
                serializeInstant(card.getLastModified()));
    }

    private static Card parseCard(String line, Map<UUID, Card> cardsById) throws IOException {
        String[] parts = line.split("\t", -1);
        if (parts.length != 6) {
            throw new IOException("Malformed card record");
        }

        try {
            UUID uid = UUID.fromString(parts[0]);
            String name = new String(Base64.getDecoder().decode(parts[1]), StandardCharsets.UTF_8);
            int quantity = Integer.parseInt(parts[2]);
            float price = Float.parseFloat(parts[3]);
            Instant lastAdded = parseInstant(parts[4]);
            Instant lastModified = parseInstant(parts[5]);

            Card card = cardsById.get(uid);
            if (card == null) {
                card = new Card.Builder()
                        .uid(uid)
                        .name(name)
                        .quantity(quantity)
                        .price(price)
                        .lastAdded(lastAdded)
                        .lastModified(lastModified)
                        .build();
                cardsById.put(uid, card);
            } else {
                card.setName(name);
                card.setQuantity(quantity);
                card.setPrice(price);
                card.setLastAdded(lastAdded);
                card.setLastModified(lastModified);
            }
            return card;
        } catch (IllegalArgumentException e) {
            throw new IOException("Malformed card record", e);
        }
    }

    private static String serializeInstant(Instant instant) {
        return instant == null ? NULL_VALUE : instant.toString();
    }

    private static Instant parseInstant(String value) {
        return NULL_VALUE.equals(value) ? null : Instant.parse(value);
    }
}
