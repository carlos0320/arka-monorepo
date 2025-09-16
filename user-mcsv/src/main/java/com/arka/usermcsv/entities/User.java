package com.arka.usermcsv.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "users", name = "user")
public class User extends BaseEntity{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long userId;

   private String password;

   private String name;

   private String email;

   private String phone;

   private boolean isActive = true;

   @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
   private Client client;

   @ManyToOne
   @JoinColumn(name="role_id", unique = true, nullable = false)
   private Role role;

   @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
   private Supplier supplier;
}
