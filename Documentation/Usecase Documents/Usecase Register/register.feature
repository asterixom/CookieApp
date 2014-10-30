Feature: register

As a unregistered user
I want to register
so that I can use the special features of the website

Scenario: Credentials are already taken
Given I am not registered
When I click on "Register"
And I enter my desired username
And I enter my desired dassword
And I enter my e-mail adress
And I click on "Register"
Then the system will send a notification to the user that the username is already taken
And the system will return to the credential input page

Scenario: Input is missing
Given I am not registered
When I click on "Register"
And I do not fill out every information that is needed
And I click on "Register"
Then the system will send a notification to the user that information is missing
And the system will return to the credential input page

Scenario: Credentials are correct
Given I am not registered
When I click on "Register"
And I enter my desired username
And I enter my desired dassword
And I enter my e-mail adress
And I click on "Register"
Then the system will send a notification that my credentials are available
And the system will save my credentials in the database
And I can login