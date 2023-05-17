package com.java.everyboard.user;

import com.java.everyboard.audit.Auditable;
import com.java.everyboard.constant.ActiveStatus;
import com.java.everyboard.constant.LoginType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
public class User extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column
    private String profileUrl;

    @Column
    private String profileKey;

    @Column
    @Enumerated(EnumType.STRING)
    private LoginType loginType = LoginType.BASIC;

    @Column
    @Enumerated(EnumType.STRING)
    private ActiveStatus activeStatus = ActiveStatus.ACTIVE;
}
