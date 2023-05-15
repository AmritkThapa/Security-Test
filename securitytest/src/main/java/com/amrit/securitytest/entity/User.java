package com.amrit.securitytest.entity;

import com.amrit.securitytest.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users",
        uniqueConstraints = @UniqueConstraint(columnNames = "user_name",name = "unique_users"))
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name",length = 20)
    private String name;

    @Column(name = "user_name")
    private  String userName;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "fk_user")),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "fk_role")))
    private List<Role> roles;

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> grantedAuthorities=new ArrayList<>();
//        for (Role role:this.roles){
//            SimpleGrantedAuthority simpleGrantedAuthority= new SimpleGrantedAuthority(role.getName());
//            grantedAuthorities.add(simpleGrantedAuthority);
//        }
//        return grantedAuthorities;
//    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword(){
        return this.password;
    }
}
