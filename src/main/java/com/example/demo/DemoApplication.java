package com.example.demo;

import com.example.demo.protobuf.SchoolProto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.example.demo.protobuf.SchoolProto.*;
import static com.example.demo.protobuf.SchoolProto.Student.*;

@SpringBootApplication
public class DemoApplication {

	@Bean
	ProtobufHttpMessageConverter protobufHttpMessageConverter() {
		return new ProtobufHttpMessageConverter();
	}

	public ArrayList<Student> createTestStudents() {
		ArrayList<Student> students = new ArrayList<>();
		ArrayList<PhoneNumber> phoneNumbers = new ArrayList<>();

		phoneNumbers.add(PhoneNumber
				.newBuilder()
				.setNumber("010-0000-0000")
				.setType(PhoneType.MOBILE)
				.build());

		Student student1 = newBuilder()
				.setFirstName("JAKE")
				.setLastName("SEO")
				.setEmail("abc@abc.com")
				.addAllPhone(phoneNumbers)
				.build();

		students.add(student1);

		return students;
	}

	@Bean
	public CourseRepository createTestCourses() {
		Map<Integer, Course> courses = new HashMap<>();

		Course course1 = Course
				.newBuilder()
				.setId(1)
				.setCourseName("REST with spring")
				.addAllStudent(createTestStudents())
				.build();
		Course course2 = Course
				.newBuilder()
				.setId(2)
				.setCourseName("Learn Spring Security")
				.addAllStudent(new ArrayList<Student>())
				.build();

		courses.put(course1.getId(), course1);
		courses.put(course2.getId(), course2);

		return new CourseRepository(courses);
	}

	@Bean
	RestTemplate restTemplate(ProtobufHttpMessageConverter protobufHttpMessageConverter) {
		return new RestTemplate(Arrays.asList(protobufHttpMessageConverter));
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
