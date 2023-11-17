package com.goalsapi.goalsapi.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "goal")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String description;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @JsonIgnore
    @OneToMany(cascade = { CascadeType.REMOVE,
            CascadeType.MERGE }, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "goal")
    private List<Habit> habits;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

}
