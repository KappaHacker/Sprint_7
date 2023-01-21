package courier;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.courier.*;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
public class CreateCourierTest {
    protected final CourierGenerator courierGenerator = new CourierGenerator();
    private CourierClient courierClient;
    private Courier courier;
    private CourierAssertions courierAssertions;
    int сourierId;

    @Before
    @Step("Предусловия для создания курьера")
    public void setUp() {
        courierClient = new CourierClient();
        courier = courierGenerator.getRandomCourier();
        courierAssertions = new CourierAssertions();
    }

    @Test
    @DisplayName("Создание нового курьера")
    @Description("Курьера можно создать")
    public void courierCanBeCreated() {
        ValidatableResponse responseCreateCourier = courierClient.createCourier(courier);
        Credentials credentials = Credentials.from(courier);
        сourierId = courierClient.loginCourier(credentials).extract().path("id");
        courierAssertions.successfullCreation(responseCreateCourier);
    }

    @Test
    @DisplayName("Создание курьера без заполнения поля логин")
    @Description("Курьера нельзя создать без логина. Проверяем статус код и сообщение об ошибке")
    public void courierCanNotBeCreatedWithoutLogin() {
        courier.setLogin(null);
        ValidatableResponse responseNullLogin = courierClient.createCourier(courier);
        courierAssertions.failedCreation(responseNullLogin);
    }

    @Test
    @DisplayName("Create courier without password field")
    @Description("Test to try create new courier without password field. Check status code and message")
    public void courierCanNotBeCreatedWithoutPassword() {
        courier.setPassword(null);
        ValidatableResponse responseNullPassword = courierClient.createCourier(courier);
        courierAssertions.failedCreation(responseNullPassword);
    }

    @Test
    @DisplayName("Create courier without login and password fields")
    @Description("Test to try create new courier without login and password fields. Check status code and message")
    public void courierCanNotBeCreatedWithoutLoginAndPassword() {
        courier.setLogin(null);
        courier.setPassword(null);
        ValidatableResponse responseNullFields = courierClient.createCourier(courier);
        courierAssertions.failedCreation(responseNullFields);
    }

    @Test
    @DisplayName("Create courier with existing data")
    @Description("Test to try create courier with the same/existing data. Check status code and message.")
    public void courierCanNotBeCreatedWithExistingCreds() {
        courierClient.createCourier(courier);
        ValidatableResponse responseCreateCourier = courierClient.createCourier(courier);
        courierAssertions.existingCredsCreation(responseCreateCourier);
    }

    @After
    @Step("Delete test courier")
    public void deleteCourier() {
        if (сourierId != 0) {
            courierClient.deleteCourier(сourierId);
        }
    }
}
