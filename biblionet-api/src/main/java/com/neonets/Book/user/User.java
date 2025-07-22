package com.neonets.Book.user;

import com.neonets.Book.role.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.IdGeneratorType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "_user")
@EntityListeners(AuditingEntityListener.class)
public class User{

    //User information attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private LocalDate dateOfBirth;
    private String password;

    @ManyToMany(
            fetch = FetchType.EAGER
    )

    private List<Role> roles;

    //Account information
    @CreatedDate
    @Column(nullable = false, updatable = false) // This field cannot be null and cannot be updated.
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDate lastModifiedDate;


    private String fullName()
    {
        return firstName + " " + lastName;
    }
}
