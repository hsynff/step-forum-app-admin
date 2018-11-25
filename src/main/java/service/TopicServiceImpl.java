package service;

import dao.TopicDao;
import dao.TopicDaoImpl;
import model.Topic;

import java.util.List;

public class TopicServiceImpl implements TopicService {

    private TopicDao topicDao;

    public TopicServiceImpl(TopicDao topicDao) {
        this.topicDao = topicDao;
    }


    @Override
    public List<Topic> getActiveOrPendingTopics(int status) {
        return topicDao.getActiveOrPendingTopics(status);
    }

    @Override
    public boolean deleteTopic(int id) {
        return topicDao.deleteTopic(id);
    }

    @Override
    public boolean activateTopic(int id, String title, String desc) {
        return topicDao.activateTopic(id, title,  desc);
    }

    @Override
    public Topic getTopicById(int id) {
        return topicDao.getTopicById(id);
    }
}
