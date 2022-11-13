Feature: Tech test cases

  Scenario: Check for broken links on tech page
    When Visitor opens https://dropbox.tech/
    And Visitor waits for .dr-header__section
    Then Visitor could see all links rendered correctly

  Scenario: Logo of the tech page
    When Visitor opens https://dropbox.tech/
    And Visitor waits for .dr-header__section
    Then Visitor could see 'logo_dropbox.svg' at .dr-grid.dr-grid--md-2 > div > a > img displayed correctly