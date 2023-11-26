package com.pavila.TaskProyect.model.dto;

import java.util.Date;

public interface TaskResponse {
    Long getId();
    String getTitle();
    String getDescription();
    Date getCreatedDate();
    String getFinishedDate();
    String getPriority();
    boolean getCompleted();
    String getAssignee();
    String getCategory();


}
