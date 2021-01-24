package tr.com.kafein.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.kafein.dashboard.data.Dashboard;

public interface DashboardRepository extends JpaRepository<Dashboard, Long> {
}
