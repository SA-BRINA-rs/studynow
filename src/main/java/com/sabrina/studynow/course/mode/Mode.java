package com.sabrina.studynow.course.mode;

import com.sabrina.studynow.base.BaseEntity;
import com.sabrina.studynow.institution.Institution;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "modes")
@NoArgsConstructor
@AllArgsConstructor
@Builder @Data @EqualsAndHashCode(callSuper = false)
public class Mode extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "institution_id", referencedColumnName = "id", nullable = false)
    protected Institution institution;

    protected @Column(nullable=false, length = 60) String name;
    protected @Column(nullable=false, length = 250) String description;
}
