package banking.repository;

import banking.dto.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

//    @Query("select COUNT(i.userId) > 0 from AccountUser i where i.id = :user_id")
//    boolean isExistUser(@Param("user_id") String userId);

    boolean existsByUserId(String userId);

    Account findByPublicToken(String publicToken);
    Account findByPrivateToken(String privateToken);

}
