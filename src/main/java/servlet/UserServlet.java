package servlet;

import constants.NavigationConstants;
import constants.UserConstants;
import dao.UserDao;
import dao.UserDaoImpl;
import exceptions.UserCredentialsException;
import model.User;
import org.json.JSONArray;
import service.UserService;
import service.UserServiceImpl;
import util.CryptoUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserServlet", urlPatterns = "/us")
public class UserServlet extends HttpServlet {

    private UserService userService = new UserServiceImpl(new UserDaoImpl());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = null;

        if (request.getParameter("action") != null) {
            action = request.getParameter("action");
        } else {
            response.sendRedirect("/");
            return;
        }

        if (action.equals("getAllActiveUsers")) {
            List<User> activeUsers = userService.getActiveOrBlockedUsers(UserConstants.USER_STATUS_ACTIVE);
            JSONArray jsonArray = new JSONArray(activeUsers);
            response.getWriter().write(jsonArray.toString());

        } else if (action.equals("getAllBlockedUsers")) {
            List<User> blockedUsers = userService.getActiveOrBlockedUsers(UserConstants.USER_STATUS_INACTIVE);
            JSONArray jsonArray = new JSONArray(blockedUsers);
            response.getWriter().write(jsonArray.toString());

        } else if (action.equals("blockUserById")) {
            int idUser = Integer.parseInt(request.getParameter("id"));
            boolean result = userService.blockUser(idUser);

        } else if (action.equals("activateUserById")) {
            int idUser = Integer.parseInt(request.getParameter("id"));
            boolean result = userService.activateUser(idUser);
        } else if (action.equals("doLogin")){
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            try {
                User user = userService.loginUser(email, CryptoUtil.inputToHash(password));
                request.getSession().setAttribute("user", user);
                response.sendRedirect("/");
            } catch (UserCredentialsException e) {
                request.setAttribute("message", e.getMessage());
                request.getRequestDispatcher(NavigationConstants.PAGE_LOGIN).forward(request, response);
            }
        } else if (action.equals("logout")) {
            HttpSession session = request.getSession();
            session.removeAttribute("user");
            session.invalidate();
            response.sendRedirect("/ns?action=login");
        }


    }
}