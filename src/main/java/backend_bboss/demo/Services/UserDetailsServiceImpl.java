package backend_bboss.demo.Services;
import backend_bboss.demo.Models.Users;
import backend_bboss.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	 UserRepository  UserRepositoryimpl;
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = UserRepositoryimpl.findByUsername(username);
		System.out.println(user.getUsername() + "" +user.getPassword());
		UserDetailsImpl userPrincipal = new UserDetailsImpl(user);
		return userPrincipal;
	}
	@Transactional
	public void addUser(Users user){
		UserRepositoryimpl.save(user);
	}
}