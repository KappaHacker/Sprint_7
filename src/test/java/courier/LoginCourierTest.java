package courier;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.courier.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoginCourierTest {
    private CourierGenerator courierGenerator = new CourierGenerator();
    private Credentials credentials;
    private CourierClient courierClient;
    private Courier courier;
    CourierAssertions courierAssertions;
    int idCourier;

    @Before
    @Step("Предусловия для логина курьера")
    public void setUp() {
        courierClient = new CourierClient();
        courier = courierGenerator.getRandomCourier();
        courierClient.createCourier(courier);
        credentials = Credentials.from(courier);
        courierAssertions = new CourierAssertions();
    }

    @Test
    @DisplayName("Логин курьера существующими данными")
    @Description("Можно войти с существующими данными")
    public void courierCanSuccessfullyLogin() {
        ValidatableResponse responseLoginCourier = courierClient.loginCourier(credentials);
        courierAssertions.successfullLogIn(responseLoginCourier);
        idCourier = responseLoginCourier.extract().path("id");
    }

    @Test
    @DisplayName("Логин курьера без заполнения поля Логин")
    @Description("Попытка входа без заполнения поля Логин. Проверка сообщения об ошибке")
    public void courierLoginUnsuccessfullyWithoutLogin() {
        Credentials credentialsWithoutLogin = new Credentials("", courier.getPassword()); // c null тесты виснут
        ValidatableResponse responseLoginErrorMessage = courierClient.loginCourier(credentialsWithoutLogin);
        courierAssertions.failledLogIn(responseLoginErrorMessage);

    }

    @Test
    @DisplayName("Логин курьера без ввода пароля")
    @Description("Попытка входа без ввода пароля. Проверка сообщения об ошибке")
    public void courierLoginUnsuccessfullyWithoutPassword() {
        Credentials credentialsWithoutPass = new Credentials(courier.getLogin(), "");
        ValidatableResponse responseLoginErrorMessage = courierClient.loginCourier(credentialsWithoutPass);
        courierAssertions.failledLogIn(responseLoginErrorMessage);
    }

    @Test
    @DisplayName("Логин курьера без ввода логина И пароля")
    @Description("Попытка входа без ввода логина И пароля. Проверка сообщения об ошибке")
    public void courierLoginWithoutLoginAndPassword() {
        Credentials credentialsWithoutLoginAndPassword = new Credentials("", "");
        ValidatableResponse responseLoginErrorMessage = courierClient.loginCourier(credentialsWithoutLoginAndPassword);
        courierAssertions.failledLogIn(responseLoginErrorMessage);
    }

    @Test
    @DisplayName("Логин курьера под несуществующим логином")
    @Description("Попытка входа с несуществующим логином. Проверка сообщения об ошибке")
    public void courierLoginWithNotExistingLogin() {
        Credentials credentialsWithNotExistingLogin = new Credentials(RandomStringUtils.randomAlphanumeric(6), courier.getPassword());
        ValidatableResponse responseLoginErrorMessage = courierClient.loginCourier(credentialsWithNotExistingLogin);
        courierAssertions.nonExistentAccountLogIn(responseLoginErrorMessage);
    }

    @After
    @Step("Удаление курьера")
    public void deleteCourier() {
        if (idCourier != 0) {
            courierClient.deleteCourier(idCourier);
        }
    }
}