package com.snd.fileupload.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @Size(max = 15)
    @Column(name = "username", nullable = false, length = 15)
    private String username;

    @Size(max = 6)
    @Column(name = "role", length = 6)
    @Enumerated(EnumType.STRING)
    private UserRole role;
}