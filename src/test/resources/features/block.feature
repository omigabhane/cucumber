Feature: Block test cases

  Scenario: Check for broken links on block page
    When Visitor opens https://blog.dropbox.com/
    And Visitor waits for .u01-header__header-logo
    Then Visitor could see all links rendered correctly

  Scenario: Logo of the block page
    When Visitor opens https://blog.dropbox.com/
    And Visitor waits for .u01-header__header-logo
    Then Visitor could see 'blockLogo.svg' at .u01-header__logo-link displayed correctly

  Scenario: Button of the block page
    When Visitor opens https://blog.dropbox.com/
    And Visitor waits for .u01-header__header-logo
    And the Visitor scrolls to .b24-featured-collection-plank__collection-title-btn
    Then Visitor could see .b24-featured-collection-plank__collection-title-btn has text as View all
    And Visitor could see .b24-featured-collection-plank__collection-title-btn has hover as rgba(0, 0, 0, 0)