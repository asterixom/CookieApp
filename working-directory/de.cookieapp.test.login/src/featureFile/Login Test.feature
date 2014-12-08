Feature: Login


 Scenario: username or password is wrong
	Given I am on the CookieApp Homepage
	And I am not logged in
	When I enter "Chris" in the field "Benutzername" 
	And I enter "Test2" in the field "Kennwort"
	And I press the "Anmelden" button
	Then I am not logged in after
	
Scenario: username and password correct
	Given I am on the CookieApp Homepage
	And I am not logged in
	When I enter "Chris" in the field "Benutzername" 
	And I enter "Test1" in the field "Kennwort"
	And I press the "Anmelden" button
	Then The Page will say Eingeloggt als "Chris"
 