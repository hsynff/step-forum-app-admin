package model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
public class Topic {

    private int id;
    private String title;
    private String description;
    private LocalDateTime shareDate;
    private int viewCount;
    private int commentsCount;
    private int status;
    private User user;
    private List<Comment> commentList;

    public Topic() {
        commentList = new ArrayList<>();
    }

    public void addComment(Comment comment) {
        commentList.add(comment);
    }

}