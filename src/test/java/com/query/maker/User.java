package com.query.maker;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "user")
public class User extends Entity
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Override
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }
}
