package banking.repository;

import banking.dto.entity.Account;
import banking.dto.entity.TransferHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferHistoryRepository extends JpaRepository<TransferHistory, Long> {
    TransferHistory findByTransferKey(String transferKey);
}
