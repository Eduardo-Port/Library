package io.github.Eduardo_Port.library.model;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.UUID;

@Entity
@Table
@Data
public class Account {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID id;
    @Column
    private String login;
    @Column
    private String password;
    @Type(ListArrayType.class)
    @Column(name = "roles", columnDefinition = "varchar[]")
    private List<String> roles;
}
