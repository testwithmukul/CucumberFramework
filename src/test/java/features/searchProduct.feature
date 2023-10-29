Feature: Search and place the order for products

Scenario: Search experience for product search in both Home Page and Offers Page
  Given User is on GreenCart Landing Page
  When User search with shortname "Tom" and extract actual name of the product
  Then User search for "Tom" shortname in Offers Page
  And Validate HomePage and OfferPage product name is same

