package ro.utcn.sd.flav.stackoverflow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.utcn.sd.flav.stackoverflow.entity.ApplicationUser;
import ro.utcn.sd.flav.stackoverflow.exception.AccountNotFoundException;
import ro.utcn.sd.flav.stackoverflow.repository.AccountRepository;

import java.util.Collections;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationUserDetailsService implements UserDetailsService{

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ApplicationUser user = (ApplicationUser) accountRepository.findApplicationUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Unknown user"));

        return new User(user.getUsername(), user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("USER")));
    }

    @Transactional
    public ApplicationUser loadCurrentUser(){

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return (ApplicationUser) accountRepository.findApplicationUserByUsername(username).orElseThrow(AccountNotFoundException::new);
    }
}
