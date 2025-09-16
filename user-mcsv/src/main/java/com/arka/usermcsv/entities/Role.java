package com.arka.usermcsv.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "users", name = "role")
public class Role {
   @Id
   @GeneratedValue(strategy =  GenerationType.IDENTITY)
   private Long roleId;

   @Enumerated(EnumType.STRING)
   @Column(name = "role_type", nullable = false, unique = true)
   private RoleTypes roleType;

   @OneToMany(mappedBy = "role")
   private List<User> user;
}
