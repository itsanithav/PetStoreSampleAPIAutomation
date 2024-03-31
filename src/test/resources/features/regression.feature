@reg
Feature: Pet Store
  Background:
  Given PetStore Base url of the API

  @inventory
  Scenario: Verify the petStore - Get Store Inventory api response
    When I query the "storeInventory" get endpoint
    Then I verify the petStore inventory api response is not null

  @placeOrder
  Scenario Outline: Verify the petStore - Post placeAnOrder api response
    When I query the "placeAnOrder" post api endpoint for request body "placeOrderPayload"
      |Id    |PetId      |Quantity   |
      | <id> |  <petId>  |<quantity> |
    Then I verify the "placeAnOrder" post api response
      |Id    |PetId      |Quantity   |
      | <id> |  <petId>  |<quantity> |
    Examples:
    |id |petId|quantity|
    | 1 |  1  |   2    |

  @purchaseOrder
  Scenario: Verify the petStore - Get findPurchaseOrderById api response
    When I query the "findPurchaseOrderById" get endpoint for "invalid" orderId "500"
    Then I verify the "findPurchaseOrderById" get endpoint response is "404" and contains error message "Order not found"

  @deleteOrder
  Scenario: Verify the petStore - Delete deletePurchaseOrderById api response for orderId where record not exists
    When I query the "deletePurchaseOrderById" delete endpoint for orderId "500"
    #Then I verify the petStore inventory api response is not null
    Then I verify the "deletePurchaseOrderById" delete endpoint response is "404" and contains message "Order Not Found"

  @deleteOrder
  Scenario: Verify the petStore - Delete deletePurchaseOrderById api response for orderId where record exists
    When I query the "placeAnOrder" post api endpoint for request body "placeOrderPayload"
      |Id |PetId |Quantity|
      | 1 |  1   |2 |
    When I query the "deletePurchaseOrderById" delete endpoint for orderId "1"
    #Then I verify the petStore inventory api response is not null
    Then I verify the "deletePurchaseOrderById" delete endpoint response is "200" and contains message "1"
