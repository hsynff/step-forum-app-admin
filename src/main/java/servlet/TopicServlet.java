package servlet;

import constants.NavigationConstants;
import constants.TopicConstants;
import dao.TopicDaoImpl;
import model.Topic;
import org.json.JSONArray;
import service.TopicService;
import service.TopicServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "TopicServlet", urlPatterns = "/ts")
public class TopicServlet extends HttpServlet {

    private TopicService topicService = new TopicServiceImpl(new TopicDaoImpl());

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

        if (action.equals("getAllActiveTopics")) {
            List<Topic> activeTopics = topicService.getActiveOrPendingTopics(TopicConstants.TOPIC_STATUS_ACTIVE);
            JSONArray jsonArray = new JSONArray(activeTopics);
            response.getWriter().write(jsonArray.toString());

        } else if (action.equals("getAllPendingTopics")) {
            List<Topic> pendingTopics = topicService.getActiveOrPendingTopics(TopicConstants.TOPIC_STATUS_INACTIVE);
            JSONArray jsonArray = new JSONArray(pendingTopics);
            response.getWriter().write(jsonArray.toString());

        } else if (action.equals("deleteTopicById")) {
            int idTopic = Integer.parseInt(request.getParameter("id"));
            boolean result = topicService.deleteTopic(idTopic);
        } else if (action.equals("activateTopicById")) {
            int idTopic = Integer.parseInt(request.getParameter("id"));
            String title = request.getParameter("title");
            String desc = request.getParameter("desc");
            boolean result = topicService.activateTopic(idTopic, title, desc);
            if (!result){
                throw  new ServletException();
            }
        } else if (action.equals("getTopicById")){
            int topicId = Integer.parseInt(request.getParameter("id"));
            Topic topic = topicService.getTopicById(topicId);
            System.out.println(topic);
            if (topic == null){
                throw new ServletException();
            }
            request.setAttribute("topic", topic);
            request.getRequestDispatcher(NavigationConstants.FRAGMENT_TOPIC).forward(request, response);
        }
    }
}
