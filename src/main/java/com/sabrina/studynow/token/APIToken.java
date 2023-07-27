package com.sabrina.studynow.token;

import com.sabrina.studynow.base.BaseEntity;
import com.sabrina.studynow.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Entity
@Table(name = "api_tokens")
@NoArgsConstructor
@AllArgsConstructor
@Builder @Data @EqualsAndHashCode(callSuper = false)
public class APIToken extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(40)")
    protected String id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    protected User user;

    protected @Column(nullable=false) LocalDate expirationDate;
    protected @Column(nullable=false) boolean active;
    protected @Column(nullable=false) boolean neverExpires;

    public boolean isExpired() {
        return !neverExpires && expirationDate.isBefore(LocalDate.now());
    }
}
