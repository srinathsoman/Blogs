package com.blogger.blogs.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@SequenceGenerator(name="id_generator",sequenceName="id_seq", allocationSize=1)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    private Long id;
    private String title;
    private String content;


}
