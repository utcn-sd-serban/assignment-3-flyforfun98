package ro.utcn.sd.flav.stackoverflow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.utcn.sd.flav.stackoverflow.dto.ApplicationUserDTO;
import ro.utcn.sd.flav.stackoverflow.entity.ApplicationUser;
import ro.utcn.sd.flav.stackoverflow.entity.UserPermission;
import ro.utcn.sd.flav.stackoverflow.entity.UserStatus;
import ro.utcn.sd.flav.stackoverflow.exception.AccountExistsException;
import ro.utcn.sd.flav.stackoverflow.exception.AccountNotFoundException;
import ro.utcn.sd.flav.stackoverflow.exception.AdminNotFoundException;
import ro.utcn.sd.flav.stackoverflow.exception.BannedUserException;
import ro.utcn.sd.flav.stackoverflow.repository.AccountRepository;
import ro.utcn.sd.flav.stackoverflow.repository.RepositoryFactory;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AccountManagementService {
    private final RepositoryFactory repositoryFactory;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public ApplicationUserDTO login(String username, String password) {

        ApplicationUserDTO user;
        if( (user = isAccountExistent(username, password, false)) != null )
        {
            if(!user.getStatus().equals(UserStatus.BANNED.name())) {
                return user;
            }
            else
                throw new BannedUserException();

        }

        throw new AccountNotFoundException();

    }

    @Transactional
    public ApplicationUserDTO register(String username, String password) {

        ApplicationUserDTO user;
        if( isAccountExistent(username, password, true) == null)
            user = addUser(username, password, UserPermission.USER, UserStatus.ALLOWED,0);
        else {
            throw new AccountExistsException();
        }

        return user;
    }

    @Transactional
    public void changeUserStatusToBanned(int userId)
    {
        updateAccount(userId, UserStatus.BANNED);
    }

    @Transactional
    public void changeUserStatusToUnbanned(int userId)
    {
        updateAccount(userId, UserStatus.ALLOWED);
    }

    @Transactional
    private ApplicationUserDTO addUser(String username, String password, UserPermission permission, UserStatus status, int points)
    {
        ApplicationUser applicationUser = new ApplicationUser(username, password, permission, status, points);
        return ApplicationUserDTO.ofEntity(repositoryFactory.createAccountRepository().save(applicationUser));
    }

    @Transactional
    public void removeUser(Integer id)
    {
        AccountRepository accountRepository = repositoryFactory.createAccountRepository();
        ApplicationUser applicationUser = accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
        accountRepository.remove(applicationUser);

    }

    @Transactional
    public List<ApplicationUserDTO> listUsers()
    {
        return repositoryFactory.createAccountRepository().findAll().stream()
                .map(ApplicationUserDTO::ofEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public ApplicationUser findApplicationUserByUserId(int userId)
    {
        return repositoryFactory.createAccountRepository().findById(userId).orElse(null);
    }

    @Transactional
    private void updateAccount (int id, UserStatus newStatus)
    {
        AccountRepository accountRepository = repositoryFactory.createAccountRepository();

        try {
            ApplicationUser applicationUser = accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
            applicationUser.setStatus(newStatus);
            accountRepository.save(applicationUser);
        }
        catch(AccountNotFoundException e)
        {
            throw new AccountNotFoundException();
        }
    }

    @Transactional
    private ApplicationUserDTO isAccountExistent(String username, String password, boolean register)
    {

        if(register)
        {
            for (ApplicationUserDTO user : listUsers()) {
                if (user.getUsername().equals(username) || user.getPassword().equals(password))
                    return user;
            }
        }

        else {
            for (ApplicationUserDTO user : listUsers()) {
                if (user.getUsername().equals(username) && passwordEncoder.matches(password,user.getPassword()))
                    return user;
            }
        }

        return null;
    }


}
