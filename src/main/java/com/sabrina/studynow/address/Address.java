package com.sabrina.studynow.address;

import com.sabrina.studynow.base.BaseEntity;
import com.sabrina.studynow.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "addresses", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"zip"})
})
@NoArgsConstructor
@AllArgsConstructor
@Builder @Data @EqualsAndHashCode(callSuper = true)
public class Address extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(40)")
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    private @Column(nullable=false, length=120) String address1;
    private @Column(nullable=false, length=80) String address2;
    private @Column(nullable=false, length=90) String city;
    private @Column(nullable=false, length=60) String state;
    private @Column(nullable=false, length=15) String zip;
    private @Column(nullable=false, length=50) String country;
}
