package com.ochwada.authservice.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * *******************************************************
 * Package: com.ochwada.authservice.controller
 * File: AuthController.java
 * Author: Ochwada
 * Date: Tuesday, 05.Aug.2025, 2:03 PM
 * Description:Controller for handling authentication-related routes such as login, dashboard, and home.
 *  Utilizes Spring Security with OIDC (OpenID Connect) for user authentication.
 * *******************************************************
 */

@Controller
public class AuthController {

    /**
     * Handles the login page request.
     *
     * @return the name of the login view template.
     */
    @GetMapping("/my_login")
    public String loginPage(){
        return "login";
    }


    /**
     * Handles the dashboard page request after successful authentication.
     * Extracts user details from the OIDC user and adds them to the model.
     *
     * @param model the model to pass data to the view.
     * @param user the authenticated OIDC user containing user attributes.
     * @return the name of the dashboard view template.
     */
    @GetMapping("/dashboard")
    public  String dashboard(Model model, @AuthenticationPrincipal OidcUser user){

        // Get user's full name from OIDC attributes
        String fullName = user.getAttribute("name");

        // Add user details to the model
        model.addAttribute( "name", fullName);
        model.addAttribute("email", user.getEmail());
        model.addAttribute("picture", user.getPicture());
        model.addAttribute("token", user.getIdToken().getTokenValue());

        return "dashboard";
    }

    /**
     * Handles the home page request.
     * This route is public and does not require authentication.
     *
     * @return the name of the home view template ("home.html").
     */
    @GetMapping("/")
    public String home(){
        return  "home";
    }

    /**
     * Returns the authenticated user's raw ID token as plain text.
     * Useful for debugging, testing, or displaying to the user.
     *
     * @param user the authenticated OpenID Connect user
     * @return the raw ID token string
     */
    @GetMapping("/token")
    @ResponseBody
    public String getToken(@AuthenticationPrincipal OidcUser user){
        return user.getIdToken().getTokenValue();
    }
}
