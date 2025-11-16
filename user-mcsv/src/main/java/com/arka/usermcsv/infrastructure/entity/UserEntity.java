package com.arka.usermcsv.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "users", name = "user")
public class UserEntity extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long userId;

  private String password;

  private String name;

  private String address;

  private String email;

  private String phone;

  private boolean isActive = true;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  private ClientEntity client;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
          name="user_roles",
          schema = "users",
          joinColumns = @JoinColumn(name="user_id"),
          inverseJoinColumns = @JoinColumn(name="role_id")
  )
  private Set<RoleEntity> roles = new HashSet<>();

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  private SupplierEntity supplier;


  @OneToMany(mappedBy = "user", cascade =  CascadeType.ALL)
  private List<RefreshTokenEntity> refreshToken;
}
