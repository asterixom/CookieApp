Feature: edit recipe

As a logged in user 
I want to edit one of my recipes
so that I can add something or remove errors

Scenario: change something and pressed on save
  Given I am logged in 
  And I am on the recipe I want to edit
  And I have pressed the "Rezept ändern" button
  When I change the entery of an field 
  And I press the "speichern" button
  Then the recipe will be updated with the given change into the database
  And I am on the recipe page
  
Scenario: recipe data complete and pressed on cancel
  Given I am logged in 
  And I am on the recipe I want to edit
  And I have pressed the "Rezept ändern" button
  When I change the entery of an field 
  And I press the "abbrechen" button
  Then the recipe change will be discarded
  And I am on the recipe page
  
Scenario: change something and pressed on save
  Given I am logged in 
  And I am on the recipe I want to edit
  And I have pressed the "Rezept ändern" button
  When I delete the entery of a needed field
  And I press the "speichern" button
  Then I am still on the same page
  And the page shows a notification that a field is missing
  
  