package br.com.localiza.mob7.pontodeinteresse;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@TestPropertySource(locations = "classpath:test.properties")
@Sql(scripts = "/db/inseri-ponto-interesse.sql")
@Sql(scripts = "/db/insert-posicionamentos.sql")
@Sql(scripts = "/db/insert_clear.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class PontoDeInteresseControllerTest {

	@LocalServerPort
	int port;

	@BeforeEach
	public void setUp() {
		RestAssured.port = port;
	}

	@Test
	void testGetResumos() {
		Response response = RestAssured.given().contentType(ContentType.JSON).param("dataInicio", "12/12/2018")
				.param("dataFim", "19/12/2018").when().get("api/v1/veiculos/pois").then().assertThat()
				.body("size()", is(4)).extract().response();

		assertEquals(200, response.statusCode());
	}

	@Test
	void testGetResumosComPlaca() {
		Response response = RestAssured.given().contentType(ContentType.JSON).param("dataInicio", "12/12/2018")
				.param("dataFim", "19/12/2018").param("placa", "TESTE001").when().get("api/v1/veiculos/pois").then()
				.assertThat().body("size()", is(3)).extract().response();

		assertEquals(200, response.statusCode());
	}

	@Test
	void testGravarNovosPontosDeInteresse() {
		Response response = RestAssured.given().header("Content-type", "application/json").and()
				.body("{\r\n" + "	\"nome\": \"MEU TRABALHO 2\",\r\n" + "	\"raio\": 500,\r\n"
						+ "	\"longitude\": -3.787128261155063,\r\n" + "	\"latitude\": -38.541740922371744\r\n" + "}")
				.when().post("api/v1/veiculos/pois").then().extract().response();

		assertEquals(201, response.statusCode());
	}

}
