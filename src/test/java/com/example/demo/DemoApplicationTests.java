package com.example.demo;

import com.example.demo.protobuf.SchoolProto;
import com.googlecode.protobuf.format.JsonFormat;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;

import static com.example.demo.protobuf.SchoolProto.*;

@SpringBootTest
class DemoApplicationTests {

	@Autowired private RestTemplate restTemplate;
	private static final String COURSE1_URL = "http://localhost:8080/course/1";

	@Test
	void whenUsingRestTemplate_thenSucceed() {
		/*
		`@Bean`으로 등록한 RestTemplate을 이용한 방법
		 */
		ResponseEntity<Course> courseResponseEntity = restTemplate.getForEntity(COURSE1_URL, Course.class);
		System.out.println(courseResponseEntity.toString());
	}

	private InputStream executeHttpRequest(String url) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet request = new HttpGet(url);
		HttpResponse httpResponse = httpClient.execute(request);
		return httpResponse.getEntity().getContent();
	}

	private String convertProtobufMessageStreamToJsonString(InputStream protobufStream) throws IOException {
		JsonFormat jsonFormat = new JsonFormat();
		// protobuf 를 컴파일해서 만든 클래스에는 `parseFrom` 이란 것이 있어서, Stream 으로부터 파싱할 수 있다.
		// 또한 protobuf 라이브러리 내부에 JsonFormat 이라는 클래스가 있어 protobuf에서 가져온 데이터를 쉽게 JSON으로 변환할 수 있다.
		Course course = Course.parseFrom(protobufStream);
		return jsonFormat.printToString(course);
	}

	@Test
	public void whenUsingHttpClient_thenSucceed() throws IOException {
		InputStream responseStream = executeHttpRequest(COURSE1_URL);
		String jsonOutput = convertProtobufMessageStreamToJsonString(responseStream);
		System.out.println(jsonOutput);
	}
}
