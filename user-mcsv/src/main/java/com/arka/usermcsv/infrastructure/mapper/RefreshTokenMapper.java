package com.arka.usermcsv.infrastructure.mapper;

import com.arka.usermcsv.domain.model.RefreshToken;
import com.arka.usermcsv.infrastructure.entity.RefreshTokenEntity;
import com.arka.usermcsv.infrastructure.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
public class RefreshTokenMapper {
  public RefreshTokenEntity toEntity(RefreshToken token) {
    if (token == null) return null;
    RefreshTokenEntity entity = new RefreshTokenEntity();
    entity.setId(token.getId());
    entity.setToken(token.getToken());
    entity.setExpired(token.isExpired());
    entity.setCreatedAt(token.getCreatedAt());
    entity.setExpiredAt(token.getExpiredAt());

    if (token.getUserId() != null) {
      UserEntity userRef = new UserEntity();
      userRef.setUserId(token.getUserId());
      entity.setUser(userRef);
    }
    return entity;
  }

  public RefreshToken toDomain(RefreshTokenEntity entity) {
    if (entity == null) return null;
    RefreshToken token = new RefreshToken();
    token.setId(entity.getId());
    token.setToken(entity.getToken());
    token.setExpired(entity.isExpired());
    token.setCreatedAt(entity.getCreatedAt());
    token.setExpiredAt(entity.getExpiredAt());
    token.setUserId(entity.getUser() != null ? entity.getUser().getUserId() : null);
    return token;
  }
}
