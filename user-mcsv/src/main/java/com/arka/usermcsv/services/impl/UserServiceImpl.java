package com.arka.usermcsv.services.impl;

import com.arka.usermcsv.dtos.UserRequestDto;
import com.arka.usermcsv.dtos.UserResponseDto;
import com.arka.usermcsv.entities.*;
import com.arka.usermcsv.mappers.ClientMapper;
import com.arka.usermcsv.mappers.RoleMapper;
import com.arka.usermcsv.mappers.SupplierMapper;
import com.arka.usermcsv.mappers.UserMapper;
import com.arka.usermcsv.repositories.ClientRepository;
import com.arka.usermcsv.repositories.RoleRepository;
import com.arka.usermcsv.repositories.SupplierRepository;
import com.arka.usermcsv.repositories.UserRepository;
import com.arka.usermcsv.services.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
   private final UserRepository userRepository;
   private final RoleRepository roleRepository;
   private final PasswordEncoder passwordEncoder;
   private final ClientRepository clientRepository;
   private final SupplierRepository supplierRepository;

   @Transactional
   public void createUser(UserRequestDto userRequestDto) {

      User newUser = UserMapper.toUser(userRequestDto);
      // hash password before saving
      String encodedPassword = passwordEncoder.encode(userRequestDto.getPassword());
      newUser.setPassword(encodedPassword);

      // Fetch role from DB
      Role userRole = roleRepository.findByRoleType(
              RoleTypes.fromString(userRequestDto.getRole())
      ).orElseThrow(() -> new RuntimeException("Role not found"));
      newUser.setRole(userRole);

      userRepository.save(newUser);

      // 4. Handle client if provided
      if (userRequestDto.getClient() != null) {
         Client client = ClientMapper.toClient(userRequestDto.getClient());
         client.setUser(newUser);
         newUser.setClient(client);
      }

      // 5. Handle supplier if provided
      if (userRequestDto.getSupplier() != null) {
         Supplier supplier = SupplierMapper.toSupplier(userRequestDto.getSupplier());
         supplier.setUser(newUser);
         newUser.setSupplier(supplier);
      }

      userRepository.save(newUser);
   }
}
