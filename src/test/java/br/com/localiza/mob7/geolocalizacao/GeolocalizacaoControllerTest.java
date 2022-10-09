package br.com.localiza.mob7.geolocalizacao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class GeolocalizacaoControllerTest {

	@LocalServerPort
	int port;

	@BeforeEach
	public void setUp() {
		RestAssured.port = port;
	}

	@Test
	void testGravarNovasLocalizacoes() {

		Response response = RestAssured.given().header("Content-type", "application/json").and()
				.body("{\r\n" + "	\"placa\": \"PLACA100\",\r\n" + "	\"data\": \"2022-10-01T20:03:42.749Z\",\r\n"
						+ "	\"velocidade\": 10,\r\n" + "	\"longitude\": -3.787128261155063,\r\n"
						+ "	\"latitude\": -38.541740922371744,\r\n" + "	\"ignicao\": true\r\n" + "}")
				.when().post("/api/v1/veiculos/geolocalizacoes").then().extract().response();

		assertEquals(201, response.statusCode());
	}

}
