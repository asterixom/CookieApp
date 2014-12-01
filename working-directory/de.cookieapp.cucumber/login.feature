Feature: login

As a user not logged in
I want log into my useraccount
so that I have full access to all the features

Scenario: username and password correct
  Given I am not logged in yet
  When I enter my "username" in the field "Benutzername" 
  And I enter my "password" in the field "Kennwort"
  And I press the "enter" button
  And both are stored in the database
  Then my "userdata" will be loaded
  And I am still on the Page
  
 Scenario: username or password is wrong
  Given I am not logged in yet
  When I enter my "username" in the field "Benutzername" 
  And I enter my "password" in the field "Kennwort"
  And I press the "enter" button
  And one of them is wrong
  Then ma dialog will open, telling the user he made an error
   