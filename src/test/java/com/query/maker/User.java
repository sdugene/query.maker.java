package com.query.maker;

import org.junit.Test;

import javax.persistence.*;

import static org.junit.Assert.assertEquals;

@javax.persistence.Entity
@Table(name = "user")
public class User extends Entity
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Override
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
}