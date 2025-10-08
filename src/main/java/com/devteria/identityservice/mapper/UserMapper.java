package com.devteria.identityservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.devteria.identityservice.dto.request.UserCreationRequest;
import com.devteria.identityservice.dto.request.UserUpdateRequest;
import com.devteria.identityservice.dto.response.UserResponse;
import com.devteria.identityservice.entity.UsersEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UsersEntity toUser(UserCreationRequest request);

    UserResponse toUserResponse(UsersEntity user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget UsersEntity user, UserUpdateRequest request);
}
