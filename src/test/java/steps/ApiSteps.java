package steps;

import com.api_test.common.ApiEventStorage;
import com.api_test.common.BaseStep;
import com.api_test.common._ApiStep;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class ApiSteps extends BaseStep{

    @And("User send api call")
    public void userSendApiCall() {

    }


    @And("^User sends api call to \"([^\"]*)\"$")
    public void userSendsApiCallTo(String re) {


    }

    @Then("response status is \"([^\"]*)\"$")
    public void responseStatusIs(String arg0) {
    }


    @And("^verify response$")
    public void verifyResponse() {

    }

    @Then("check db")
    public void checkDb() {
    }


    @And("verify response for api call to \"([^\"]*)\"$")
    public void verifyResponseForApiCallTo(String re) throws Exception {

    }

    @Given("user send api call to {string}")
    public void userSendApiCallTo(String arg0) {

    }

    @And("user send api call \"([^\"]*)\"$")
    public void userSendApiCall(String re) {
        new ApiEventStorage(re);
    }
}
