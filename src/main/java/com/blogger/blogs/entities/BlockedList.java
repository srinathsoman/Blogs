package com.blogger.blogs.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@SequenceGenerator(name="blocked_id_generator",sequenceName="post_seq", allocationSize=1)
@Data
public class BlockedList {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "blocked_id_generator")
    private Long id;

    private Long userId;

    private Long blockedUserId;
}
