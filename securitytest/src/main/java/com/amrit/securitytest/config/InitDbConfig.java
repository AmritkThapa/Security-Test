package com.amrit.securitytest.config;

import com.amrit.securitytest.entity.Role;
import com.amrit.securitytest.entity.User;
import com.amrit.securitytest.enums.Gender;
import com.amrit.securitytest.repo.RoleRepo;
import com.amrit.securitytest.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import javax.jws.soap.SOAPBinding;
import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class InitDbConfig {
    private final RoleRepo roleRepo;
    private final UserRepo userRepo;
    @PostConstruct
    public void doEntries(){
        if (roleRepo.findAll().size()==0){
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            Role savedAdminRole=roleRepo.save(adminRole);

            Role userRole = new Role();
            userRole.setName("USER");
            roleRepo.save(userRole);

            BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();

            User user = new User();
            user.setName("Sagar Thapa");
            user.setUserName("sthapa");
            user.setPassword(encoder.encode("thapakaji"));
            user.setRoles(Arrays.asList(savedAdminRole));
            user.setGender(Gender.MALE);
            userRepo.save(user);


        }

    }

}
