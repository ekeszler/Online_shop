package Teme_sping.Online_shop.repositories;

import Teme_sping.Online_shop.entities.Role;
import Teme_sping.Online_shop.entities.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleType(RoleType roleType);
}
