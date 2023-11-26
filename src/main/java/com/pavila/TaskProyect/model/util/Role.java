package com.pavila.TaskProyect.model.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum Role {

    USER(Arrays.asList(
            RolePermission.READ_ALL_TASKS,
            RolePermission.READ_ONE_TASK,
            RolePermission.CREATE_ONE_TASK,
            RolePermission.UPDATE_ONE_TASK,
            RolePermission.DELETE_ONE_TASK,
            RolePermission.COMPLETED_ONE_TASK,

            RolePermission.READ_MY_PROFILE
    )),

    ADMINISTRATOR(Arrays.asList(
            RolePermission.READ_ALL_USERS,
            RolePermission.READ_MY_PROFILE

    ));

    private List<RolePermission> permissions;



    public void setPermissions(List<RolePermission> permissions) {
        this.permissions = permissions;
    }


}
