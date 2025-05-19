package org.kunal.spring_sec_demo.service;

import org.kunal.spring_sec_demo.model.User;
import org.kunal.spring_sec_demo.model.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // Extract user info from OAuth2 provider
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        // Get provider details
        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = extractProviderId(attributes, provider);

        try {
            // Check if user exists
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            return new UserPrinciple(((UserPrinciple) userDetails).getUser(), attributes);
        } catch (UsernameNotFoundException e) {
            // Create new user through the service
            User user = userService.findOrCreateOAuthUser(email, name, provider, providerId);
            return new UserPrinciple(user, attributes);
        }
    }

    private String extractProviderId(Map<String, Object> attributes, String provider) {
        // Different providers store ID differently
        if ("google".equals(provider)) {
            return (String) attributes.get("sub");
        } else if ("github".equals(provider)) {
            Integer id = (Integer) attributes.get("id");
            return id != null ? id.toString() : null;
        } else {
            // Default fallback
            Object id = attributes.get("id");
            return id != null ? id.toString() : null;
        }
    }
}