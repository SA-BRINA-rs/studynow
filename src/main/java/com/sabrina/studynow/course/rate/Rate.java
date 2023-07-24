package com.sabrina.studynow.course.rate;

import com.sabrina.studynow.base.BaseEntity;
import com.sabrina.studynow.course.Course;
import com.sabrina.studynow.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rates")
@NoArgsConstructor
@AllArgsConstructor
@Builder @Data @EqualsAndHashCode(callSuper = false)
public class Rate extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false)
    private Course course;

    private @Column(nullable = false) int rate = 1;
    private @Column(nullable = false) String comment;

}
