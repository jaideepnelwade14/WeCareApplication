package com.team3.wecare.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.team3.wecare.entities.Jurisdiction;

@Repository
public interface JurisdictionRepo extends JpaRepository<Jurisdiction, Integer> {

	@Query("SELECT J FROM Jurisdiction J WHERE area=?1 AND ward=?2 AND layout=?3")
	Jurisdiction getJurisdictionByDetails(String area, String ward, String layout);

	@Query("SELECT DISTINCT j.area FROM Jurisdiction j")
	List<String> findDistinctAreas();

	@Query("SELECT DISTINCT j.ward FROM Jurisdiction j WHERE j.area = :area")
	List<String> findWardsByArea(@Param("area") String area);

	@Query("SELECT DISTINCT j.layout FROM Jurisdiction j WHERE j.ward = :ward")
	List<String> findLayoutsByWard(@Param("ward") String ward);

	@Query("SELECT juryId from Jurisdiction J WHERE area=?1 AND ward=?2 AND layout=?3")
	Integer getJurisdictionIdByDetails(String area, String ward, String layout);
}
