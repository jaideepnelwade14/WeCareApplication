package com.team3.wecare.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.team3.wecare.entities.Complaint;
import com.team3.wecare.entities.Jurisdiction;
import com.team3.wecare.entities.Officer;
import com.team3.wecare.models.JurisdictionModel;
import com.team3.wecare.repositories.ComplaintRepo;
import com.team3.wecare.repositories.JurisdictionRepo;
import com.team3.wecare.repositories.OfficerRepo;
import com.team3.wecare.service.JurisdictionService;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class JurisdictionServiceImpl implements JurisdictionService {
	private static final Logger logger = LoggerFactory.getLogger(JurisdictionServiceImpl.class);
	private static final String JURISDICTION_ERROR_MESSAGE = "Unable\\u200Bto\\u200Bdelete\\u200Bjurisdiction,\\u200Bthere\\u200Bare\\u200Bassociated\\u200Bgrievances.";
	private static final String JURISDICTION_NOT_FOUND = "Jurisdiction not found.";

	@Autowired
	private JurisdictionRepo juryRepo;
	@Autowired
	private OfficerRepo officerRepo;
	@Autowired
	private ComplaintRepo complaintRepo;

	@Override
	public Jurisdiction updateJurisdiction(JurisdictionModel jurisdiction) {
		logger.debug("Updating Jurisdication details of Id: {}",jurisdiction.getJuryId());
		Jurisdiction jury=juryRepo.getReferenceById(jurisdiction.getJuryId());
		jury.setArea(jurisdiction.getArea());
		jury.setWard(jurisdiction.getWard());
		jury.setLayout(jurisdiction.getLayout());
		jury.setModifiedDate(LocalDateTime.now());
		
		return juryRepo.save(jury);
	}

	@Override
	public Jurisdiction getJurisdiction(int juryId) {
		logger.debug("Retrieving Jurisdiction with Id {}",juryId);
		
		return juryRepo.getReferenceById(juryId);
	}

	@Override
	public List<Jurisdiction> getAllJurisdictions() {
		logger.debug("Retrieving all the Jurisdictions registered !");
		
		return juryRepo.findAll();
	}

	@Override
	public Jurisdiction createJurisdiction(JurisdictionModel juryModel) {
		Jurisdiction jury = new Jurisdiction(juryModel.getArea(), juryModel.getWard(), juryModel.getLayout());
		jury.setRegisteredDate(LocalDateTime.now());
		logger.debug("Create a new Jurisdiction !");
		logger.info("Create a new Jurisdiction !");

		return juryRepo.save(jury);
	}

	@Override
	public String deleteJurisdictionById(int jurisdictionId) {
		Optional<Jurisdiction> optionalJurisdiction = juryRepo.findById(jurisdictionId);
		logger.debug("Deleting Jurisdiction Details");

		if (optionalJurisdiction.isPresent()) {
			Jurisdiction jurisdiction = optionalJurisdiction.get();
			List<Complaint> complaints = complaintRepo.findByJuryComplaint(jurisdiction);
			if (!complaints.isEmpty()) {
				return JURISDICTION_ERROR_MESSAGE;
			}
			for (Officer officer : jurisdiction.getOfficers()) {
				officer.setJuryOfficer(null);
			}
			officerRepo.saveAll(jurisdiction.getOfficers());
			juryRepo.delete(jurisdiction);

			return null;
		}

		return JURISDICTION_NOT_FOUND;
	}

	@Override
	public Jurisdiction getJuryByDetails(String area, String ward, String layout) {
		logger.debug("Retrieving Jurisdiction Details on {}, {}, {}",area, ward, layout);
		
		return juryRepo.getJurisdictionByDetails(area, ward, layout);
	}

	@Override
	public List<String> getWardsByArea(String area) {
		logger.debug("Retrieving Wards in a Jurisdiction by Area: {}",area);

		return juryRepo.findWardsByArea(area);
	}

	@Override
	public List<String> getLayoutByWard(String ward) {
		logger.debug("Retrieving Layouts in a Jurisdiction by Ward: {}",ward);

		return juryRepo.findLayoutsByWard(ward);
	}

	@Override
	public List<String> getDistinctArea() {
		logger.debug("Getting Distinct Areas from Jurisdictions !");
		
		return juryRepo.findDistinctAreas();
	}

	@Override
	public Long countJurisdictions() {
		logger.debug("Counting total number of Jurisdictions Present !");

		return juryRepo.count();
	}
}