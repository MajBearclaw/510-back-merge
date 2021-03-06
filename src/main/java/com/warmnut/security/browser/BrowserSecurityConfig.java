package com.warmnut.security.browser;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import com.warmnut.security.browser.handler.MyLoginUrlAuthenticationEntryPoint;
import com.warmnut.security.code.AuthorizeConfigManager;
import com.warmnut.security.code.FormAuthenticationConfig;
import com.warmnut.security.code.properties.SecurityConstants;
import com.warmnut.security.code.properties.SecurityProperties;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private UserDetailsService userDetailsService;



	@Autowired
	private InvalidSessionStrategy invalidSessionStrategy;

	@Autowired
	private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

	@Autowired
	private LogoutSuccessHandler logoutSuccessHandler;

	@Autowired
	private AuthorizeConfigManager authorizeConfigManager;

	@Autowired
	private FormAuthenticationConfig formAuthenticationConfig;

	@Autowired
	protected AuthenticationSuccessHandler myAuthenticationSuccessHandler;

	/**
	 * ??????????????????token???????????????
	 * 
	 * @return
	 */
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
		// ????????????????????????????????????
		// tokenRepository.setCreateTableOnStartup(true);
		return tokenRepository;

	}

	@Override /** ???????????? */
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		// remember me
		auth.eraseCredentials(false);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// ?????????????????????
		formAuthenticationConfig.configure(http);

		http

				// // ??????????????????
				// .apply(earthchenSocialConfig)
				// .and()
				.rememberMe().tokenRepository(persistentTokenRepository())
				.tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
				.userDetailsService(userDetailsService).and()
				// session ??????
				.sessionManagement().invalidSessionStrategy(invalidSessionStrategy)
				.maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())
				.maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())
				.expiredSessionStrategy(sessionInformationExpiredStrategy)
				// ??????session????????????????????????url
				// .invalidSessionUrl("/session/invalid")
				// // ????????????session??????
				// .maximumSessions(1)
				// //???session???????????????????????????????????????????????????
				// //.maxSessionsPreventsLogin(true)
				// // session??????????????????
				// .expiredSessionStrategy(new ImoocExpiredSessionStrategy())
				.and().and().exceptionHandling().authenticationEntryPoint(myLoginUrlAuthenticationEntryPoint()).and()
				.logout().logoutUrl("/logout")
				// .logoutSuccessUrl("/imooc-logout.html")
				.logoutSuccessHandler(logoutSuccessHandler).deleteCookies("JSESSIONID").and().authorizeRequests()
				// .antMatchers("/","/static/**", "/login", "/login/**","/code/**",
				// "/user/checkCode","/user/checkPhone","/user/register","/*.ico").permitAll()
				.antMatchers(securityProperties.getBrowser().getPermitAll().split(",")).permitAll()//
				.and().headers().frameOptions().sameOrigin().and().csrf().disable();

		authorizeConfigManager.config(http.authorizeRequests());

	}

	@Bean
	public MyLoginUrlAuthenticationEntryPoint myLoginUrlAuthenticationEntryPoint() {
		return new MyLoginUrlAuthenticationEntryPoint(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL);
	}

	// @Override
	// public void configure(WebSecurity web) throws Exception {
	// //????????????????????????????????????
	// web.ignoring().antMatchers("**.xlsx");
	// }

}
