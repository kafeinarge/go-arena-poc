package tr.com.kafein.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.kafein.dashboard.data.SalesSummary;

public interface SalesSummaryRepository extends JpaRepository<SalesSummary, Long> {
}
