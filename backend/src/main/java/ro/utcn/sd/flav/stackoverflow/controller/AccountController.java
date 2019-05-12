package ro.utcn.sd.flav.stackoverflow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.utcn.sd.flav.stackoverflow.dto.ApplicationUserDTO;
import ro.utcn.sd.flav.stackoverflow.entity.ApplicationUser;
import ro.utcn.sd.flav.stackoverflow.service.AccountManagementService;
import ro.utcn.sd.flav.stackoverflow.service.ApplicationUserDetailsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountManagementService accountService;
    private final ApplicationUserDetailsService userService;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public ApplicationUserDTO loginUser(@RequestBody  ApplicationUserDTO applicationUserDTO) {

        return accountService.login(applicationUserDTO.getUsername(), applicationUserDTO.getPassword());
    }

    @PostMapping("/register")
    public ApplicationUserDTO registerUser(@RequestBody  ApplicationUserDTO applicationUserDTO) {

        return accountService.register(applicationUserDTO.getUsername(), passwordEncoder.encode(applicationUserDTO.getPassword()));
    }

    @GetMapping("/users")
    public List<ApplicationUserDTO> listAll() {

        return accountService.listUsers();
    }

    @PostMapping("/ban-user")
    public void banUser(@RequestBody ApplicationUserDTO applicationUserDTO) {

        accountService.changeUserStatusToBanned(applicationUserDTO.getUserId());
    }

    @PostMapping("/unban-user")
    public void unbanUser(@RequestBody ApplicationUserDTO applicationUserDTO) {

        accountService.changeUserStatusToUnbanned(applicationUserDTO.getUserId());
    }

    @GetMapping("/current-user")
    public ApplicationUser readCurrentUser(){
        return userService.loadCurrentUser();
    }

}
