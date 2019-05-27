package ro.utcn.sd.flav.stackoverflow.dto;

import lombok.Data;
import ro.utcn.sd.flav.stackoverflow.entity.ApplicationUser;

@Data
public class ApplicationUserDTO {

    private Integer userId;
    private String username;
    private String password;
    private String permission;
    private String status;
    private int points;

    public static ApplicationUserDTO ofEntity(ApplicationUser applicationUser)
    {
        ApplicationUserDTO applicationUserDTO = new ApplicationUserDTO();
        applicationUserDTO.setUserId(applicationUser.getUserId());
        applicationUserDTO.setUsername(applicationUser.getUsername());
        applicationUserDTO.setPassword(applicationUser.getPassword());
        applicationUserDTO.setPermission(applicationUser.getPermission().name());
        applicationUserDTO.setStatus(applicationUser.getStatus().name());
        applicationUserDTO.setPoints(applicationUser.getPoints());

        return applicationUserDTO;
    }
}
