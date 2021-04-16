package com.flaminiovilla.geopic.security.helper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flaminiovilla.geopic.security.jwt.JWTFilter;
import com.flaminiovilla.geopic.security.jwt.TokenProvider;
import com.flaminiovilla.geopic.security.model.Authority;
import com.flaminiovilla.geopic.security.model.User;
import com.flaminiovilla.geopic.security.rest.dto.LoginDTO;
import com.flaminiovilla.geopic.security.rest.dto.UserDTO;
import com.flaminiovilla.geopic.model.Region;
import com.flaminiovilla.geopic.repository.RegionRepository;
import com.flaminiovilla.geopic.security.exception.UserException;
import com.flaminiovilla.geopic.security.repository.AuthorityRepository;
import com.flaminiovilla.geopic.security.repository.UserRepository;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.flaminiovilla.geopic.security.exception.UserException.userExceptionCode.*;

@Component
public class UserHelper {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthorityRepository authorityRepository;
    @Autowired
    private PasswordEncoder bcryptEncoder;
    @Autowired
    private RegionRepository regionRepository;

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public UserHelper(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginDTO loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.email, loginDto.password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        //SecurityContextHolder è una classe di supporto, che forniscono l'accesso al contesto di protezione
        SecurityContextHolder.getContext().setAuthentication(authentication);

        boolean rememberMe = (loginDto.rememberMe == null) ? false : loginDto.isRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new JWTToken(jwt,userRepository.findByEmail(loginDto.email)), httpHeaders, HttpStatus.OK);
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    public static class JWTToken {

        private String idToken;
        private final UserDTO user;
        JWTToken(String idToken, User user) {
            this.idToken = idToken;
            this.user = new UserDTO(user);
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }
        @JsonProperty("user")
        UserDTO getUser() {
            return user;
        }
        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }

    public void ceckUser(UserDTO userDTO) {
        Preconditions.checkArgument(Objects.nonNull(userDTO.email));
        Preconditions.checkArgument(Objects.nonNull(userDTO.password));
        Preconditions.checkArgument(Objects.nonNull(userDTO.role));
        Preconditions.checkArgument(Objects.nonNull(userDTO.firstName));
        Preconditions.checkArgument(Objects.nonNull(userDTO.lastName));

    }

    public User registerUser(UserDTO userDTO) {
        userDTO.role = "USER";
        return register(userDTO);

    }

    public User registerSecretary(UserDTO userDTO) {
        userDTO.role = "SECRETARY";
        return register(userDTO);

    }

    public User registerAdminSecretary(UserDTO userDTO) {
        userDTO.role = "ADMIN_SECRETARY";
        return register(userDTO);

    }

    public User registerAdmin(UserDTO userDTO) {
        userDTO.role = "ADMIN";
        return register(userDTO);
    }


    private User register(UserDTO userDTO) {
        ceckUser(userDTO);
        if (!userRepository.existsByEmail(userDTO.email) && !role(userDTO).isEmpty()) {
            return userRepository.save(User.builder()
                    .email(userDTO.email)
                    .password(bcryptEncoder.encode(userDTO.password))
                    .firstName(userDTO.firstName)
                    .lastName(userDTO.lastName)
                    .activated(true)
                    .region(getRegion(userDTO))
                    .authorities(role(userDTO))
                    .build());
        }
        throw new UserException(USER_ALREADY_EXISTS);
    }

    private Set<Authority> role(UserDTO userDTO) {
        Set<Authority> author = new HashSet<>();
        switch (userDTO.role) {
            case "USER":
                author.add(authorityRepository.getByName("ROLE_USER"));

                break;
            case "SECRETARY":
                author.add(authorityRepository.getByName("ROLE_USER"));
                author.add(authorityRepository.getByName("ROLE_SECRETARY"));

                break;
            case "ADMIN_SECRETARY":
                author.add(authorityRepository.getByName("ROLE_USER"));
                author.add(authorityRepository.getByName("ROLE_SECRETARY"));
                author.add(authorityRepository.getByName("ROLE_ADMIN_SECRETARY"));

                break;
            case "ADMIN":
                author.add(authorityRepository.getByName("ROLE_USER"));
                author.add(authorityRepository.getByName("ROLE_SECRETARY"));
                author.add(authorityRepository.getByName("ROLE_ADMIN_SECRETARY"));
                author.add(authorityRepository.getByName("ROLE_ADMIN"));

                break;
            default:
                throw new UserException(AUTHORITY_NOT_EXIST);
        }
        return author;
    }
//TODO l'eliminazione non e' possibile , al massimo si disattiva

    private Region getRegion(UserDTO userDTO) {
            //Admin e User devono avere regione 99 , per indicare ovunque
        switch (userDTO.role) {
            case "ADMIN":
            case "USER":
                return regionRepository.findById((long) 99).get();

            case "ADMIN_SECRETARY":
                Preconditions.checkArgument(Objects.nonNull(userDTO.regionId));
                return regionRepository.findById(userDTO.regionId).get();

            case "SECRETARY":
                Preconditions.checkArgument(Objects.nonNull(userDTO.callUser));

                return userDTO.callUser.getRegion();
        }
        return null;
    }

}
