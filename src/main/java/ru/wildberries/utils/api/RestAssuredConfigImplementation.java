package ru.wildberries.utils.api;

import io.restassured.RestAssured;
import io.restassured.config.ConnectionConfig;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RedirectConfig;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.specification.RequestSpecification;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.Timeout;
import org.openqa.selenium.WebDriver;
import ru.wildberries.config.TestConfig;
import ru.wildberries.config.env.DefaultUserEnvironmentPool;
import ru.wildberries.utils.Utils;

import java.util.HashMap;

public final class RestAssuredConfigImplementation implements Utils {

    private static final Timeout CONNECTION_TIMEOUT_IN_MILLISECONDS = Timeout.ofSeconds(TestConfig.IMPLICITLY_WAIT_IN_SECONDS);

    static {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        SSLConfig sslConfig = SSLConfig.sslConfig().allowAllHostnames().relaxedHTTPSValidation();
        ConnectionConfig connectionConfig = ConnectionConfig.connectionConfig().closeIdleConnectionsAfterEachResponse();

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(CONNECTION_TIMEOUT_IN_MILLISECONDS)
                .setConnectionRequestTimeout(CONNECTION_TIMEOUT_IN_MILLISECONDS)
                .setRedirectsEnabled(true)
                .build();

        HttpClientConfig httpClientConfig = HttpClientConfig.httpClientConfig().dontReuseHttpClientInstance();
        RedirectConfig redirectConfig = new RedirectConfig().followRedirects(true);

        HttpClientConfig.HttpClientFactory httpClientFactory = () -> {
            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
            connectionManager.closeExpired();
            connectionManager.closeIdle(CONNECTION_TIMEOUT_IN_MILLISECONDS);

            CloseableHttpClient closeableHttpClient = HttpClientBuilder.create()
                    .setDefaultRequestConfig(requestConfig)
                    .setConnectionManager(connectionManager)
                    .build();

            return null;
        };

        RestAssured.config = RestAssured.config()
                .redirect(redirectConfig)
                .connectionConfig(connectionConfig)
                .sslConfig(sslConfig)
                .httpClient(httpClientConfig);
    }

    private RestAssuredConfigImplementation() {
    }

    public static RestAssuredConfigImplementation with() {
        return new RestAssuredConfigImplementation();
    }

    public RequestSpecification napiUrl() {
        RestAssured.baseURI = TestConfig.environment.napiUrl.toString();
        WebDriver webDriver = DefaultUserEnvironmentPool.getInstance().get().webDriver;
        Cookie routeCookie = cookiesUtils().getRouteCookie(webDriver);
        HashMap<String, String> queryParams = getQueryParams();

        return RestAssured.given()
                .cookie(routeCookie)
                .queryParams(queryParams)
                .contentType(ContentType.URLENC)
                .config(RestAssured.config())
                .accept(ContentType.URLENC)
                .auth().none()
                .log().all();
    }

    public RequestSpecification siteUrl() {
        RestAssured.baseURI = TestConfig.environment.siteUrl.toString();
        WebDriver webDriver = DefaultUserEnvironmentPool.getInstance().get().webDriver;
        Cookie routeCookie = cookiesUtils().getRouteCookie(webDriver);
        HashMap<String, String> queryParams = getQueryParams();

        return RestAssured.given()
                .cookie(routeCookie)
                .queryParams(queryParams)
                .config(RestAssured.config())
                .accept(ContentType.ANY)
                .auth().none()
                .log().all();
    }

    private HashMap<String, String> getQueryParams() {
        return new HashMap<>() {{
            put("_app-type", "sitemobile");
            put("_test-instance", "Y");
        }};
    }

}
