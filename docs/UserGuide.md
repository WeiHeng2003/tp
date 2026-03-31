# User Guide

## Introduction

CardCollector is a CLI application for tracking a trading card inventory and a separate wishlist.
Each card stores a name, quantity, price, optional metadata such as set, rarity, condition,
language, and card number, plus timestamps used by the history commands.

## Quick Start

1. Ensure that Java 17 or above is installed.
2. Run `./gradlew run` from the project root.
3. Enter commands in the terminal.

## Features

### Getting help: `help` or `/h`

Lists all available commands in a compact reference view, or shows detailed syntax for a specific command.

**Format:** `help [COMMAND]`
**Format:** `COMMAND /h`

**Examples:**
`help`
`help add`
`find /h`

### Adding a card: `add`

Adds a new card to the current list.

**Format:** `add /n NAME /q QUANTITY /p PRICE [/s SET] [/r RARITY] [/c CONDITION] [/l LANGUAGE] [/no CARD_NUMBER]`

- `NAME` can contain spaces.
- `QUANTITY` must be an integer greater than or equal to 0.
- `PRICE` must be a valid number.
- Metadata flags are optional.

**Example:** `add /n Pikachu VMAX /q 2 /p 25.50`
**Example:** `add /n Charizard /q 1 /p 99.99 /s Base Set /r Holo /c Near Mint /l English /no 4/102`

### Editing a card: `edit`

Edits the name, quantity, price, or optional metadata of an existing card.

**Format:** `edit INDEX [/n NEW_NAME] [/q NEW_QUANTITY] [/p NEW_PRICE] [/s SET] [/r RARITY] [/c CONDITION] [/l LANGUAGE] [/no CARD_NUMBER]`

**Examples:**
`edit 1 /n Dragonite VMAX`
`edit 2 /q 5 /p 12.99`
`edit 3 /s Jungle /r Rare`

### Comparing cards: `compare`

Compares two cards from the same list.

**Format:** `compare INDEX1 INDEX2`

**Examples:**
`compare 1 3`
`wishlist compare 2 4`

### Reordering the list: `reorder`

Permanently reorders the stored cards in your inventory or wishlist by the chosen criteria.

**Format:** `reorder CRITERIA [asc|desc]`

- CRITERIA = `name` | `price` | `quantity` | `lastadded` | `lastmodified`

**Examples:**
`reorder price desc`
`wishlist reorder name asc`

### Listing cards: `list`

Displays all cards in the current list in a sorted order.

**Format:** `list [NUMBER | all] [index | quantity | price] [ascending | descending]`

**Examples:**
`list`
`list all`
`list 50 quantity ascending`

- Arguments are optional, but if specified, they must be in order.
- Argument matching is intentionally fuzzy for fast usage.

### Filtering cards: `filter`

Displays only cards in the current list that are filtered by tag.

**Format:** `filter /t TAG`

**Examples:**
`filter`
`filter /t sealed`

### Viewing analytics: `analytics` or `stats`

Shows a quick summary of the current list, including total value, the 3 most expensive cards,
and the top sets by total quantity.

**Format:** `analytics`
**Format:** `stats`

**Examples:**
`analytics`
`stats`
`wishlist analytics`

### Finding cards: `find`

Searches the current list by name, price, quantity, optional metadata, tags/folders, or any combination of them.

**Format:** `find [/n NAME] [/p PRICE] [/q QUANTITY] [/s SET] [/r RARITY] [/c CONDITION] [/l LANGUAGE] [/no CARD_NUMBER] [/t TAG]`

**Examples:**
`find /n pika`
`find /p 5.99`
`find /n charizard /q 2`
`find /s Base Set /r Rare`
`find /t trade`

### Tagging a card: `tag` or `folder`

Adds or removes an optional tag/folder label on an existing card. Tags are lightweight labels such as `deck`,
`sealed`, or `trade`, and you can use them later with `find /t ...` or `list /t ...`.

