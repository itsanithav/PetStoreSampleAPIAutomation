package petStore.steps;
import CommonApiUtil.ApiUtil;
import Utility.TestPayload;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dataProvider.ConfigFileReader;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;
import petStore.PetStoreApiResources;
import petStore.PetStoreSpecBuilders;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class PetStoreApiSteps {
    public static Logger logger = Logger.getLogger(PetStoreApiSteps.class.getName());
    public Response response;
    private String baseUrl;
    private TestPayload payload = new TestPayload();
    private Object body;

    @Given("PetStore Base url of the API")
    public void PetStoreBaseUrlOfTheAPI() {
        this.baseUrl = ConfigFileReader.petStoreBaseUrl;
        logger.info("PetStore base url is "+baseUrl);
    }

    @When("I query the {string} get endpoint")
    public void queryForTheAccountType(String resource) {
        PetStoreApiResources endpoint = PetStoreApiResources.valueOf(resource);
        logger.info("Endpoint of PetStore is: " +endpoint.getResource());
        response = ApiUtil.getRequestWithSuccessStausCode(PetStoreSpecBuilders.getRequestWithNoQueryParam(baseUrl), endpoint.getResource());
    }

    @Then("I verify the petStore inventory api response is not null")
    public void verifyPetStoreInventoryApiResponseIsNotNull() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = response.asString();
        logger.info("PetStore Inventory Api Response "+jsonString);
        JsonNode node = mapper.readTree(jsonString);
        JsonParser parser = mapper.treeAsTokens(node);
        Map actualResponse = mapper.readValue(parser, Map.class);
        for(Object set : actualResponse.entrySet())
            Assert.assertTrue(set.toString()!=null);
    }

    @When("I query the {string} get endpoint for {string} orderId {string}")
    public void queryGivenEndpointForGivenOrderId(String resource, String validOrInvalid, String orderId) {
        PetStoreApiResources endpoint = PetStoreApiResources.valueOf(resource);
        String completeEndpoint = endpoint.getResource().replace("{orderId}",orderId);
        logger.info("Endpoint of PetStore is: " +completeEndpoint);
        if(validOrInvalid.equalsIgnoreCase("valid"))
            response = ApiUtil.getRequestWithSuccessStausCode(PetStoreSpecBuilders.getRequestWithQueryParam(baseUrl,2), completeEndpoint);
        else
            response = ApiUtil.getRequestWithoutStausCodeValidation(PetStoreSpecBuilders.getRequestWithNoQueryParam(baseUrl), completeEndpoint);
    }

    @Then("I verify the {string} get endpoint response is {string} and contains error message {string}")
    public void verifyErrorMessageForGivenEndpoint(String resource, String statusCode, String expectedErrorMessage) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String actualResponseCode = String.valueOf(response.statusCode());
        Assert.assertTrue(actualResponseCode.equals(statusCode));
        String jsonString = response.asString();
        logger.info("PetStore "+resource +" Api Response "+jsonString);
        JsonNode node = mapper.readTree(jsonString);
        JsonParser parser = mapper.treeAsTokens(node.path("message"));
        String actualResponse = mapper.readValue(parser, String.class);
        Assert.assertTrue(actualResponse.equalsIgnoreCase(expectedErrorMessage));
    }

    @When("I query the {string} post api endpoint for request body {string}")
    public void queryPostApiEndpointForGivenRequestBody(String resource, String payloadName, DataTable table) throws JsonProcessingException {
        PetStoreApiResources endpoint = PetStoreApiResources.valueOf(resource);
        logger.info("Endpoint of PetStore is: " + endpoint);
        List<Map<String,Integer>> fields = table.asMaps(String.class,Integer.class);
        if (payloadName.equalsIgnoreCase("placeOrderPayload")) {
            ObjectMapper objectMapper = new ObjectMapper();
            body = objectMapper.writeValueAsString(payload.placeOrderPayload(fields.get(0).get("Id"),fields.get(0).get("PetId"),fields.get(0).get("Quantity"), LocalDateTime. now().toString() ));
        }
        response = ApiUtil.postRequest(PetStoreSpecBuilders.postRequestWithBody(baseUrl, body), endpoint.getResource());
    }

    @Then("I verify the {string} post api response")
    public void verifyPostApiResponse(String resource, DataTable table) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = response.asString();
        logger.info("PetStore "+resource +" Api Response "+jsonString);
        List<Map<String,Integer>> expectedFieldValues = table.asMaps(String.class,Integer.class);
        JsonNode node = mapper.readTree(jsonString);
        JsonParser parser = mapper.treeAsTokens(node);
        Map<String,Object> actualResponse = parser.readValueAs(Map.class);
        Assert.assertTrue(actualResponse.get("id").equals(expectedFieldValues.get(0).get("Id")));
        Assert.assertTrue(actualResponse.get("petId").equals(expectedFieldValues.get(0).get("PetId")));
        Assert.assertTrue(actualResponse.get("quantity").equals(expectedFieldValues.get(0).get("Quantity")));
    }

    @When("I query the {string} delete endpoint for orderId {string}")
    public void queryGivenEndpointForGivenOrderId(String resource, String orderId) {
        PetStoreApiResources endpoint = PetStoreApiResources.valueOf(resource);
        String completeEndpoint = endpoint.getResource().replace("{orderId}",orderId);
        logger.info("Endpoint of PetStore is: " +completeEndpoint);
        response = ApiUtil.deleteRequest(PetStoreSpecBuilders.deleteRequestWithQueryParam(baseUrl,2), completeEndpoint);
    }

    @Then("I verify the {string} delete endpoint response is {string} and contains message {string}")
    public void verifyErrorMessageForGivenDeleteEndpoint(String resource, String statusCode, String expectedMessage) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String actualResponseCode = String.valueOf(response.statusCode());
        Assert.assertTrue(actualResponseCode.equals(statusCode));
        String jsonString = response.asString();
        logger.info("PetStore "+resource +" Api Response "+jsonString);
        JsonNode node = mapper.readTree(jsonString);
        JsonParser parser = mapper.treeAsTokens(node.path("message"));
        String actualResponse = mapper.readValue(parser, String.class);
        Assert.assertTrue(actualResponse.equalsIgnoreCase(expectedMessage));
    }

}
