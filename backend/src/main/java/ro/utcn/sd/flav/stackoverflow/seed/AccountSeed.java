package ro.utcn.sd.flav.stackoverflow.seed;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.utcn.sd.flav.stackoverflow.entity.ApplicationUser;
import ro.utcn.sd.flav.stackoverflow.entity.UserPermission;
import ro.utcn.sd.flav.stackoverflow.entity.UserStatus;
import ro.utcn.sd.flav.stackoverflow.repository.AccountRepository;
import ro.utcn.sd.flav.stackoverflow.repository.RepositoryFactory;

@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AccountSeed{

    private final RepositoryFactory repositoryFactory;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void run(String... args) {

       AccountRepository accountRepository = repositoryFactory.createAccountRepository();
        if(accountRepository.findAll().isEmpty())
        {
            accountRepository.save(new ApplicationUser("flyforfun98", passwordEncoder.encode("flavius1"), UserPermission.ADMIN, UserStatus.ALLOWED, 0));
            accountRepository.save(new ApplicationUser("flavius1", passwordEncoder.encode("parola1"), UserPermission.USER, UserStatus.ALLOWED, 0));
            accountRepository.save(new ApplicationUser("flavius2", passwordEncoder.encode("parola2"), UserPermission.USER, UserStatus.ALLOWED, 0));
            accountRepository.save(new ApplicationUser("flavius3", passwordEncoder.encode("parola3"), UserPermission.USER, UserStatus.ALLOWED, 0));
        }
    }
}
