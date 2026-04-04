# Calvin-GH's Project Portfolio Page

## Overview
CardCollector is a lightweight command-line application designed for trading card enthusiasts to efficiently manage and track their collections.

## Summary of contributions

### Code Contributed
- [RepoSense Report](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=calvin-gh&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2026-02-20T00%3A00%3A00&filteredFileName=)

---

### Enhancements implemented

- Implemented `remove` feature with multiple modes (`RemoveCardByIndex`, `RemoveCardByName`)
    - Justification: Provides flexibility for users, as removing by index is more convenient when viewing lists, while removing by name is useful when the exact item is known.

- Added `notes` feature to allow users to attach additional information to cards
    - Justification: Enhances usability by enabling users to store extra details beyond basic attributes.
    - Difficulties faced:
        - Required modification of the data model to support notes.
        - Needed updates to parsing logic to handle new input formats.

- Implemented `duplicate` detection feature
    - Justification: Helps users identify repeated entries and maintain a clean collection.
    - Required designing comparison logic across stored cards.

- Contributed to `analytics` feature for summarising card data
    - Justification: Provides users with insights instead of just storage functionality.
    - Fixed issues related to numeric precision and formatting.

- Improved `history` feature to track user actions
    - Justification: Allows users to review past actions such as additions and removals.
    - Fixed edge cases to ensure consistency of recorded history.

- Refactored command structure and added commands such as `find`, `list`, and `exit`
    - Justification: Improves maintainability and scalability of the system.
    - Enables easier integration of future features.

---

### Contributions to the UG
- Documented and updated multiple command features, including:
    - `notes` feature (usage, examples, and expected behaviour)
    - `duplicate` detection feature
    - enhancements to existing commands such as `remove`
- Improved clarity and consistency of command instructions.
- Ensured documentation accurately reflects system behaviour after feature updates and refactoring.

---

### Contributions to the DG
- Documented implementation details for key features:
    - Notes feature (data model changes and command flow)
    - Duplicate detection logic
- Updated documentation to reflect command architecture refactoring.
- Contributed to explaining design decisions and system behaviour to improve maintainability.

---

### Contribution to Team-Based Tasks
- Assisted in integrating features into the main codebase.
- Helped resolve merge conflicts and maintain code consistency.
- Contributed to improving overall code quality and structure.
