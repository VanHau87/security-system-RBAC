package org.security.system.security;

import org.security.system.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

public class MultiSourceUserDetailsService implements UserDetailsService {
	
	private final InMemoryUserDetailsManager inMemoryUserDetailsManager;
	private final UserRepository userRepository;
    
	public MultiSourceUserDetailsService(UserRepository userRepository, PasswordEncoder encoder) {
        // Khởi tạo user in-memory ở đây!
        UserDetails user1 = User.withUsername("admin")
                .password(encoder.encode("1eN0cn4uD2H9PAIz"))
                .roles("ADMIN").build();
        UserDetails user2 = User.withUsername("user")
                .password(encoder.encode("1eN0cn4uD2H9PAIz"))
                .roles("USER").build();
        this.inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1, user2);
        this.userRepository = userRepository;
    }
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			return inMemoryUserDetailsManager.loadUserByUsername(username);
		} catch (UsernameNotFoundException e) {
			return userRepository.findByUsername(username)
		            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
		}
	}
	
}
