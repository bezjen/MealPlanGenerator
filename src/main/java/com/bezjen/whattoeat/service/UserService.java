package com.bezjen.whattoeat.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.bezjen.whattoeat.model.UpdateProfileSettingsModel;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bezjen.whattoeat.entity.User;
import com.bezjen.whattoeat.repository.RoleRepository;
import com.bezjen.whattoeat.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameIgnoreCase(username);

        if (user == null) {
        	user = userRepository.findByEmailIgnoreCase(username);
        	if (user == null) {
                throw new UsernameNotFoundException("User not found");
        	}
        }

        if (user.isBlocked()) {
            throw new LockedException("User is blocked");
        }

        return user;
    }

    public List<User> allUsers() {
        return IterableUtils.toList(userRepository.findAll());
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }
    
    public void updateUser(UpdateProfileSettingsModel updateProfileSettingsModel) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (StringUtils.isNotBlank(updateProfileSettingsModel.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(updateProfileSettingsModel.getPassword()));
        }
    	userRepository.save(user);
    }
    
    public boolean saveAdmin(User user) {
    	return saveUser(user, "ROLE_ADMIN");
    }

    public boolean saveModerator(User user) {
        return saveUser(user, "ROLE_MODERATOR");
    }

    public boolean saveUser(User user) {
    	return saveUser(user, "ROLE_USER");
    }

    public void setUserBlock(Long userId, boolean blocked) {
        User user = userRepository.findById(userId).get();
        user.setBlocked(blocked);
        userRepository.save(user);
    }
    
    private boolean saveUser(User user, String role) {
    	if (userRepository.existsByUsernameIgnoreCaseOrEmailIgnoreCase(user.getUsername(), user.getEmail())) {
            return false;
        }

        user.setRoles(Collections.singleton(roleRepository.findByName(role)));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setDate(new Date());
        userRepository.save(user);
        return true;
    }
}