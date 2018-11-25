package service;

import exceptions.UserCredentialsException;
import model.User;

import java.util.List;

public interface UserService {

    User loginUser(String email, String password) throws UserCredentialsException;

    List<User> getActiveOrBlockedUsers(int status);

    boolean activateUser(int id);

    boolean blockUser(int id);

}
