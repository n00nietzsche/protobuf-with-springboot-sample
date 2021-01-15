package com.example.demo;

import com.example.demo.protobuf.SchoolProto;

import java.util.Map;

import static com.example.demo.protobuf.SchoolProto.*;

public class CourseRepository {
    Map<Integer, Course> courses;

    public CourseRepository (Map<Integer, Course> courses) {
        this.courses = courses;
    }

    public Course getCourse(int id) {
        return courses.get(id);
    }
}
