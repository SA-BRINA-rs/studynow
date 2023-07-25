package com.sabrina.studynow.course;

import com.sabrina.studynow.base.BaseEntity;
import com.sabrina.studynow.converter.StringListConverter;
import com.sabrina.studynow.course.mode.Mode;
import com.sabrina.studynow.institution.Institution;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "courses")
@NoArgsConstructor
@AllArgsConstructor
@Builder @Data @EqualsAndHashCode(callSuper = false)
public class Course extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "institution_id", referencedColumnName = "id", nullable = false)
    private Institution institution;

    @ManyToOne
    @JoinColumn(name = "mode_id", referencedColumnName = "id", nullable = false)
    private Mode mode;

    @Transient
    private Integer averageRate;

    private @Column(nullable=false, length = 100) String name;
    private @Column(nullable=false) double price;
    private @Column(nullable=false) LocalDate startDate;
    private @Column(nullable=false) LocalDate endDate;
    private @Column(nullable=false, length = 250) String subject;
    private @Column(nullable=false, length = 500) String description;
    protected @Column byte[] profilePicture;

    @Convert(converter = StringListConverter.class)
    private List<String> tags;
}
