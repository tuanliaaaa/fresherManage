package com.g11.FresherManage.dataInitializer;
import com.g11.FresherManage.entity.Role;
import com.g11.FresherManage.entity.Working;
import com.g11.FresherManage.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (roleRepository.count() == 0) {
            roleRepository.save(new Role(1,"ROLE_USER"));
            roleRepository.save(new Role(2,"ROLE_ADMIN"));
            roleRepository.save(new Role(3,"ROLE_MENTOR"));
            roleRepository.save(new Role(4,"ROLE_CENTERDIRECTOR"));
            roleRepository.save(new Role(5,"ROLE_MARKETDIRECTOR"));
        }
    }
}
