Feature: Comment
As a logged in user
I want to comment on other recipes
so that I can express my opinion on other recipes

Scenario: Comment on recipe
Given I am logged in
And I am currently on the page of a recipe
When I click on the button "Comment"
And I type in my comment in textfield that opens
And I click on "Comment"
Then my comment should be displayed under the recipe
And I should return to the recipe page

Scenario: Comment is not saved
Given I am logged in
And I am currently on the page of a recipe
When I click on the button "Comment"
And I type in my comment in textfield that opens
And I click on "Abort"
Then my comment should not be displayed under the recipe
And I should return to the recipe page
