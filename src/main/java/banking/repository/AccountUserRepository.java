package banking.repository;

import banking.dto.AccountUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountUserRepository extends JpaRepository<AccountUser, String> {

//    @Query("select COUNT(i.userId) > 0 from AccountUser i where i.id = :user_id")
//    boolean isExistUser(@Param("user_id") String userId);

    boolean existsByUserId(String userId);

    AccountUser findByPublicToken(String publicToken);

}
