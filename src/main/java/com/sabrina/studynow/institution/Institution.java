package com.sabrina.studynow.institution;

import com.sabrina.studynow.base.BaseEntity;
import com.sabrina.studynow.converter.StringListConverter;
import com.sabrina.studynow.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "institutions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "institution_type", discriminatorType = DiscriminatorType.STRING)
@NoArgsConstructor
@AllArgsConstructor
@Builder @Data @EqualsAndHashCode(callSuper = false)
public class Institution extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    protected User user;

    @Column(name = "institution_type", insertable = false, updatable = false)
    private String institutionType;

    @Transient
    protected Integer averageRate;

    protected @Column(nullable = false, length = 100) String name;
    protected @Column(nullable = false, length = 20) String phone;
    protected @Column(nullable = false, length = 250) String description;
    protected @Column byte[] thumbnail;

    @Convert(converter = StringListConverter.class)
    protected List<String> tags;

    public boolean isThumbnailPresent() {
        return this.thumbnail != null && this.thumbnail.length > 0;
    }

    public boolean isEnabled() {
        return true;
    }
}
