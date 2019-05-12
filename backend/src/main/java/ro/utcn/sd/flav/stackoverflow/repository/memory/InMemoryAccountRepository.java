package ro.utcn.sd.flav.stackoverflow.repository.memory;

import ro.utcn.sd.flav.stackoverflow.entity.ApplicationUser;
import ro.utcn.sd.flav.stackoverflow.repository.AccountRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class InMemoryAccountRepository implements AccountRepository{

    private final Map<Integer, ApplicationUser> account = new ConcurrentHashMap<>();
    private final AtomicInteger currentId = new AtomicInteger(0);

    @Override
    public List<ApplicationUser> findAll() {
        return new ArrayList<>(account.values());
    }

    @Override
    public ApplicationUser save(ApplicationUser applicationUser) {
        if(applicationUser.getUserId() == null)
        {
            applicationUser.setUserId(currentId.incrementAndGet());
        }

        account.put(applicationUser.getUserId(), applicationUser);

        return applicationUser;
    }

    @Override
    public void remove(ApplicationUser applicationUser) {

        account.remove(applicationUser.getUserId());
    }

    @Override
    public Optional<ApplicationUser> findById(int id) {

        return Optional.ofNullable(account.get(id));
    }

    @Override
    public ApplicationUser findApplicationUserByUserId(int userId) {
        return account.get(userId);
    }

    @Override
    public Optional<Object> findApplicationUserByUsername(String username) {
        for (ApplicationUser user: account.values())
        {
               if(username.equals(user.getUsername()))
                   return Optional.ofNullable(user);
        }
        return Optional.empty();
    }
}
