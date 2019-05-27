package ro.utcn.sd.flav.stackoverflow.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ro.utcn.sd.flav.stackoverflow.dto.ApplicationUserDTO;
import ro.utcn.sd.flav.stackoverflow.entity.ApplicationUser;
import ro.utcn.sd.flav.stackoverflow.entity.UserStatus;
import ro.utcn.sd.flav.stackoverflow.event.BaseEvent;
import ro.utcn.sd.flav.stackoverflow.service.AccountManagementService;
import ro.utcn.sd.flav.stackoverflow.service.ApplicationUserDetailsService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountManagementService accountService;
    private final ApplicationUserDetailsService userService;
    private final SimpMessagingTemplate messagingTemplate;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public ApplicationUserDTO loginUser(@RequestBody  ApplicationUserDTO applicationUserDTO) {

        return accountService.login(applicationUserDTO.getUsername(), applicationUserDTO.getPassword());
    }

    @PostMapping("/users")
    public ApplicationUserDTO registerUser(@RequestBody  ApplicationUserDTO applicationUserDTO) {

        return accountService.register(applicationUserDTO.getUsername(), passwordEncoder.encode(applicationUserDTO.getPassword()));
    }

    @GetMapping("/list-users")
    public List<ApplicationUserDTO> listAll() {

        return accountService.listUsers();
    }

    @PutMapping("/user/{userId}")
    public void banUser(@PathVariable int userId, @RequestBody ApplicationUserDTO applicationUserDTO) {

        if(applicationUserDTO.getStatus().equals(UserStatus.BANNED.name()))
            accountService.changeUserStatusToBanned(userId);
        else
            accountService.changeUserStatusToUnbanned(userId);

    }

    @GetMapping("/current-user")
    public ApplicationUser readCurrentUser(){
        return userService.loadCurrentUser();
    }

    @EventListener(BaseEvent.class)
    public void handleEvent(BaseEvent event) {
        log.info("Got an event: {}.", event);
        messagingTemplate.convertAndSend("/topic/events", event);
    }

}
