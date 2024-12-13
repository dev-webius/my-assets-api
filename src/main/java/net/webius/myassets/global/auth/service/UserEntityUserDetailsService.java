package net.webius.myassets.global.auth.service;

import lombok.RequiredArgsConstructor;
import net.webius.myassets.global.auth.domain.UserEntityUserDetails;
import net.webius.myassets.global.auth.exception.UsernameNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class UserEntityUserDetailsService implements UserDetailsService {
    private final UserDataService userDataService;

    @Override
    public UserEntityUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userDataService.findByUsername(username);
        return new UserEntityUserDetails(user);
    }

    @PreAuthorize("hasRole('USER')")
    public UserEntityUserDetails getUserDetails() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = authentication.getPrincipal();

        if (principal instanceof UserEntityUserDetails userDetails) {
            return userDetails;
        }

        return null;
    }
}
