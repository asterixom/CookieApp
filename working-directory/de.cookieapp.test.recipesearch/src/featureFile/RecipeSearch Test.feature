Feature: RecipeSearch

Scenario: Search for Lasagne
	Given I am on the CookieApp Homepage
	And I am on the RecipeSearch Tab
	When I enter "Lasagne" in the searchfield 
	And I press the Suchen button
	Then The Page will show "Lasagne" as Result
  
Scenario: Search for Burger
	Given I am on the CookieApp Homepage
	And I am on the RecipeSearch Tab
	When I enter "Burger" in the searchfield 
	And I press the Suchen button
	Then The Page will show "Hamburger" and "Cheeseburger" as Results