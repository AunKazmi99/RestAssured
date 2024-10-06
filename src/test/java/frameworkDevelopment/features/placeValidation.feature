Feature: Validating Place APIs

  @AddPlace
  Scenario Outline: To verify if place is being successfully added using AddPlaceAPI
    Given Add Place Payload with <name>, <address>, and <language>
    When User calls "AddPlaceAPI" with "Post" HTTP Request
    Then The API call got success with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    And Verify placeId created maps to <name> using "GetPlaceAPI"
    Examples:
      | name              | address                     | language    |
      | "FrontLine house" | "29, side layout, cohen 09" | "French-IN" |
      | "BackLine house"  | "28, side layout, cohen 09" | "English"   |

    @DeletePlace
    Scenario: To verify if delete place functionality is working
      Given Delete Place payload
      When User calls "DeletePlaceAPI" with "POST" HTTP Request
      Then The API call got success with status code 200
      And "status" in response body is "OK"
