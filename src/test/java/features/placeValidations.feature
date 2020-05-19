Feature: Validating Place API's

  @AddPlace
  Scenario Outline: Verify if a Place is being Successfully added using AddPlaceAPI
    Given Add Place Payload with "<name>" "<language>" "<address>"
    When user calls "addPlaceAPI" with "POST" http request
    Then the API call is success with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    And verify place_Id created maps to "<name>" using "getPlaceAPI"

    Examples:
      | name     | language   | address            |
      | AAHouse  | English    | World cross center |
 #     | BBHouse  | Spanish    | Sea Cross Center   |

  @DeletePlace
  Scenario: Verify if Delete Place functionality is working
    Given Delete Place Payload
    When user calls "deletePlaceAPI" with "POST" http request
    Then the API call is success with status code 200
    And "status" in response body is "OK"



