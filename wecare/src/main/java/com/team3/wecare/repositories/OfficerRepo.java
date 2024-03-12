package com.team3.wecare.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.team3.wecare.entities.Officer;

@Repository
public interface OfficerRepo extends JpaRepository<Officer, Integer> {

	Officer findByEmail(String email);

	Officer findOfficerByPhone(String phone);

	@Modifying
	@Query("DELETE FROM Officer o WHERE o.officerId=:id")
	void deleteOfficerById(@Param("id") int id);

	@Query("SELECT o.officerId FROM Officer o LEFT JOIN Complaint c ON o.officerId = c.officer.officerId WHERE o.juryOfficer.juryId = :juryId OR o.juryOfficer.juryId IS NULL GROUP BY o.officerId ORDER BY COALESCE(COUNT(c.officer.officerId), 0), o.officerId LIMIT 1")
	Integer findOfficerIdWithMinComplaintCount(@Param("juryId") Integer juryId);
	
	@Query("SELECT O FROM Officer O WHERE (email=?1 OR phone=?2) AND id <> ?3")
	Officer getOfficerByDetails(String email, String phone, int officerId);
}
