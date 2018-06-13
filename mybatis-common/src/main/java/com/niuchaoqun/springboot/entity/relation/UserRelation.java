package com.niuchaoqun.springboot.entity.relation;


import com.niuchaoqun.springboot.entity.Role;
import com.niuchaoqun.springboot.entity.User;
import com.niuchaoqun.springboot.entity.UserDetail;
import com.niuchaoqun.springboot.entity.UserProfile;
import lombok.Data;

@Data
public class UserRelation extends User {
    private Role role;

    private UserProfile profile;

    private UserDetail detail;
}
