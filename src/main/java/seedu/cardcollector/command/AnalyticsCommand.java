package seedu.cardcollector.command;

import seedu.cardcollector.card.CardsAnalytics;
import seedu.cardcollector.card.CardsList;

public class AnalyticsCommand extends Command {
    private static final int MOST_EXPENSIVE_LIMIT = 3;
    private static final int TOP_SET_LIMIT = 3;

    @Override
    public CommandResult execute(CommandContext context) {
        CardsList targetList = context.getTargetList();
        CardsAnalytics analytics = targetList.getAnalytics(MOST_EXPENSIVE_LIMIT, TOP_SET_LIMIT);
        String listName = targetList == context.getWishlist() ? "wishlist" : "inventory";

        context.getUi().printAnalytics(listName, analytics);
        return new CommandResult(false, false);
    }
}
