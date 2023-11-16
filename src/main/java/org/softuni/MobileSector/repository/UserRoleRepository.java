package org.softuni.MobileSector.repository;

import org.softuni.MobileSector.model.entity.UserRoleEntity;
import org.softuni.MobileSector.model.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

    List<UserRoleEntity> findAllByRoleIn(List<UserRoleEnum> roles);

}
