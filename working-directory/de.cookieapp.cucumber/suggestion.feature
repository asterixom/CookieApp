Feature: Suggestion

A user wants to add a suggestion

Scenario: Suggesting
Given I am logged in
And I opened a suggestion Page
When I enter a suggestion in the "Suggestion" text field
And I click "Submit"
Then the suggestion will be saved
And the suggestion will be shown on the page

Scenario: Cancel
Given I am logged in
And I opened a suggestion Page
When I enter a suggestion in the "Suggestion" text field
And I click "Cancel"
Then the suggestion will NOT be saved
And the suggestion will NOT be shown on the page

Scenario: Leaving the page
Given I am logged in
And I opened a suggestion Page
When I enter a suggestion in the "Suggestion" text field
And I leave the page
Then the suggestion will NOT be saved
And the suggestion will NOT be shown on the page