package bg.softuni.Spring.ecommerce.app.web;

import bg.softuni.Spring.ecommerce.app.model.dto.SignupRequestDto;
import bg.softuni.Spring.ecommerce.app.model.dto.UserAuthenticationRequestDto;
import bg.softuni.Spring.ecommerce.app.model.entity.UserEntity;
import bg.softuni.Spring.ecommerce.app.service.UserService;
import bg.softuni.Spring.ecommerce.app.utils.impl.JwtUtil;
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

@RestController
public class UserAuthController {

    private final static String TOKEN_PREFIX = "Bearer ";
    private final static String HEADER_STRING = "Authorization";

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserAuthController(AuthenticationManager authenticationManager,
                              UserDetailsService userDetailsService,
                              UserService authService,
                              JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/authenticate")
    public void createAuthenticationToken(@RequestBody UserAuthenticationRequestDto userAuthenticationRequest,
                                          HttpServletResponse response) throws IOException, JSONException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userAuthenticationRequest.getUsername(),
                    userAuthenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password.");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(userAuthenticationRequest.getUsername());
        UserEntity userEntity = userService.findUserByEmail(userAuthenticationRequest.getUsername());
        String jwt = jwtUtil.generateToken(userDetails.getUsername());

        response.getWriter().write(new JSONObject()
                .put("userId", userEntity.getId())
                .put("role", userEntity.getRole())
                .toString()
        );

        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("Access-Control-Allow-Headers", "Authorization, X-PINGOTHER, Origin, " +
                "X-Requested-With, Content-Type, Accept, X-Custom-header");

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
    }


    @PostMapping("/sign-up")
    public ResponseEntity<Long> signupUser(@RequestBody SignupRequestDto signupRequest) {

        Long userId = userService.createUser(signupRequest);
        return ResponseEntity.ok().body(userId);
    }
}



