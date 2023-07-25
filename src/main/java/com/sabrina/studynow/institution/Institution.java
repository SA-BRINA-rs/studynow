package com.sabrina.studynow.institution;

import com.sabrina.studynow.base.BaseEntity;
import com.sabrina.studynow.converter.StringListConverter;
import com.sabrina.studynow.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "institutions")
@NoArgsConstructor
@AllArgsConstructor
@Builder @Data @EqualsAndHashCode(callSuper = false)
public class Institution extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    private User user;

    private @Column(nullable = false, length = 100) String name;
    private @Column(nullable = false, length = 20) String phone;
    private @Column(nullable = false, length = 250) String description;
    protected @Column byte[] profilePicture;

    @Convert(converter = StringListConverter.class)
    private List<String> tags;
}
