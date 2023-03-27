package com.ftn.security.smarthomebackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="admin")
public class Admin extends User {
}
