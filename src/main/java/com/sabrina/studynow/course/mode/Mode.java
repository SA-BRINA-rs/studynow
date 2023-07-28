package com.sabrina.studynow.course.mode;

import com.sabrina.studynow.base.BaseEntity;
import com.sabrina.studynow.institution.Institution;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "modes")
@NoArgsConstructor
@AllArgsConstructor
@Builder @Data @EqualsAndHashCode(callSuper = false)
public class Mode extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "institution_id", referencedColumnName = "id", nullable = false)
    protected Institution institution;

    private @Column(nullable=false, length = 60) String name;
    private @Column(nullable=false, length = 250) String description;
}
