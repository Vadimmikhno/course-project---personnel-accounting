package org.warehouse.repository.abstr;


import org.warehouse.model.Role;

public interface RoleDao extends DefaultDao<Role>{
    Role findByName(String name);
}
