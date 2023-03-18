package com.blogger.blogs.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@SequenceGenerator(name="id_seq", allocationSize=1)
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "id_seq")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String comment;


}
