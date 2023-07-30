package com.sabrina.studynow.course;

import com.sabrina.studynow.base.BaseEntity;
import com.sabrina.studynow.converter.StringListConverter;
import com.sabrina.studynow.course.mode.Mode;
import com.sabrina.studynow.institution.Institution;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "courses")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "course_type", discriminatorType = DiscriminatorType.STRING)
@NoArgsConstructor
@AllArgsConstructor
@Builder @Data @EqualsAndHashCode(callSuper = false)
public class Course extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "institution_id", referencedColumnName = "id", nullable = false)
    protected Institution institution;

    @ManyToOne
    @JoinColumn(name = "mode_id", referencedColumnName = "id", nullable = false)
    protected Mode mode;

    @Column(name = "course_type", insertable = false, updatable = false)
    private String institutionType;

    protected Integer averageRate;

    protected @Column(nullable=false, length = 100) String name;
    protected @Column(nullable=false) double price;
    protected @Column(nullable=false) LocalDate startDate;
    protected @Column(nullable=false) LocalDate endDate;
    protected @Column(nullable=false, length = 250) String subject;
    protected @Column(nullable=false, length = 500) String description;
    protected @Column byte[] thumbnail;

    @Convert(converter = StringListConverter.class)
    protected List<String> tags;
}
