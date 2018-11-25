package service;

import dao.UserDao;
import exceptions.UserCredentialsException;
import model.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User loginUser(String email, String password) throws UserCredentialsException {
        return userDao.loginUser(email, password);
    }

    @Override
    public List<User> getActiveOrBlockedUsers(int status) {
        return userDao.getActiveOrBlockedUsers(status);
    }

    @Override
    public boolean activateUser(int id) {
        return userDao.activateUser(id);
    }

    @Override
    public boolean blockUser(int id) {
        return userDao.blockUser(id);
    }

}
