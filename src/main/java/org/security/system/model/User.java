package org.security.system.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.security.system.service.SoftDeletable;
import org.security.system.service.impl.SoftDeleteListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users") // Tránh dùng tên 'user' vì là keyword SQL
@Getter @Setter
@EntityListeners({SoftDeleteListener.class})
public class User extends VersionedEntity implements SoftDeletable, UserDetails {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	// Định danh duy nhất (username hoặc email, tùy chính sách)
    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false)
    private String password; // lưu hash, KHÔNG bao giờ lưu plaintext

    @Column(name = "full_name", length = 255)
    private String fullName;

    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 255)
    private String avatarUrl;
    
    @Column(nullable = false)
    private boolean enabled = true; // trạng thái tài khoản

    @Column(name = "account_non_locked")
    private boolean accountNonLocked = true;

    @Column(name = "failed_login_attempts")
    private int failedLoginAttempts = 0;

    @Column(name = "lock_time")
    private LocalDateTime lockTime;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;
    
    // Bạn có thể mở rộng với các trường như: address, dateOfBirth, 2FA secret, MFA enabled...
    // Phân quyền (Role-based)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// Chuyển roles (String) thành SimpleGrantedAuthority
        return roles.stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
            .collect(Collectors.toSet());
	}
}
