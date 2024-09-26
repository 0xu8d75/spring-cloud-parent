package com.u8d75.user.service;

import com.u8d75.core.base.service.impl.BaseService;
import com.u8d75.user.dao.UserDao;
import com.u8d75.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * create by  develop team
 */
@Service("userService")
public class UserServiceImpl extends BaseService<User> {

    @Autowired
    private UserDao userDao;

    @SuppressWarnings("unchecked")
    @Override
    public UserDao getDao() {
        return userDao;
    }
}