**Format:** `tag add INDEX /t TAG`
**Format:** `tag remove INDEX /t TAG`

**Examples:**
`tag add 3 /t deck`
`folder remove 2 /t trade`

### Removing a card by index: `removeindex`

Removes a card by its displayed position.

**Format:** `removeindex INDEX`

**Example:** `removeindex 2`

### Removing a card by name: `removename`

Removes the first exact case-insensitive name match.

**Format:** `removename NAME`

**Example:** `removename Pikachu`

### Viewing history: `history`

Displays a historical log of when cards were added, modified, or removed.

**Format:** `history [NUMBER | all] [added | modified | removed | entire] [ascending | descending]`

- Arguments are optional, but if specified, they must be in order.
- Argument matching is intentionally fuzzy for fast usage.
- An 'added' entry occurs when a new or existing card is added, or when the edit command increases the quantity of the card.
- A 'modified' entry occurs when a card value is edited, **excluding** any changes to the quantity of the card.
- A 'removed' entry occurs when a card is removed, or when the edit command decreases the quantity of the card.

**Examples:**
`history all`
`history all removed`
`history 50 added ascending`
`history 50 a a`

### Using the wishlist: `wishlist`

Prefix any list-based command with `wishlist ` to run it on the wishlist instead of the main inventory.

**Examples:**
`wishlist add /n Charizard /q 1 /p 99.99`
`wishlist list`
`wishlist edit 1 /n Shiny Charizard`
`wishlist removeindex 2`

### Acquiring a card from wishlist: `wishlist acquired`

Moves a card from the wishlist to your main inventory (and removes it from the wishlist).

**Format:** `wishlist acquired INDEX`

**Example:**
`wishlist acquired 3`

### Downloading a storage snapshot: `download`

Exports the current full app state, including inventory and wishlist, to a file path of your choice.

**Format:** `download /f FILE_PATH`

**Example:** `download /f backups/cardcollector.txt`

### Uploading a storage snapshot: `upload`

Imports a previously exported storage file into the current session.

**Format:** `upload /f FILE_PATH`

**Example:** `upload /f backups/cardcollector.txt`

`upload` warns before replacing the current in-memory inventory and wishlist. After a successful upload, you can use `undoupload` once to restore the previous session state. The app continues to auto-save to `data/cardcollector.txt`.

### Undoing the last upload: `undoupload`

Restores the inventory and wishlist from before the last successful upload.

**Format:** `undoupload`

### Exiting the program: `bye`

Exits the application.

**Format:** `bye`

## FAQ

**Q**: How do I transfer my data to another computer?

**A**: Run `download /f some-file.txt`, move that file to the other computer, then run `upload /f some-file.txt` there.

## Command Summary

| Command                        | Description                          |
|--------------------------------|--------------------------------------|
| `add /n NAME /q QTY /p PRICE`  | Add card                             |
| `edit INDEX [...]`             | Edit card                            |
| `compare INDEX1 INDEX2`        | Compare cards                        |
| `reorder CRITERIA [asc\|desc]` | Reorder list                         |
| `removeindex INDEX`            | Remove by index                      |
| `removename NAME`              | Remove by name                       |
| `undo`                         | Undo the most recent add/remove/edit |
| `tag add INDEX /t TAG`         | Add tag/folder                       |
| `tag remove INDEX /t TAG`      | Remove tag/folder                    |
| `wishlist acquired INDEX`      | Move to inventory                    |
| `list [...]`                   | List cards                           |
| `filter /t TAG`                | Filter cards                         |
| `analytics`                    | Show list insights                   |
| `help [COMMAND]`               | Show command help                    |
| `download /f FILE_PATH`        | Export data                          |
| `upload /f FILE_PATH`          | Import data                          |
| `undoupload`                   | Undo upload                          |
| `history [...]`                | View history                         |
| `wishlist <command>`           | Use wishlist                         |
| `find [...]`                   | Search cards                         |
| `bye`                          | Exit app                             |
