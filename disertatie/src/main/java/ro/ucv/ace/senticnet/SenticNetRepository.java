package ro.ucv.ace.senticnet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SenticNetRepository extends JpaRepository<SenticNet, String> {
}
