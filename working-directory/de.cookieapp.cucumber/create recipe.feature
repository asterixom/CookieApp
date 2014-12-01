Feature: create recipe

As a logged in user 
I want create and submit a new recipe
so that other users can open and cook my recipe

Scenario: recipe data complete and pressed on save
  Given I am logged in 
  And I have pressed the "Rezept Hochladen" button
  When I enter a "recipe name" in the field "Rezeptname" 
  And I enter all the "ingredients" in the field "Zutaten"
  And I enter the "decription" in the field "Beschreibung"
  And I enter the "instruction" in the field "Zubereitung"
  And I press the "speichern" button
  Then the recipe will be stored into the database
  And I am on the recipe page
  
Scenario: recipe data complete and pressed on cancel
  Given I am logged in 
  And I have pressed the "Rezept Hochladen" button
  When I enter a "recipe name" in the field "Rezeptname" 
  And I enter all the "ingredients" in the field "Zutaten"
  And I enter the "decription" in the field "Beschreibung"
  And I enter the "instruction" in the field "Zubereitung"
  And I press the "abbrechen" button
  Then the recipe will be discarded
  And I am on the main page
  
Scenario: recipe data incomplete and pressed on save
  Given I am logged in 
  And I have pressed the "Rezept Hochladen" button
  And I enter the "decription" in the field "Beschreibung"
  And I enter the "instruction" in the field "Zubereitung"
  And I press the "speichern" button
  Then I am still on the same page
  And the page shows a notification that a field is missing
  