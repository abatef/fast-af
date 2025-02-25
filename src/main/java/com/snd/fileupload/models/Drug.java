package com.snd.fileupload.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "drugs")
@AllArgsConstructor
@NoArgsConstructor
public class Drug {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 250)
    @NotNull
    @Column(name = "name", nullable = false, length = 250)
    private String name;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "drug", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "created_by")
    @JsonIgnore
    private User createdBy;

    @Column(name = "created_at")
    @CreationTimestamp
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;

}