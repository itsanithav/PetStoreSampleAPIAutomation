package petStore;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class PetStoreSpecBuilders {
    public static RequestSpecification req;

    public static RequestSpecification getRequestWithNoQueryParam(String uri) {
        req = new RequestSpecBuilder()
                .setBaseUri(uri)
                .addHeader("accept","application/json")
                .build();
        return req;
    }

    public static RequestSpecification getRequestWithQueryParam(String uri, int orderId) {
        req = new RequestSpecBuilder()
                .setBaseUri(uri)
                .addHeader("accept","application/json")
                .addQueryParam("orderId",orderId)
                .build();
        return req;
    }

    public static RequestSpecification postRequestWithBody(String uri,Object body) {
        req = new RequestSpecBuilder()
                .setBaseUri(uri)
                .addHeader("accept","application/json")
                .addHeader("Content-Type","application/json")
                .setBody(body)
                .build();
        return req;
    }
    public static RequestSpecification deleteRequestWithQueryParam(String uri, int orderId) {
        req = new RequestSpecBuilder()
                .setBaseUri(uri)
                .addHeader("accept","application/json")
                .addQueryParam("orderId",orderId)
                .build();
        return req;
    }


}

