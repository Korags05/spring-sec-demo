package org.kunal.spring_sec_demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Student {
    private int id;
    private String name;
    private String techStack;
}
