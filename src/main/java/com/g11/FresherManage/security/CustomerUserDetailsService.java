    package com.g11.FresherManage.security;

    import com.g11.FresherManage.entity.Account;
    import com.g11.FresherManage.repository.AccountRepository;
    import com.g11.FresherManage.exception.account.UsernameNotFoundException;
    import lombok.RequiredArgsConstructor;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.security.core.userdetails.User;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.stereotype.Component;

    import java.util.*;
    import java.util.stream.Collectors;


    @Component
    @RequiredArgsConstructor
    public class CustomerUserDetailsService implements UserDetailsService {

        private final AccountRepository accountRepository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Account user = accountRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException());
            List<String> userRoles = accountRepository.findRolesByUsername(user.getUsername());
            // Tạo danh sách quyền từ role của Employee
            List<GrantedAuthority> authorities = userRoles.stream()
                    .map(userRole -> new SimpleGrantedAuthority( userRole))
                    .collect(Collectors.toList());

            return User.withUsername(user.getUsername())
                    .password(user.getPassword())
                    .authorities(authorities)
                    .build();

        }


    }