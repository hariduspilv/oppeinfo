package ee.hitsa.ois.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.hitsa.ois.domain.PracticeJournal;

public interface PracticeJournalRepository extends JpaRepository<PracticeJournal, Long> {

    PracticeJournal findByContractId(Long id);

}
