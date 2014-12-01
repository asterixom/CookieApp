Feature: Delete User

As admin I want to remove a user

Scenario: Deleting user
Given I am logged in as admin
And I opened the delete user page
When I select a user
And I click "Delete"
And I click "OK"
Then the user will be deleted
And I stay on the page

Scenario: Cancel
Given I am logged in as admin
And I opened the delete user page
When I select a user
And I click "Delete"
And I click "Cancel"
Then the user will NOT be deleted
And I stay on the page