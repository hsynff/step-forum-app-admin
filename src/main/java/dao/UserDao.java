package dao;

import exceptions.UserCredentialsException;
import model.User;

import java.util.List;

public interface UserDao {

    User loginUser(String email, String password) throws UserCredentialsException;

    List<User> getActiveOrBlockedUsers(int status);

    boolean activateUser(int id);

    boolean blockUser(int id);

}
