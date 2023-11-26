package com.pavila.TaskProyect.model.util;

import lombok.Getter;

@Getter
public enum RolePermission {

    READ_ALL_TASKS,
    READ_ONE_TASK,
    CREATE_ONE_TASK,
    UPDATE_ONE_TASK,
    DELETE_ONE_TASK,
    COMPLETED_ONE_TASK,

    READ_MY_PROFILE,

    READ_ALL_USERS
}
