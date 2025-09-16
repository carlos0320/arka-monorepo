package com.arka.usermcsv.mappers;

import com.arka.usermcsv.dtos.RoleDto;
import com.arka.usermcsv.entities.Role;
import com.arka.usermcsv.entities.RoleTypes;

public class RoleMapper  {
   public static Role toRole(RoleDto roleDto){
      Role role = new Role();
      role.setRoleType(RoleTypes.fromString(roleDto.getRole()));
      return role;
   }

   public static RoleDto toRoleDto(Role role){
      RoleDto roleDto = new RoleDto();
      roleDto.setRole(String.valueOf(role.getRoleType()).toLowerCase());
      return roleDto;
   }
}
