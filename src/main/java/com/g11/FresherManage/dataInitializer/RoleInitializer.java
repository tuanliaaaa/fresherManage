package com.g11.FresherManage.dataInitializer;
import com.g11.FresherManage.entity.Role;
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
            roleRepository.save(new Role(1,"USER"));
            roleRepository.save(new Role(2,"ADMIN"));
            roleRepository.save(new Role(3,"MENTOR"));
            roleRepository.save(new Role(4,"CENTERDIRECTOR"));
            roleRepository.save(new Role(5,"MARKETDIRECTOR"));
        }
    }
}
