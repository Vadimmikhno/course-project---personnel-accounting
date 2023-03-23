package org.warehouse.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.warehouse.model.Role;
import org.warehouse.repository.abstr.RoleDao;
import org.warehouse.service.abstr.RoleService;

import java.util.List;
import java.util.Optional;

public class RoleServiceImpl implements RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
    private RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Optional<Role> findById(Long id) {
        return Optional.ofNullable(roleDao.findById(id));
    }

    @Override
    public Optional<List<Role>> findAll() {
        return Optional.ofNullable(roleDao.getAll());
    }

    @Override
    public Optional<List<Role>> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public boolean update(Role o) {
        return false;
    }

    @Override
    public boolean deleteByKey(Long id) {
        return false;
    }

    @Override
    public boolean save(Role o) {
        return false;
    }
}
