package ru.epam.miniparking.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.epam.miniparking.domain.Office;

import java.util.List;

@Repository
public interface OfficeRepo extends JpaRepository<Office, Long> {
    List<Office> findByOfficeTitleContaining(String title);
}
