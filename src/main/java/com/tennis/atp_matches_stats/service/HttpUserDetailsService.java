package com.tennis.atp_matches_stats.service;

import static com.tennis.atp_matches_stats.util.UserRoles.ROLE_USER;

import com.tennis.atp_matches_stats.domain.User;
import com.tennis.atp_matches_stats.model.HttpUserDetails;
import com.tennis.atp_matches_stats.repos.UserRepository;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class HttpUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public HttpUserDetailsService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public HttpUserDetails loadUserByUsername(final String username) {
        final User user = userRepository.findByEmailIgnoreCase(username);
        if (user == null) {
            log.warn("user not found: {}", username);
            throw new UsernameNotFoundException("User " + username + " not found");
        }
        return new HttpUserDetails(user.getId(), username, user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(ROLE_USER)));
    }

}
