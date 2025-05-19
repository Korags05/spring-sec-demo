package org.kunal.spring_sec_demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    // Add these fields for OAuth support
    private String provider; // "local", "google", "github", etc.
    private String providerId; // ID from OAuth provider
}