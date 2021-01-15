package com.example.demo.controller;

import com.example.demo.CourseRepository;
import com.example.demo.protobuf.SchoolProto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController {
    @Autowired CourseRepository courseRepository;

    @RequestMapping("/course/{id}")
    SchoolProto.Course customer(@PathVariable Integer id) {
        return courseRepository.getCourse(id);
    }
}
