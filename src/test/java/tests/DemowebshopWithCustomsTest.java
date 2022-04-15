package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static listenters.CustomAllureListener.withCustomTemplates;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class DemowebshopWithCustomsTest {

        @BeforeAll
        static void setUp() {
            RestAssured.baseURI = "http://demowebshop.tricentis.com/";
        }
    @Tag("FirstTest")
    @Test
    void addToWishlistWithAllureListenerTest() {
        given()
                .filter(new AllureRestAssured())
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .body("addtocart_53.EnteredQuantity=1")
                .when()
                .post("/addproducttocart/details/53/2")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/wishlist\">wishlist</a>"));
    }

    @Test
    void addToWishlistAndCheckWithCustomFiltersTest() {
        given()
                .filter(withCustomTemplates())
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .body("addtocart_53.EnteredQuantity=1")
                .when()
                .post("/addproducttocart/details/53/2")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/wishlist\">wishlist</a>"))
                .body("updatetopwishlistsectionhtml", is(notNullValue()));
    }
}
