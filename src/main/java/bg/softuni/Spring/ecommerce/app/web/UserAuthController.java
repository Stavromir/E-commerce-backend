package bg.softuni.Spring.ecommerce.app.web;

import bg.softuni.Spring.ecommerce.app.model.dto.SignupRequest;
import bg.softuni.Spring.ecommerce.app.model.dto.UserAuthenticationRequest;
import bg.softuni.Spring.ecommerce.app.model.dto.UserDto;
import bg.softuni.Spring.ecommerce.app.model.entity.UserEntity;
import bg.softuni.Spring.ecommerce.app.repository.UserRepository;
import bg.softuni.Spring.ecommerce.app.service.AuthService;
import bg.softuni.Spring.ecommerce.app.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
public class UserAuthController {

    private final static String TOKEN_PREFIX = "Bearer ";
    private final static String HEADER_STRING = "Authorization";

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public UserAuthController(AuthenticationManager authenticationManager,
                              UserDetailsService userDetailsService,
                              UserRepository userRepository,
                              AuthService authService,
                              JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/authenticate")
    public void createAuthenticationToken (@RequestBody UserAuthenticationRequest userAuthenticationRequest,
                                           HttpServletResponse response) throws IOException, JSONException {


        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userAuthenticationRequest.getUsername(),
                    userAuthenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password.");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(userAuthenticationRequest.getUsername());
        Optional<UserEntity> optionalUser = userRepository.findByEmail(userAuthenticationRequest.getUsername());
        String jwt = jwtUtil.generateToken(userDetails.getUsername());

        if (optionalUser.isPresent()) {
            response.getWriter().write(new JSONObject()
                    .put("userId", optionalUser.get().getId())
                    .put("role", optionalUser.get().getRole())
                    .toString()
            );
        }

        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("Access-Control-Allow-Headers", "Authorization, X-PINGOTHER, Origin, " +
                "X-Requested-With, Content-Type, Accept, X-Custom-header");

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
    }


    @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser (@RequestBody SignupRequest signupRequest) {

        if (authService.hasUserWithEmail(signupRequest.getEmail())) {
            return new ResponseEntity<>("User already exists", HttpStatus.NOT_ACCEPTABLE);
        }

        UserDto userDTO = authService.createUser(signupRequest);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}



