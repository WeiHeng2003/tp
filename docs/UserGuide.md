# User Guide

## Introduction

CardCollector is a command-line interface (CLI) application designed for trading card enthusiasts to easily manage their card inventory. Track the cards you own, their quantities, their prices, and your collection history efficiently.

## Quick Start

{Give steps to get started quickly}

1. Ensure that you have Java 17 or above installed.
1. Down the latest version of `CardCollector` from [here](http://link.to/todo).

## Features 


### Adding a card: `add`
Adds a new card to your inventory.
**Format:** `add /n [NAME] /q [QUANTITY] /p [PRICE]`
* The `NAME` can contain spaces.
* `QUANTITY` must be an integer greater than or equal to 0.
* `PRICE` must be a positive number.

**Example:**
`add /n Pikachu VMAX /q 2 /p 25.50`

### Editing a card: `edit`
Edits the name, quantity, or price of an existing card.  
**Format:** `edit INDEX [/n NEW_NAME] [/q NEW_QUANTITY] [/p NEW_PRICE]`
* At least **one** field must be provided.
* `INDEX` is the number shown in the list.

**Examples:**  
`edit 1 /n Dragonite VMAX`  
`edit 2 /q 5 /p 12.99`

### Wishlist commands
All commands work on a separate wishlist. Prefix any command with `wishlist`.  
**Examples:**  
`wishlist add /n Charizard /q 1 /p 99.99`  
`wishlist list`  
`wishlist edit 1 /n Shiny Charizard`  
`wishlist remove 2`

### Finding cards: `find`
Searches your inventory for cards that match specific attributes. You can search by name, price, quantity, or a combination of them.
**Format:** `find [/n NAME] [/p PRICE] [/q QUANTITY]`
* The search is case-insensitive.
* At least one of the optional fields must be provided.
* When multiple attributes are specified, it behaves like an AND operation (finds cards matching *all* provided attributes).

**Examples:**
* `find /n pika` returns all cards with "pika" in the name.
* `find /p 5.99` returns all cards priced exactly at 5.99.
* `find /n charizard /q 2` returns cards with "charizard" in the name that also have exactly 2 in quantity.

### Removing a card: `remove`
Removes a card from the inventory either by its displayed index number or by its exact name.
**Format:** `remove [INDEX]` OR `remove [NAME]`
* `INDEX` refers to the index number shown in the displayed list.
* If a string is provided instead of an index, it will attempt to remove the first card matching that exact name (case-insensitive).

**Examples:**
* `remove 2` removes the 2nd card in the inventory list.
* `remove Pikachu` removes the card named "Pikachu".

### Listing all cards: `list`
Displays all the cards currently in your inventory.
**Format:** `list`

### Viewing history: `history`
Displays a chronological history of your cards based on when they were added, modified, or removed.
**Format:** `history [added | modified | removed] [NUMBER | all]`

**Examples:**
* `history added` shows the history of cards sorted by the date they were added.
* `history removed` shows a log of the cards you have deleted from your inventory.

### Exiting the program: `bye`
Exits the application.
**Format:** `bye`

## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: {your answer here}

## Command Summary

{Give a 'cheat sheet' of commands here}

* Add todo `todo n/TODO_NAME d/DEADLINE`
* Find a card `find [/n NAME] [/p PRICE] [/q QUANTITY]`
* Edit a card `edit INDEX [/n NAME] [/q QTY] [/p PRICE]`
* Wishlist commands `wishlist <any command>`
