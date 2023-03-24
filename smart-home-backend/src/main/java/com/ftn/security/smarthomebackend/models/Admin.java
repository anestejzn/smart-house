package com.ftn.security.smarthomebackend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="admin")
public class Admin extends User {
}
