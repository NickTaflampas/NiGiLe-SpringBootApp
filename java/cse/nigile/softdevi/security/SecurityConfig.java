package cse.nigile.softdevi.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired DataSource dataSource;
	@Autowired LoginSuccessHandler loginSuccessHandler;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery("select username,password,enabled from app_users where username = ?")
			.authoritiesByUsernameQuery("select app_user_username, roles_role_name" + " from app_users_roles where app_user_username = ?");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
			.antMatchers("/admin").hasAuthority("ADMIN")
			.antMatchers("/user").hasAnyAuthority("USER", "ADMIN")
			.antMatchers("/","/login","/register").permitAll()
			.and().formLogin()
				.loginPage("/login")
				.successHandler(loginSuccessHandler)
				.usernameParameter("username")
				.passwordParameter("password")
			.and().logout()
				.logoutUrl("/logout")
				.deleteCookies("JSESSIONID")
				.addLogoutHandler(new SecurityContextLogoutHandler())
			.and()
				.exceptionHandling()
				.accessDeniedPage("/forbidden");
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
}
