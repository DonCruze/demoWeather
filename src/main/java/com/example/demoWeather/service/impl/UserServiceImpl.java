package com.example.demoWeather.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demoWeather.configs.jwt.JwtUserDetailsImpl;
import com.example.demoWeather.configs.jwt.JwtUtils;
import com.example.demoWeather.dto.UserDto;
import com.example.demoWeather.dto.UserEditDto;
import com.example.demoWeather.dto.city.CityAddDto;
import com.example.demoWeather.dto.jwt.JwtResponse;
import com.example.demoWeather.dto.jwt.LoginRequest;
import com.example.demoWeather.dto.jwt.MessageResponse;
import com.example.demoWeather.dto.jwt.SignupRequest;
import com.example.demoWeather.enums.ERole;
import com.example.demoWeather.enums.Status;
import com.example.demoWeather.models.City;
import com.example.demoWeather.models.Role;
import com.example.demoWeather.models.User;
import com.example.demoWeather.repository.CityRepository;
import com.example.demoWeather.repository.RoleRepository;
import com.example.demoWeather.repository.UserRepository;
import com.example.demoWeather.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRespository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final CityRepository cityRepository;

    public UserServiceImpl(AuthenticationManager authenticationManager, UserRepository userRespository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils,
                           CityRepository cityRepository) {
        this.authenticationManager = authenticationManager;
        this.userRespository = userRespository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.cityRepository = cityRepository;
    }


    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        JwtUserDetailsImpl userDetails = (JwtUserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @Override
    public ResponseEntity<?> register(SignupRequest signupRequest) {


        if (userRespository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is exist"));
        }

        if (userRespository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is exist"));
        }

        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()));

        Set<Role> roles = new HashSet<>();
        Set<String> reqRoles = new HashSet<>();
        reqRoles.add("user");

        reqRoles.forEach(r -> {

            Role userRole = roleRepository
                    .findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
            roles.add(userRole);
        });

        user.setRoles(roles);
        user.setStatus(Status.ACTIVE);
        userRespository.save(user);
        return ResponseEntity.ok(new MessageResponse("User CREATED"));


    }

    @Override
    public ResponseEntity<?> subscribeToCity(CityAddDto dto, String token) {
        Optional<City> oneCity = cityRepository.findByName(dto.getName());

        if (oneCity.isPresent()) {
            DecodedJWT decodedJWT = JWT.decode(token.substring(7));
            List<City> saveCity = new ArrayList<>();
            saveCity.add(oneCity.get());

            Optional<User> oneUser = userRespository.findByUsername(decodedJWT.getClaim("sub").asString());
            User user = oneUser.get();
            user.setCities(saveCity);
            userRespository.save(user);
        } else {
            throw new RuntimeException("City not found");
        }

        return ResponseEntity.ok(new MessageResponse("City subscribed"));
    }

    @Override
    public ResponseEntity<?> findAllUsers() {
        return ResponseEntity.ok(userRespository.findAllByDeletedFalse().stream().map(user ->
                UserDto.builder()
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .roles(user.getRoles())
                        .build()).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<?> findOneUser(Long id) {
        return ResponseEntity.ok(userRespository.findAllByDeletedFalseAndId(id).stream().map(user ->
                UserDto.builder()
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .roles(user.getRoles())
                        .build()).collect(Collectors.toList()
        ));
    }

    @Override
    public ResponseEntity<?> getSubscriptions(String token) {
        DecodedJWT decodedJWT = JWT.decode(token.substring(7));
        Optional<User> oneUser = userRespository.findByUsername(decodedJWT.getClaim("sub").asString());

        return ResponseEntity.ok(oneUser.get().getCities());
    }

    @Override
    public ResponseEntity<?> editUser(Long id, UserEditDto dto) {
        User user = userRespository.findAllByDeletedFalseAndId(id).orElseThrow(() -> new RuntimeException("USER not found"));
        user.setEmail(dto.getEmail());
        user.setDeleted(dto.getDeleted());
        userRespository.save(user);
        return ResponseEntity.ok(new MessageResponse("User Updated"));
    }

    @PostConstruct
    public void adminAdd(){

        Optional<User> admin = userRespository.findById(1L);

        if (admin.isPresent()){
            User adminOne = admin.get();
            adminOne.setPassword(passwordEncoder.encode(adminOne.getPassword()));
            userRespository.save(adminOne);

        }
    }
}
