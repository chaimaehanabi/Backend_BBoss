package backend_bboss.demo.Security;
import backend_bboss.demo.Security.JWT.JwtAuthTokenFilter;
import backend_bboss.demo.Services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Bean
	public JwtAuthTokenFilter authenticationJwtTokenFilter() {
		return new JwtAuthTokenFilter();
	}
	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder
				.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		         http.cors().and().csrf().disable()
			    .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			    .authorizeRequests()
				.antMatchers("/api/BBBos/**").permitAll()
				.antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/register").permitAll()
						 //consulter un produit par son id
		.antMatchers(HttpMethod.GET,"/prodact/BBBos/**").permitAll();

		//ajouter un produit
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/prodact/BBBos/**").hasAuthority("ADMIN");

		//modifier un produit
		http.authorizeRequests().antMatchers(HttpMethod.PUT,"/prodact/BBBos/**").hasAuthority("ADMIN");

		//supprimer un produit
		http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/prodact/BBBos**").hasAuthority("ADMIN");

		http.authorizeRequests().anyRequest().authenticated();
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}