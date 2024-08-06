package com.g11.FresherManage.dataInitializer;
import com.g11.FresherManage.entity.Account;
import com.g11.FresherManage.entity.AccountRole;
import com.g11.FresherManage.entity.Role;
import com.g11.FresherManage.entity.Working;
import com.g11.FresherManage.repository.AccountRepository;
import com.g11.FresherManage.repository.AccountRoleRepository;
import com.g11.FresherManage.repository.RoleRepository;
import com.g11.FresherManage.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class RoleInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountRoleRepository accountRoleRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (roleRepository.count() == 0&&accountRepository.count() == 0&&accountRoleRepository.count() == 0) {
            Role roleFresher=roleRepository.save(new Role(1,"ROLE_FRESHER"));
            Role roleAdmin=roleRepository.save(new Role(2,"ROLE_ADMIN"));
            Role roleMentor =roleRepository.save(new Role(3,"ROLE_MENTOR"));
            roleRepository.save(new Role(4,"ROLE_CENTERDIRECTOR"));
            roleRepository.save(new Role(5,"ROLE_MARKETDIRECTOR"));
            Account accountAdmin=accountRepository.save(new Account(3,"admin","$2a$10$tEWOJ6PlKtjZTqhIZg1vl.3opnE.FusBZpWgeVi3ouuQ8y4tyra06","/tuan.img","Lương Nhật","Tuấn","nhattuan44t@gmail.com","0379230864","","active", LocalDate.parse("2024-07-03"),LocalDate.parse("2024-07-02"),""));
            Account accountMentor=accountRepository.save(new Account(2,"mentor","$2a$10$tEWOJ6PlKtjZTqhIZg1vl.3opnE.FusBZpWgeVi3ouuQ8y4tyra06","/tuan.img","Lương Nhật","Tuấn","nhattuan44t@gmail.com","0379230864","MENTOR","active",LocalDate.parse("2024-07-03"),LocalDate.parse("2024-07-02"),""));
            Account accountFresher=accountRepository.save(new Account(1,"nhattuan44t@gmail.com","$2a$10$tEWOJ6PlKtjZTqhIZg1vl.3opnE.FusBZpWgeVi3ouuQ8y4tyra06","/tuan.img","Lương Nhật","Tuấn","nhattuan44t@gmail.com","0379230864","FRESHER","active",LocalDate.parse("2024-07-03"),LocalDate.parse("2024-07-02"),""));

            accountRoleRepository.save(new AccountRole(3,roleFresher,accountFresher));
            accountRoleRepository.save(new AccountRole(2,roleAdmin,accountMentor));
            accountRoleRepository.save(new AccountRole(1,roleAdmin,accountAdmin));
//            for (int i = 1; i <= 1_000_000; i++) {
//                String email = "user" + i + "@example.com";
//                String phone = "037923" + String.format("%04d", i % 10000);
//                LocalDate createdAt = getRandomDateInJuly2024(1,10);
//                LocalDate endAt = getRandomDateInJuly2024(11,32);
//
//                Account d = new Account(
//                        i,
//                        "fresher" + i,
//                        "$2a$10$tEWOJ6PlKtjZTqhIZg1vl.3opnE.FusBZpWgeVi3ouuQ8y4tyra06",
//                        "/tuan" + i + ".img",
//                        "Lương Nhật",
//                        "Tuấn",
//                        email,
//                        phone,
//                        "FRESHER",
//                        "active",
//                        createdAt,
//                        endAt,
//                        ""
//                );
//
//                accountRepository.save(d);
//            }
        }



    }
    private LocalDate getRandomDateInJuly2024(int a,int b) {
        int day = ThreadLocalRandom.current().nextInt(a, b); // Ngày trong tháng 7 từ 1 đến 31
        return LocalDate.of(2024, Month.JULY, day);
    }
}
