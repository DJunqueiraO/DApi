package com.github.djunqueirao.test;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.github.djunqueirao.main.DapiRequestManager;
import com.github.djunqueirao.main.DapiRequestResponse;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RequestManagerTest {
	
	DapiRequestManager requestManager;

	@BeforeEach
	void BeforeEachTest() {
		this.requestManager = new DapiRequestManager("https://65d4abe53f1ab8c63435b6ea.mockapi.io");
		this.requestManager.setSSLVerification(false);
	}

	@Test
	@DisplayName("Should get a body")
	void shouldPerformGet() {
		DapiRequestResponse response = requestManager.get("/test");
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(200, response.getCode());
		Assertions.assertNull(response.getError());
		Assertions.assertNotNull(response.getMessage());
	}

	@Test
	@DisplayName("Should post a body")
	void shouldPerformPost() {
		DapiRequestResponse response = requestManager.post("/test", "{\"message\": \"test\"}");
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(201, response.getCode());
		Assertions.assertNull(response.getError());
		Assertions.assertNotNull(response.getMessage());
	}

	@Test
	@DisplayName("Should put a body")
	void shouldPerformPut() {
		DapiRequestResponse response = requestManager.put("/test/" + 1, String.format("{\"message\": \"%s\"}", LocalDateTime.now()));
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(200, response.getCode());
		Assertions.assertNull(response.getError());
		Assertions.assertNotNull(response.getMessage());
	}

	@Test
	@DisplayName("Should delete a body")
	void shouldPerformDelete() {
		DapiRequestResponse response = requestManager.delete("/test/", 1);
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(200, response.getCode());
		Assertions.assertNull(response.getError());
		Assertions.assertNotNull(response.getMessage());
	}
}
