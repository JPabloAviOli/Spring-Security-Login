package com.pavila.TaskProyect.model.dto;

import com.pavila.TaskProyect.model.util.Role;
import com.pavila.TaskProyect.model.util.RolePermission;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
public class UserProfileResponse implements Serializable {

    private String username;
    private String name;
    private Role role;
    private boolean credentialsNonExpired;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private List<RolePermission> authorities;
    private List<String> taskName;


}
