package petStore;

public enum PetStoreApiResources {

    //pet store resources

    storeInventory("/v2/store/inventory"),
    placeAnOrder("/v2/store/order"),
    findPurchaseOrderById("/v2/store/order/{orderId}"),
    deletePurchaseOrderById("/v2/store/order/{orderId}");

    private String resource;

    PetStoreApiResources(String resource) { this.resource = resource;}
    public String getResource() {
        return resource;
    }
}