package backend_bboss.demo.Services;

import backend_bboss.demo.Models.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Setter
@Data
@AllArgsConstructor
@Getter
//users service
public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String username;
	private String firstname;
	private String Address_users;
	@JsonIgnore
	private String password;
	private String Email;
	private String tele_users;
	private Users user;
	private Collection<? extends GrantedAuthority> authorities;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(temp -> {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(temp.getNane_roles());
			authorities.add(grantedAuthority);
		});
		return authorities;
	}

	public UserDetailsImpl(Long id, String username, String password,String firstname,String Email,String tele_users,
                           Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.firstname= firstname;
		this.password = password;
		this.Email = Email;
		this.tele_users=tele_users;
		this.authorities = authorities;
	}

	public UserDetailsImpl(Users user) {
		this.user=user;
	}

	public static UserDetailsImpl build(Users user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());

		return new UserDetailsImpl(
				user.getId(),
				user.getUsername(), 
				user.getfirstname(),
				user.getPassword(),
				user.getEmail(),
				user.getTel(),
				authorities);
	}


	public Long getId() {
		return id;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getAddress_users() {
		return Address_users;
	}

	public void setAddress_users(String address_users) {
		Address_users = address_users;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getTele_users() {
		return tele_users;
	}

	public void setTele_users(String tele_users) {
		this.tele_users = tele_users;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public void addUser(Users user) {
	}
}