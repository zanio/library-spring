package com.book.library.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Setter
@Getter
@ToString
public class
Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String name;

    private Boolean active; // use to enable and disable categories;

    @OneToMany(orphanRemoval = true, fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<Book> books;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    public Category() {
        this.active = true;
    }

    public Category(String name) {
        this.name = name;
        this.active = true;
    }
}
