package com.globallogic.bci.service;

import com.globallogic.bci.dto.LoggedInUserResponse;
import com.globallogic.bci.dto.UserPayload;
import com.globallogic.bci.dto.UserResponse;
import com.globallogic.bci.entity.Phone;
import com.globallogic.bci.entity.User;
import com.globallogic.bci.exceptions.ExistingUserException;
import com.globallogic.bci.exceptions.InvalidTokenException;
import com.globallogic.bci.repo.PhoneRepository;
import com.globallogic.bci.repo.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhoneRepository phoneRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    private final String secretKey = "secretBCI";

    public UserResponse signUpUser(UserPayload user) throws Exception {
        try {
            User newUser = modelMapper.map(user, User.class);
            newUser.setCreated(Timestamp.valueOf(LocalDateTime.now()));
            newUser.setActive(true);
            List<Phone> phones = savePhones(user.getPhones());
            newUser.setPhones(phones);
            User createdUser = userRepository.save(newUser);
            UserResponse userResponse = modelMapper.map(createdUser, UserResponse.class);
            userResponse.setToken(generateToken(newUser));
            return userResponse;
        }catch (Exception ex){
            throw new ExistingUserException();
        }
    }

    private List<Phone> savePhones(List<Phone> phoneList) {
        List<Phone> phones = new ArrayList<>();
        phoneList.forEach(phone -> {
            if(phoneRepository.existsById(phone.getNumber())){
                phones.add(phoneRepository.findById(phone.getNumber()).get());
            } else{
                phones.add(phoneRepository.save(phone));
            }
        });
        return phones;
    }

    public LoggedInUserResponse logInUser(String token){
        validateToken(token);
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(token)
                .getBody();
        Timestamp lastLogin = Timestamp.valueOf(LocalDateTime.now());
        try{
            User user = userRepository.findByEmail(claims.getSubject());
            user.setLastLogin(lastLogin);
            LoggedInUserResponse userResponse = modelMapper.map(user, LoggedInUserResponse.class);
            userResponse.setToken(generateToken(user));
            return userResponse;
        }catch(Exception ex){
            throw new InvalidTokenException();
        }
    }

    private void validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token);
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }

    private String generateToken(User user){
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");
        return Jwts
                .builder()
                .setId("bci")
                .setSubject(user.getEmail())
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(null)
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();
    }
}
