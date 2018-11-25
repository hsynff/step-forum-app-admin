package service;

import model.Topic;

import java.util.List;

public interface TopicService {

    List<Topic> getActiveOrPendingTopics(int status);

    boolean deleteTopic(int id);

    boolean activateTopic(int id, String title, String desc);

    Topic getTopicById(int id);
}
