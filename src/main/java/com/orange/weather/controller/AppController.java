package com.orange.weather.controller;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.orange.weather.model.User;
import com.orange.weather.model.UserProfile;
import com.orange.weather.service.UserProfileService;
import com.orange.weather.service.UserService;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class AppController {

	@Autowired
	UserService userService;

	@Autowired
	UserProfileService userProfileService;

	@Autowired
	MessageSource messageSource;

	@Autowired
	PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

	@Autowired
	AuthenticationTrustResolver authenticationTrustResolver;

	static final Logger logger = (Logger) LoggerFactory.getLogger(AppController.class);

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String LandingPage(ModelMap model) {
		logger.info("landing page");
		logger.setLevel(Level.DEBUG);
		logger.debug("Parameters => ModelMap : model");

		String url = "";
		if (!isCurrentAuthenticationAnonymous()) {
			if (isAdmin()) {
				url = "redirect:/dashboard";
			} else {
				url = "redirect:/check";
			}
		} else {
			url = "login";
		}
		return url;
	}

	/**
	 * This method will list all existing users.
	 */
	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public String listUsers(ModelMap model) {
		logger.info("user list page");
		logger.debug("Parameters => ModelMap : model");

		List<User> users = userService.findAllUsers();
		model.addAttribute("users", users);
		model.addAttribute("loggedinuser", getPrincipal());
		return "userslist";
	}

	/**
	 * This method will provide the medium to add a new user.
	 */
	@RequestMapping(value = { "/newuser" }, method = RequestMethod.GET)
	public String newUser(ModelMap model) {
		logger.info("New User Page");
		logger.setLevel(Level.DEBUG);
		logger.debug("Parameters => ModelMap : model");
		return "registration";
	}

	/**
	 * This method will be called on form submission, handling POST request for
	 * saving user in database. It also validates the user input
	 */
	@RequestMapping(value = { "/newuser/process" }, method = RequestMethod.POST)
	public String saveUser(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
			@RequestParam("password") String password, @RequestParam("email") String email,
			@RequestParam("phone") String phone, ModelMap model) {
		logger.info("New user Submit Form");
		logger.setLevel(Level.DEBUG);
		logger.debug(
				"Parameters => String : firstName , String : lastName , String : password , String : email , String : phone");
		User user = new User();
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setPassword(password);
		user.setPhone(phone);
		user.setSsoId(user.getFirstName() + Math.random());

		// if (result.hasErrors()) {
		// logger.info("user"+user);
		//
		// return "registration";
		// }

		/*
		 * Preferred way to achieve uniqueness of field [email] should be
		 * implementing custom @Unique annotation and applying it on field
		 * [email] of Model class [User].
		 * 
		 * Below mentioned peace of code [if block] is to demonstrate that you
		 * can fill custom errors outside the validation framework as well while
		 * still using internationalized messages.
		 * 
		 */
		if (!userService.isUserEmailUnique(user.getId(), user.getEmail())) {
			FieldError ssoError = new FieldError("user", "ssoId", messageSource.getMessage("non.unique.ssoId",
					new String[] { user.getSsoId() }, Locale.getDefault()));
			model.addAttribute("ErrorMsg", ssoError);
			return "registration";
		}
		try {
			userService.saveUser(user);

		} catch (Exception e) {
			// TODO: handle exception
			logger.setLevel(Level.ERROR);
			logger.error(e.getMessage());
		}

		model.addAttribute("success",
				"User " + user.getFirstName() + " " + user.getLastName() + " registered successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		// return "success";
		return "redirect:/check";
	}

	/**
	 * This method will delete an user by it's SSOID value.
	 */
	@RequestMapping(value = { "/delete-user-{ssoId}" }, method = RequestMethod.GET)
	public String deleteUser(@PathVariable String ssoId) {
		logger.info("delete user ");
		logger.setLevel(Level.DEBUG);
		logger.debug("Parameters => String : ssoid");
		userService.deleteUserBySSO(ssoId);
		return "redirect:/dashboard";
	}

	/**
	 * This method will provide UserProfile list to views
	 */
	@ModelAttribute("roles")
	public List<UserProfile> initializeProfiles() {
		logger.info("get all roles ");
		return userProfileService.findAll();
	}

	/**
	 * This method handles Access-Denied redirect.
	 */
	@RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
	public String accessDeniedPage(ModelMap model) {
		logger.info("access denied page ");
		model.addAttribute("loggedinuser", getPrincipal());
		return "accessDenied";
	}

	/**
	 * This method handles login GET requests. If users is already logged-in and
	 * tries to goto login page again, will be redirected to dashboard if
	 * current user is admin and check weather page if user.
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() {
		logger.info("login page");
		if (isCurrentAuthenticationAnonymous()) {
			return "login";
		} else {
			if (isAdmin()) {
				return "redirect:/dashboard";
			} else {
				return "redirect:/check";
			}

		}
	}

	/**
	 * This method handles logout requests. Toggle the handlers if you are
	 * RememberMe functionality is useless in your app.
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		logger.info("logout request");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			// new SecurityContextLogoutHandler().logout(request, response,
			// auth);
			persistentTokenBasedRememberMeServices.logout(request, response, auth);
			SecurityContextHolder.getContext().setAuthentication(null);
		}
		return "redirect:/login?logout";
	}

	/**
	 * This method returns the principal[user-name] of logged-in user.
	 */
	private String getPrincipal() {
		logger.info("get principal");

		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}

	/**
	 * This method returns true if users is already authenticated [logged-in],
	 * else false.
	 */
	private boolean isCurrentAuthenticationAnonymous() {
		logger.info("is Current authentication anonymous method");

		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authenticationTrustResolver.isAnonymous(authentication);
	}

	/**
	 * This method returns true if users is Admin [logged-in], else false.
	 */
	private boolean isAdmin() {
		logger.info("is admin method");
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		boolean authorized = authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
		return authorized;
	}

}