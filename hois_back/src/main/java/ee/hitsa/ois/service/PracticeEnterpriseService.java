package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.enterprise.Enterprise;
import ee.hitsa.ois.domain.enterprise.EnterpriseSchool;
import ee.hitsa.ois.domain.enterprise.EnterpriseSchoolIscedClass;
import ee.hitsa.ois.domain.enterprise.EnterpriseSchoolLocation;
import ee.hitsa.ois.domain.enterprise.EnterpriseSchoolPerson;
import ee.hitsa.ois.domain.enterprise.PracticeAdmission;
import ee.hitsa.ois.domain.enterprise.PracticeAdmissionStudentGroup;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.enums.HigherAssessment;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.OccupationalGrade;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.service.arireg.AriregisterService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.ClassifierUtil.ClassifierCache;
import ee.hitsa.ois.validation.EstonianIdCodeValidator;
import ee.hitsa.ois.web.commandobject.enterprise.ContractStatisticsCommand;
import ee.hitsa.ois.web.commandobject.enterprise.PracticeAdmissionCommand;
import ee.hitsa.ois.web.commandobject.enterprise.PracticeEnterpriseCsvRow;
import ee.hitsa.ois.web.commandobject.enterprise.PracticeEnterpriseForm;
import ee.hitsa.ois.web.commandobject.enterprise.PracticeEnterpriseGradeCommand;
import ee.hitsa.ois.web.commandobject.enterprise.PracticeEnterpriseIscedClassCommand;
import ee.hitsa.ois.web.commandobject.enterprise.PracticeEnterpriseLocationCommand;
import ee.hitsa.ois.web.commandobject.enterprise.PracticeEnterprisePersonCommand;
import ee.hitsa.ois.web.commandobject.enterprise.PracticeEnterpriseSearchCommand;
import ee.hitsa.ois.web.commandobject.enterprise.StudyYearStatisticsCommand;
import ee.hitsa.ois.web.commandobject.student.StudentPracticeStatisticsSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ContactDto;
import ee.hitsa.ois.web.dto.ContractSearchDto;
import ee.hitsa.ois.web.dto.enterprise.ContractStatisticsDto;
import ee.hitsa.ois.web.dto.enterprise.EnterpriseAdmissionDto;
import ee.hitsa.ois.web.dto.enterprise.EnterpriseAdmissionWithStudentGroupsDto;
import ee.hitsa.ois.web.dto.enterprise.EnterpriseGradeDto;
import ee.hitsa.ois.web.dto.enterprise.EnterpriseImportResultDto;
import ee.hitsa.ois.web.dto.enterprise.EnterpriseImportedRowMessageDto;
import ee.hitsa.ois.web.dto.enterprise.EnterpriseRegCodeCheckDto;
import ee.hitsa.ois.web.dto.enterprise.EnterpriseRegCodeResponseDto;
import ee.hitsa.ois.web.dto.enterprise.EnterpriseSchoolIscedClassDto;
import ee.hitsa.ois.web.dto.enterprise.EnterpriseSchoolLocationDto;
import ee.hitsa.ois.web.dto.enterprise.EnterpriseSchoolPersonDto;
import ee.hitsa.ois.web.dto.enterprise.EnterpriseSearchDto;
import ee.hitsa.ois.web.dto.enterprise.StudyYearStatisticsDto;
import ee.hitsa.ois.web.dto.student.StudentGroupResult;
import ee.hitsa.ois.web.dto.student.StudentPracticeStatisticsDto;
import ee.hois.xroad.ariregister.generated.LihtandmedV1Response;
import ee.hois.xroad.ariregister.generated.ParinglihtV5Ettevote;
import ee.hois.xroad.ariregister.service.LihtandmedResponse;

@Transactional
@Service
public class PracticeEnterpriseService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private EntityManager em;
    @Autowired
	private AriregisterService ariregisterService;
    @Autowired
    private XlsService xlsService;
    @Autowired
    private ClassifierService classifierService;
    
    private final CsvMapper csvMapper = new CsvMapper();
    
    private final EstonianIdCodeValidator idCodeValidator = new EstonianIdCodeValidator();

    private static final String SEARCH_SELECT = "en.id, en.name, cl.name_et, es.address"
    		+ ", string_agg(nullif(trim(concat(esp.firstname, ' ', esp.lastname)), ''), ', ')"
    		+ ", es.is_active, en.reg_code, es.rating_code, es.rating_thru";

    public Page<EnterpriseSearchDto> search(HoisUserDetails user, PracticeEnterpriseSearchCommand command,
            Pageable pageable) {
    	String SEARCH_FROM = "from enterprise en "
        		+ "left join enterprise_school es on (es.enterprise_id = en.id and es.school_id = " + user.getSchoolId()
        		+ ") left join enterprise_school_person esp on (esp.enterprise_school_id = es.id and esp.is_contact = true)"
        		+ "left join classifier cl on en.country_code = cl.code ";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM).sort(pageable);
        qb.optionalContains("en.name", "name", command.getEnterpriseName());
        qb.optionalContains("en.reg_code", "reg_code", command.getEnterpriseCode());
        if ("PR_HINNANG_P".equals(command.getRatingCode())) {
            qb.filter("(es.rating_code is null or es.rating_code = 'PR_HINNANG_P')");
        } else if ("PR_HINNANG_X".equals(command.getRatingCode())) {
            qb.filter("(es.rating_thru < '" + JpaQueryUtil.parameterAsTimestamp(LocalDateTime.now()) + "' and es.rating_code = 'PR_HINNANG_A')");
        } else { 
            qb.optionalContains("es.rating_code", "rating_code", command.getRatingCode());
        }
        if (command.getEnterpriseActive() != null && command.getEnterpriseActive().booleanValue()) {
        	qb.optionalCriteria("es.is_active = :isActive", "isActive", command.getEnterpriseActive());
        }
        qb.groupBy("en.id, es.id, cl.name_et, es.address, es.is_active, en.reg_code, en.name, es.rating_code, es.rating_thru");

        return JpaQueryUtil.pagingResult(qb, SEARCH_SELECT, em, pageable).map(r -> {
            EnterpriseSearchDto dto = new EnterpriseSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setEnterpriseName(resultAsString(r, 1));
            dto.setCountryCode(resultAsString(r, 2));
            dto.setAddress(resultAsString(r, 3));
            dto.setContactPersons(resultAsString(r, 4));
            dto.setIsActive(resultAsBoolean(r, 5));
            dto.setRegCode(resultAsString(r, 6));
            dto.setRatingCode(resultAsString(r, 7));
            dto.setRatingThru(JpaQueryUtil.resultAsLocalDate(r, 8));
            return dto;
        });
    }
    
    public EnterpriseRegCodeResponseDto sameCountryAndCode(HoisUserDetails user,
            EnterpriseRegCodeCheckDto enterpriseForm) {
        JpaNativeQueryBuilder enterpriseCountQuery = new JpaNativeQueryBuilder("from enterprise en ");
        enterpriseCountQuery.requiredCriteria("en.reg_code = :regCode", "regCode",  enterpriseForm.getRegCode());
        enterpriseCountQuery.requiredCriteria("en.country_code = :countryCode", "countryCode",  enterpriseForm.getCountry());
        String selectCount = "en.id";
        List<?> enterprises = enterpriseCountQuery.select(selectCount, em).getResultList();
        EnterpriseRegCodeResponseDto enterpriseResponse = new EnterpriseRegCodeResponseDto();
        if(!enterprises.isEmpty()) {
            enterpriseResponse.setStatus("Sama registrikoodi ja riigiga ettevõte on juba olemas.");
            return enterpriseResponse;
        }
        return enterpriseResponse;
    }

	public EnterpriseRegCodeResponseDto checkForCode(HoisUserDetails user, EnterpriseRegCodeCheckDto enterpriseForm) {
		JpaNativeQueryBuilder enterpriseCountQuery = new JpaNativeQueryBuilder("from enterprise en ");
		enterpriseCountQuery.requiredCriteria("en.reg_code = :regCode", "regCode",  enterpriseForm.getRegCode());
		enterpriseCountQuery.requiredCriteria("en.country_code = :countryCode", "countryCode",  enterpriseForm.getCountry());
        String selectCount = "en.id";
        List<?> enterprises = enterpriseCountQuery.select(selectCount, em).getResultList();
        List<Long> resultEnterprises = enterprises.stream().map(r -> resultAsLong(r, 0)).collect(Collectors.toList());
        if(!enterprises.isEmpty()) {
        	EnterpriseRegCodeResponseDto enterpriseResponse = new EnterpriseRegCodeResponseDto();
        	enterpriseResponse.setStatus("Sama registrikoodi ja riigiga ettevõte on juba olemas.");
        	return enterpriseResponse;
        }
        try {
    		LihtandmedResponse simpleData = ariregisterService.getSimpleData(BigInteger.valueOf(Long.valueOf(enterpriseForm.getRegCode())), user);
    		EnterpriseRegCodeResponseDto enterpriseResponse = new EnterpriseRegCodeResponseDto();
    		LihtandmedV1Response response = simpleData.getResult();
    		if (response == null || response.getKeha() == null || response.getKeha().getEttevotjad() == null) {
    			enterpriseResponse.setStatus("Äriregistri päring ei tagastanud andmeid.");
    			return enterpriseResponse;
    		}
    		List<ParinglihtV5Ettevote> aadressidAds = response.getKeha().getEttevotjad().getItem();
    		if (aadressidAds.isEmpty()) {
    			enterpriseResponse.setStatus("Äriregistri päring ei tagastanud andmeid.");
    			return enterpriseResponse;
    		}
    		ParinglihtV5Ettevote aadress = aadressidAds.get(0);
    		String normalizedAddress = aadress.getEvaadressid().getValue().getAadressAdsAdsNormaliseeritudTaisaadress();
    		if (normalizedAddress == null) {
    		    normalizedAddress = aadress.getEvaadressid().getValue().getAsukohtEttevotjaAadressis();
    		    if (normalizedAddress != null) {
    		        normalizedAddress += ", " + aadress.getEvaadressid().getValue().getAsukohaEhakTekstina();
    		    } else {
    		        normalizedAddress = aadress.getEvaadressid().getValue().getAsukohaEhakTekstina();
    		    }
    		}
    		if (normalizedAddress == null) {
    		    normalizedAddress = aadress.getEvaadressid().getValue().getAsukohaEhakTekstina();
    		}
    		if (normalizedAddress == null) {
                normalizedAddress = aadress.getPiirkondTekstina();
            }
    		enterpriseResponse.setAddress(normalizedAddress);
    		enterpriseResponse.setAddressOid(aadress.getEvaadressid().getValue().getAadressAdsAdsOid());
            enterpriseResponse.setAddressAds(aadress.getEvaadressid().getValue().getAadressAdsKoodaadress());
            
            enterpriseResponse.setRegisterAddress(normalizedAddress);
            enterpriseResponse.setRegisterAddressAds(aadress.getEvaadressid().getValue().getAadressAdsAdsOid());
            enterpriseResponse.setRegisterAddressOid(aadress.getEvaadressid().getValue().getAadressAdsKoodaadress());
            
        	enterpriseResponse.setName(aadress.getEvnimi());
        	return enterpriseResponse;
    	} catch (NumberFormatException n) {
    		EnterpriseRegCodeResponseDto enterpriseResponse = new EnterpriseRegCodeResponseDto();
    		enterpriseResponse.setStatus("Registri number pole õiges formaadis.");
        	return enterpriseResponse;
    	}
	}

	public PracticeEnterpriseForm create(HoisUserDetails user, PracticeEnterpriseForm practiceEnterpriseForm) {
		
		Enterprise enterprise = null;
		if (practiceEnterpriseForm.getId() != null) {
			try {
				enterprise = em.getReference(Enterprise.class, practiceEnterpriseForm.getId());
			} catch (EntityNotFoundException e) {
				enterprise = new Enterprise();
			}
		} else {
			enterprise = new Enterprise();
		}
		EnterpriseSchool enterpriseSchool = null;
		if (practiceEnterpriseForm.getEnterpriseSchoolId() != null) {
			try {
				enterpriseSchool = em.getReference(EnterpriseSchool.class, practiceEnterpriseForm.getEnterpriseSchoolId());
			} catch (EntityNotFoundException e) {
				enterpriseSchool = new EnterpriseSchool();
			}
		} else {
			enterpriseSchool = new EnterpriseSchool();
		}
		
		//Required fields
		enterprise.setCountry(em.getReference(Classifier.class, practiceEnterpriseForm.getCountry()));
		enterprise.setName(practiceEnterpriseForm.getName());
		enterpriseSchool.setSchool(em.getReference(School.class, user.getSchoolId()));
		//Non required fields
		
		enterprise.setRegCode(practiceEnterpriseForm.getRegCode());
		if ( practiceEnterpriseForm.getLanguage() != null) {
			enterpriseSchool.setLanguage(em.getReference(Classifier.class, practiceEnterpriseForm.getLanguage()));
		} else {
		    enterpriseSchool.setLanguage(null);
		}
		enterprise.setAddress(practiceEnterpriseForm.getRegisterAddress());
		enterprise.setAddressAds(practiceEnterpriseForm.getRegisterAddressAds());
		enterprise.setAddressOid(practiceEnterpriseForm.getRegisterAddressOid());
		enterpriseSchool.setAddress(practiceEnterpriseForm.getAddress());
		enterpriseSchool.setAddressAds(practiceEnterpriseForm.getAddressAds());
		enterpriseSchool.setAddressOid(practiceEnterpriseForm.getAddressOid());
		enterprise.setPerson(practiceEnterpriseForm.getPerson());
		enterpriseSchool.setEmail(practiceEnterpriseForm.getEmail());
		enterpriseSchool.setPlacesDescr(practiceEnterpriseForm.getPlacesDescr());
		if (practiceEnterpriseForm.getPlaces() != null) {
			enterpriseSchool.setPlaces(Integer.valueOf(practiceEnterpriseForm.getPlaces()));
		} else {
		    enterpriseSchool.setPlaces(null);
		}
		if (practiceEnterpriseForm.getActive() != null) {
			enterpriseSchool.setActive(practiceEnterpriseForm.getActive());
		} else {
			enterpriseSchool.setActive(Boolean.FALSE);
		}
		enterpriseSchool.setApplication(practiceEnterpriseForm.getApplication());
		enterpriseSchool.setAddInfo(practiceEnterpriseForm.getAddInfo());
		enterpriseSchool.setPhone(practiceEnterpriseForm.getPhone());
		enterpriseSchool.setPostcode(practiceEnterpriseForm.getPostcode());
		EntityUtil.setUsername(user.getUsername(), em);
		Enterprise newEnterprise = EntityUtil.save(enterprise, em);
		enterpriseSchool.setEnterprise(newEnterprise);
        EnterpriseSchool enterpriseSch = EntityUtil.save(enterpriseSchool, em);
        return get(enterpriseSch.getEnterprise(), user);
	}
    
    public PracticeEnterpriseForm get(Enterprise enterprise, HoisUserDetails user) {
        EnterpriseRegCodeCheckDto dto = new EnterpriseRegCodeCheckDto();
        return PracticeEnterpriseForm.of(enterprise, user);
    }

	public EnterpriseRegCodeResponseDto regCodeWithoutCheck(HoisUserDetails user, EnterpriseRegCodeCheckDto enterpriseForm) {
		try {
    		LihtandmedResponse simpleData = ariregisterService.getSimpleData(BigInteger.valueOf(Long.valueOf(enterpriseForm.getRegCode())), user);
    		EnterpriseRegCodeResponseDto enterpriseResponse = new EnterpriseRegCodeResponseDto();
    		LihtandmedV1Response response = simpleData.getResult();
    		if (response == null) {
    			enterpriseResponse.setStatus("Äriregistri päring ei tagastanud andmeid.");
    			return enterpriseResponse;
    		}
    		List<ParinglihtV5Ettevote> aadressidAds = response.getKeha().getEttevotjad().getItem();
    		if (aadressidAds.isEmpty()) {
    			enterpriseResponse.setStatus("Äriregistri päring ei tagastanud andmeid.");
    			return enterpriseResponse;
    		}
    		ParinglihtV5Ettevote aadress = aadressidAds.get(0);
            String normalizedAddress = aadress.getEvaadressid().getValue().getAadressAdsAdsNormaliseeritudTaisaadress();
            if (normalizedAddress == null) {
                normalizedAddress = aadress.getEvaadressid().getValue().getAsukohtEttevotjaAadressis();
                if (normalizedAddress != null) {
                    normalizedAddress += ", " + aadress.getEvaadressid().getValue().getAsukohaEhakTekstina();
                } else {
                    normalizedAddress = aadress.getEvaadressid().getValue().getAsukohaEhakTekstina();
                }
            }
            if (normalizedAddress == null) {
                normalizedAddress = aadress.getEvaadressid().getValue().getAsukohaEhakTekstina();
            }
            if (normalizedAddress == null) {
                normalizedAddress = aadress.getPiirkondTekstina();
            }
            enterpriseResponse.setAddress(normalizedAddress);
            enterpriseResponse.setAddressOid(aadress.getEvaadressid().getValue().getAadressAdsAdsOid());
            enterpriseResponse.setAddressAds(aadress.getEvaadressid().getValue().getAadressAdsKoodaadress());
            
            enterpriseResponse.setRegisterAddress(normalizedAddress);
            enterpriseResponse.setRegisterAddressOid(aadress.getEvaadressid().getValue().getAadressAdsAdsOid());
            enterpriseResponse.setRegisterAddressAds(aadress.getEvaadressid().getValue().getAadressAdsKoodaadress());
            
        	enterpriseResponse.setName(aadress.getEvnimi());
        	return enterpriseResponse;
    	} catch (NumberFormatException n) {
    		EnterpriseRegCodeResponseDto enterpriseResponse = new EnterpriseRegCodeResponseDto();
    		enterpriseResponse.setStatus("Registri number pole õiges formaadis.");
        	return enterpriseResponse;
    	}
	}
	
	public PracticeEnterpriseForm updateRegCode(HoisUserDetails user, EnterpriseRegCodeCheckDto enterpriseForm, EnterpriseSchool enterpriseSchool) {
		EnterpriseRegCodeResponseDto regCodeResponse = regCodeWithoutCheck(user, enterpriseForm);
		regCodeResponse.setRegisterAddress(regCodeResponse.getRegisterAddress());
		regCodeResponse.setRegisterAddressOid(regCodeResponse.getRegisterAddressAds());
		regCodeResponse.setRegisterAddressAds(regCodeResponse.getRegisterAddressOid());
		if (StringUtils.isEmpty(enterpriseSchool.getAddress())) {
		    enterpriseSchool.setAddress(regCodeResponse.getAddress());
	        enterpriseSchool.setAddressAds(regCodeResponse.getAddressAds());
	        enterpriseSchool.setAddressOid(regCodeResponse.getAddressOid());
		}
		enterpriseSchool.getEnterprise().setEbusinessUpdated(LocalDateTime.now());
		EntityUtil.setUsername(user.getUsername(), em);
		EntityUtil.save(enterpriseSchool, em);
		PracticeEnterpriseForm practiceEnterpriseForm = PracticeEnterpriseForm.of(enterpriseSchool, user);
		practiceEnterpriseForm.setAddressRegister(regCodeResponse.getAddress());
		return practiceEnterpriseForm;
	}

	public void createPerson(HoisUserDetails user, EnterpriseSchool enterpriseSchool,
			PracticeEnterprisePersonCommand practiceEnterprisePersonCommand) {
		EnterpriseSchoolPerson person = new EnterpriseSchoolPerson();
		person.setFirstname(practiceEnterprisePersonCommand.getFirstname());
		person.setLastname(practiceEnterprisePersonCommand.getLastname());
		person.setPhone(practiceEnterprisePersonCommand.getPhone());
		person.setEmail(practiceEnterprisePersonCommand.getEmail());
		List<String> existingIdCodes = enterpriseSchool.getEnterpriseSchoolPersons().stream().map(p->p.getIdcode()).collect(Collectors.toList());
		if (StringUtils.isNotEmpty(practiceEnterprisePersonCommand.getCountry())) {
			person.setIdcodeCountry(em.getReference(Classifier.class, practiceEnterprisePersonCommand.getCountry()));
		}
		if(practiceEnterprisePersonCommand.getContact() != null) {
			person.setContact(practiceEnterprisePersonCommand.getContact());
		} else {
			person.setContact(Boolean.FALSE);
		}
		if(practiceEnterprisePersonCommand.getSupervisor() != null) {
			person.setSupervisor(practiceEnterprisePersonCommand.getSupervisor());
		} else {
			person.setSupervisor(Boolean.FALSE);
		}
		if(StringUtils.isNotEmpty(practiceEnterprisePersonCommand.getPosition())) {
			person.setPosition(practiceEnterprisePersonCommand.getPosition());
		}
		if(StringUtils.isNotEmpty(practiceEnterprisePersonCommand.getIdcode())) {
			if (existingIdCodes.contains(practiceEnterprisePersonCommand.getIdcode())) {
				throw new HoisException("Isikukoodiga " + practiceEnterprisePersonCommand.getIdcode() + " isik juba eksisteerib.");
			}
			person.setIdcode(practiceEnterprisePersonCommand.getIdcode());
		}
		person.setEnterpriseSchool(enterpriseSchool);
		EntityUtil.setUsername(user.getUsername(), em);
		EntityUtil.save(person, em);
	}

	public void updatePerson(HoisUserDetails user, EnterpriseSchoolPerson person,
			PracticeEnterprisePersonCommand practiceEnterprisePersonCommand) {
		EntityUtil.bindToEntity(practiceEnterprisePersonCommand, person, "regCode", "name");
		List<String> existingIdCodes = person.getEnterpriseSchool().getEnterpriseSchoolPersons().stream().filter(p->p.getId() != person.getId()).map(p->p.getIdcode()).collect(Collectors.toList());
		if(StringUtils.isNotEmpty(practiceEnterprisePersonCommand.getIdcode())) {
			if (existingIdCodes.contains(practiceEnterprisePersonCommand.getIdcode())) {
				throw new HoisException("Isikukoodiga " + practiceEnterprisePersonCommand.getIdcode() + " isik juba eksisteerib.");
			}
		}
		if (practiceEnterprisePersonCommand.getCountry() != null) {
			person.setIdcodeCountry(em.getReference(Classifier.class, practiceEnterprisePersonCommand.getCountry()));
		} else {
			person.setIdcodeCountry(null);
		}
		EntityUtil.setUsername(user.getUsername(), em);
		EntityUtil.save(person, em);
	}

	public void createLocation(HoisUserDetails user, EnterpriseSchool enterpriseSchool,
			PracticeEnterpriseLocationCommand locationCommand) {
		EnterpriseSchoolLocation location = new EnterpriseSchoolLocation();
		EntityUtil.bindToEntity(locationCommand, location, "country", "language", "enterpriseSchool");
		if (locationCommand.getCountry() != null) {
			location.setCountry(em.getReference(Classifier.class, locationCommand.getCountry()));
		}
		if (locationCommand.getLanguage() != null) {
			location.setLanguage(em.getReference(Classifier.class, locationCommand.getLanguage()));
		}
		location.setEnterpriseSchool(enterpriseSchool);
		EntityUtil.setUsername(user.getUsername(), em);
		EntityUtil.save(location, em);
	}

	public void updateLocation(HoisUserDetails user, EnterpriseSchoolLocation location,
			PracticeEnterpriseLocationCommand practiceEnterpriseLocationCommand) {
		EntityUtil.bindToEntity(practiceEnterpriseLocationCommand, location, "regCode", "name");
		if (practiceEnterpriseLocationCommand.getCountry() != null) {
			location.setCountry(em.getReference(Classifier.class, practiceEnterpriseLocationCommand.getCountry()));
		} else {
			location.setCountry(null);
		}
		if (practiceEnterpriseLocationCommand.getLanguage() != null) {
			location.setLanguage(em.getReference(Classifier.class, practiceEnterpriseLocationCommand.getLanguage()));
		} else {
			location.setLanguage(null);
		}
		EntityUtil.setUsername(user.getUsername(), em);
		EntityUtil.save(location, em);
	}

	public void deleteLocation(HoisUserDetails user, EnterpriseSchoolLocation location) {
		EntityUtil.setUsername(user.getUsername(), em);
		EntityUtil.deleteEntity(location, em);
	}

	public void deletePerson(HoisUserDetails user, EnterpriseSchoolPerson person) {
		EntityUtil.setUsername(user.getUsername(), em);
		EntityUtil.deleteEntity(person, em);
	}

	public void createStudentGroup(HoisUserDetails user, EnterpriseSchool enterpriseSchool,
			PracticeEnterpriseIscedClassCommand studentGroupCommand) {
		EnterpriseSchoolIscedClass iscedClass = new EnterpriseSchoolIscedClass();
		EntityUtil.bindToEntity(studentGroupCommand, iscedClass, "iscedClass");
		iscedClass.setIscedClass(em.getReference(Classifier.class, studentGroupCommand.getIscedClass()));
		iscedClass.setEnterpriseSchool(enterpriseSchool);
		EntityUtil.setUsername(user.getUsername(), em);
		EntityUtil.save(iscedClass, em);
	}

	public void updateStudentGroup(HoisUserDetails user, EnterpriseSchoolIscedClass iscedClass,
			PracticeEnterpriseIscedClassCommand studentGroupCommand) {
		EntityUtil.bindToEntity(studentGroupCommand, iscedClass, "iscedClass");
		iscedClass.setIscedClass(em.getReference(Classifier.class, studentGroupCommand.getIscedClass()));
		EntityUtil.setUsername(user.getUsername(), em);
		EntityUtil.save(iscedClass, em);
	}

	public void deleteStudentGroup(HoisUserDetails user, EnterpriseSchoolIscedClass iscedClass) {
		EntityUtil.setUsername(user.getUsername(), em);
		EntityUtil.deleteEntity(iscedClass, em);
	}
	
	public Page<ContractSearchDto> getContracts(EnterpriseSchool enterpriseSchool, Pageable pageable) {
		String searchString = "from enterprise_school es join enterprise e on e.id = es.enterprise_id "
				+ "join contract c on c.enterprise_id = e.id "
				+ "join student s on s.id = c.student_id "
				+ "left join enterprise_school_person esp on esp.enterprise_school_id = es.id ";
		String selectString = "c.id, c.student_id, c.contract_nr, c.start_date, c.end_date, c.confirm_date, "
				+ "c.status_code, c.teacher_id, string_agg(concat(esp.firstname, esp.lastname), ', '), c.contact_person_name";
		JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(searchString).sort(pageable).groupBy("c.id, c.student_id, c.contract_nr, "
				+ "c.start_date, c.end_date, c.confirm_date, "
				+ "c.status_code, c.teacher_id, c.contact_person_name");
		qb.requiredCriteria("es.id = :enterpriseSchoolId" , "enterpriseSchoolId", EntityUtil.getId(enterpriseSchool));
		qb.requiredCriteria("s.school_id = :schoolId" , "schoolId", enterpriseSchool.getSchool().getId());
        return JpaQueryUtil.pagingResult(qb, selectString, em, pageable).map(r -> {
        	ContractSearchDto dto = new ContractSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setStudent(AutocompleteResult.of(em.getReference(Student.class, resultAsLong(r, 1))));
            dto.setContractNr(resultAsString(r, 2));
            dto.setStartDate(JpaQueryUtil.resultAsLocalDate(r, 3));
            dto.setEndDate(JpaQueryUtil.resultAsLocalDate(r, 4));
            dto.setConfirmDate(JpaQueryUtil.resultAsLocalDate(r, 5));
            dto.setStatus(resultAsString(r, 6));
            dto.setTeacher(AutocompleteResult.of(em.getReference(Teacher.class, resultAsLong(r, 7))));
            if (StringUtils.isNotEmpty(resultAsString(r, 8).trim())) {
            	dto.setEnterpriseContactPersonName(resultAsString(r, 8).trim());
            }
            if (dto.getEnterpriseContactPersonName() == null) {
            	dto.setEnterpriseContactPersonName(resultAsString(r, 9));
            }
            return dto;
        });
	}

	public Page<EnterpriseGradeDto> getGrades(Enterprise enterprise, Pageable pageable) {
		String searchString = "from enterprise e join enterprise_school es on e.id = es.enterprise_id join school s on es.school_id = s.id ";
		JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(searchString).sort(pageable);
		qb.requiredCriteria("e.id = :enterpriseId", "enterpriseId", EntityUtil.getId(enterprise));
		qb.filter("es.rating_code is not null");
		String selectString = "es.rating_code, es.rating_info, es.rating_thru, s.id, s.name_et, s.name_en";
        return JpaQueryUtil.pagingResult(qb, selectString, em, pageable).map(r -> {
        	EnterpriseGradeDto dto = new EnterpriseGradeDto();
            dto.setRatingCode(resultAsString(r, 0));
            dto.setRatingInfo(resultAsString(r, 1));
            dto.setRatingThru(JpaQueryUtil.resultAsLocalDate(r, 2));
            dto.setSchoolName(new AutocompleteResult(resultAsLong(r, 3), resultAsString(r, 4), resultAsString(r, 5)));
            return dto;
        });
	}

	public void setGrades(HoisUserDetails user, EnterpriseSchool enterpriseSchool, PracticeEnterpriseGradeCommand grades) {
		enterpriseSchool.setRating(em.getReference(Classifier.class, grades.getRatingCode()));
		enterpriseSchool.setRatingThru(grades.getRatingThru());
		enterpriseSchool.setRatingInfo(grades.getRatingInfo());
		EntityUtil.setUsername(user.getUsername(), em);
		EntityUtil.save(enterpriseSchool, em);
	}

	public void deleteGrade(HoisUserDetails user, EnterpriseSchool enterpriseSchool) {
		enterpriseSchool.setRating(null);
		enterpriseSchool.setRatingThru(null);
		enterpriseSchool.setRatingInfo(null);
		EntityUtil.setUsername(user.getUsername(), em);
		enterpriseSchool = EntityUtil.save(enterpriseSchool, em);
	}

	public Page<EnterpriseSchoolPersonDto> getPersons(EnterpriseSchool enterpriseSchool, Pageable pageable) {
		String searchString = "from enterprise_school es join enterprise_school_person esp on esp.enterprise_school_id = es.id ";
		JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(searchString).sort(pageable);
		qb.requiredCriteria("es.id = :enterpriseSchoolId" , "enterpriseSchoolId", EntityUtil.getId(enterpriseSchool));
		String selectString = "esp.id, esp.firstname, esp.lastname, esp.phone, esp.email, "
				+ "esp.idcode, esp.idcode_country_code, esp.position, esp.is_supervisor, esp.is_contact";
        return JpaQueryUtil.pagingResult(qb, selectString, em, pageable).map(r -> {
        	EnterpriseSchoolPersonDto dto = new EnterpriseSchoolPersonDto();
            dto.setId(resultAsLong(r, 0));
            dto.setFirstname(resultAsString(r, 1));
            dto.setLastname(resultAsString(r, 2));
            dto.setPhone(resultAsString(r, 3));
            dto.setEmail(resultAsString(r, 4));
            dto.setIdcode(resultAsString(r, 5));
            dto.setCountry(resultAsString(r, 6));
            dto.setPosition(resultAsString(r, 7));
            dto.setSupervisor(resultAsBoolean(r, 8));
            dto.setContact(resultAsBoolean(r, 9));
            return dto;
        });
	}

	public Page<EnterpriseSchoolLocationDto> getLocations(EnterpriseSchool enterpriseSchool,
			Pageable pageable) {
		String searchString = "from enterprise_school es join enterprise_school_location esl on esl.enterprise_school_id = es.id ";
		JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(searchString).sort(pageable);
		qb.requiredCriteria("es.id = :enterpriseSchoolId" , "enterpriseSchoolId", EntityUtil.getId(enterpriseSchool));
		String selectString = "esl.id, esl.language_code, esl.country_code, esl.address, esl.address_ads, esl.address_oid, esl.name_et";
        return JpaQueryUtil.pagingResult(qb, selectString, em, pageable).map(r -> {
        	EnterpriseSchoolLocationDto dto = new EnterpriseSchoolLocationDto();
            dto.setId(resultAsLong(r, 0));
            dto.setLanguage(resultAsString(r, 1));
            dto.setCountry(resultAsString(r, 2));
            dto.setAddress(resultAsString(r, 3));
            dto.setAddressAds(resultAsString(r, 4));
            dto.setAddressOid(resultAsString(r, 5));
            dto.setNameEt(resultAsString(r, 6));
            return dto;
        });
	}

	public Page<EnterpriseSchoolIscedClassDto> getStudentGroups(HoisUserDetails user, EnterpriseSchool enterpriseSchool,
			Pageable pageable) {
		String searchString = "from enterprise_school es "
				+ "join enterprise_school_isced_class esic on esic.enterprise_school_id = es.id "
				+ "join classifier c on c.code = esic.isced_class_code ";
		JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(searchString).sort(pageable);
		qb.requiredCriteria("es.id = :enterpriseSchoolId" , "enterpriseSchoolId", EntityUtil.getId(enterpriseSchool));
		String selectString = "esic.id, esic.isced_class_code, esic.places, esic.add_info, c.value, "
				+ "exists(select null from curriculum cu "
				+ "where cu.school_id = " + user.getSchoolId()
				+ " and cu.isced_class_code = esic.isced_class_code "
				+ "and cu.status_code != 'OPPEKAVA_STAATUS_C'), c.valid_from, c.valid_thru";
        return JpaQueryUtil.pagingResult(qb, selectString, em, pageable).map(r -> {
        	EnterpriseSchoolIscedClassDto dto = new EnterpriseSchoolIscedClassDto();
            dto.setId(resultAsLong(r, 0));
            dto.setIscedClass(resultAsString(r, 1));
            dto.setPlaces(resultAsLong(r, 2));
            dto.setAddInfo(resultAsString(r, 3));
            dto.setIscedValue(resultAsString(r, 4));
            dto.setExists(resultAsBoolean(r, 5));
            dto.setValidFrom(JpaQueryUtil.resultAsLocalDate(r, 6));
            dto.setValidThru(JpaQueryUtil.resultAsLocalDate(r, 7));
            return dto;
        });
	}

	public void createAdmission(HoisUserDetails user, EnterpriseSchool enterpriseSchool,
			PracticeAdmissionCommand admissionCommand) {
		if (enterpriseSchool.getApplication() == null || !enterpriseSchool.getApplication().booleanValue()) {
			return;
		}
		PracticeAdmission admission = new PracticeAdmission();
		EntityUtil.bindToEntity(admissionCommand, admission);
		admission.setEnterpriseSchool(enterpriseSchool);
		if (admissionCommand.getStudentGroups() != null) {
			List<PracticeAdmissionStudentGroup> practiceAdmissionStudentGroups = new ArrayList<>();
			for (StudentGroupResult studentGroupResult : admissionCommand.getStudentGroups()) {
				PracticeAdmissionStudentGroup sg = new PracticeAdmissionStudentGroup();
				sg.setStudentGroup(em.getReference(StudentGroup.class, studentGroupResult.getId()));
				sg.setPracticeAdmission(admission);
				practiceAdmissionStudentGroups.add(sg);
			}
			admission.setPracticeAdmissionStudentGroups(practiceAdmissionStudentGroups);
		}
		EntityUtil.setUsername(user.getUsername(), em);
		EntityUtil.save(admission, em);
	}

	public Page<EnterpriseAdmissionDto> getAdmissions(EnterpriseSchool enterpriseSchool,
			Pageable pageable) {
		String searchString = "from enterprise_school es "
				+ "join practice_admission pa on pa.enterprise_school_id = es.id "
				+ "left join practice_admission_student_group pasg on pasg.practice_admission_id = pa.id "
				+ "left join student_group sg on sg.id = pasg.student_group_id ";
		JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(searchString).sort(pageable).groupBy("pa.id, pa.valid_from, pa.valid_thru, pa.places, pa.add_info");
		qb.requiredCriteria("es.id = :enterpriseSchoolId" , "enterpriseSchoolId", EntityUtil.getId(enterpriseSchool));
		String selectString = "pa.id, pa.valid_from, pa.valid_thru, pa.places, pa.add_info, string_agg(sg.code, ', ')";
        return JpaQueryUtil.pagingResult(qb, selectString, em, pageable).map(r -> {
        	EnterpriseAdmissionDto dto = new EnterpriseAdmissionDto();
            dto.setId(resultAsLong(r, 0));
            dto.setValidFrom(JpaQueryUtil.resultAsLocalDate(r, 1));
            dto.setValidThru(JpaQueryUtil.resultAsLocalDate(r, 2));
            dto.setPlaces(resultAsLong(r, 3));
            dto.setAddInfo(resultAsString(r, 4));
            dto.setStudentGroups(resultAsString(r, 5));
            return dto;
        });
	}

	public void updateAdmission(HoisUserDetails user, PracticeAdmission admission,
			PracticeAdmissionCommand admissionCommand) {
		EntityUtil.bindToEntity(admissionCommand, admission);
		if (admissionCommand.getStudentGroups() != null) {
			List<PracticeAdmissionStudentGroup> practiceAdmissionStudentGroups = new ArrayList<>();
			for (StudentGroupResult studentGroupResult : admissionCommand.getStudentGroups()) {
				PracticeAdmissionStudentGroup sg = new PracticeAdmissionStudentGroup();
				sg.setStudentGroup(em.getReference(StudentGroup.class, studentGroupResult.getId()));
				sg.setPracticeAdmission(admission);
				practiceAdmissionStudentGroups.add(sg);
			}
			admission.setPracticeAdmissionStudentGroups(practiceAdmissionStudentGroups);
		}
		EntityUtil.setUsername(user.getUsername(), em);
		EntityUtil.save(admission, em);
	}

	public AutocompleteResult deleteAdmission(HoisUserDetails user, PracticeAdmission admission) {
		if (!admission.getPracticeApplications().isEmpty()) {
			return new AutocompleteResult(null, "Taotlusele on esitatud avaldusi", "Admission has applications");
		}
		EntityUtil.setUsername(user.getUsername(), em);
		EntityUtil.deleteEntity(admission, em);
		return new AutocompleteResult();
	}

	public EnterpriseAdmissionWithStudentGroupsDto getAdmission(PracticeAdmission admission) {
		EnterpriseAdmissionWithStudentGroupsDto dto = new EnterpriseAdmissionWithStudentGroupsDto();
		dto.setAddInfo(admission.getAddInfo());
		dto.setId(admission.getId());
		if (admission.getPlaces() != null) dto.setPlaces(admission.getPlaces().toString());
		dto.setValidFrom(admission.getValidFrom());
		dto.setValidThru(admission.getValidThru());
		dto.setIsStrict(admission.getIsStrict());
		if (admission.getPracticeAdmissionStudentGroups() != null) {
			List<AutocompleteResult> studentGroups = new ArrayList<>();
			for (PracticeAdmissionStudentGroup pasg : admission.getPracticeAdmissionStudentGroups()) {
				StudentGroup sg = pasg.getStudentGroup();
				AutocompleteResult sgr = StudentGroupResult.of(sg);
				studentGroups.add(sgr);
			}
			dto.setStudentGroups(studentGroups);
		}
		return dto;
	}

	public Page<StudentPracticeStatisticsDto> getStudentStatistics(HoisUserDetails user,
			StudentPracticeStatisticsSearchCommand command, Pageable pageable) {
		String searchString = "from contract c  "
				+ "join student stu on stu.id = c.student_id "
				+ "join person p on stu.person_id = p.id "
				+ "join school sch on sch.id = stu.school_id "
				+ "join enterprise e on e.id = c.enterprise_id "
				+ "join student_group sg on stu.student_group_id = sg.id ";
		JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(searchString).sort(pageable);
		qb.requiredCriteria("sch.id = :schoolId", "schoolId", user.getSchoolId());
		qb.optionalCriteria("e.id = :enterpriseId", "enterpriseId", command.getEnterprise());
		qb.optionalCriteria("stu.id = :studentId", "studentId", command.getStudent());
		qb.optionalCriteria("stu.student_group_id = :studentGroupId", "studentGroupId", command.getStudentGroup());
		qb.optionalCriteria("c.start_date >= :startDate", "startDate", command.getStartDate());
	    qb.optionalCriteria("c.end_date <= :endDate", "endDate", command.getEndDate());
	    qb.optionalCriteria("c.status_code = :status", "status", command.getStatus());
		String selectString = "c.id, c.contract_nr, concat(p.firstname, ' ', p.lastname), sg.code, c.start_date, c.end_date, e.name, c.status_code";
        return JpaQueryUtil.pagingResult(qb, selectString, em, pageable).map(r -> {
        	StudentPracticeStatisticsDto dto = new StudentPracticeStatisticsDto();
            dto.setId(resultAsLong(r, 0));
            dto.setContractNr(resultAsString(r, 1));
            dto.setStudent(resultAsString(r, 2));
            dto.setStudentGroup(resultAsString(r, 3));
            dto.setStartDate(JpaQueryUtil.resultAsLocalDate(r, 4));
            dto.setEndDate(JpaQueryUtil.resultAsLocalDate(r, 5));
            dto.setEnterprise(resultAsString(r, 6));
            dto.setStatus(resultAsString(r, 7));
            return dto;
        });
	}
	
	public byte[] searchExcel(HoisUserDetails user, ContractStatisticsCommand criteria) {
        Map<String, Object> data = new HashMap<>();
        data.put("criteria", criteria);
        data.put("documents", getContractStatistics(user, criteria, new PageRequest(0, Integer.MAX_VALUE)));
        return xlsService.generate("practicecontractstatistics.xls", data);
    }
	
	public byte[] searchExcel(HoisUserDetails user, StudentPracticeStatisticsSearchCommand criteria) {
		Map<String, Object> data = new HashMap<>();
        data.put("criteria", criteria);
        data.put("documents", getStudentStatistics(user, criteria, new PageRequest(0, Integer.MAX_VALUE)));
        return xlsService.generate("practicestudentstatistics.xls", data);
	}
	
	public byte[] searchExcel(HoisUserDetails user, StudyYearStatisticsCommand criteria) {
        Map<String, Object> data = new HashMap<>();
        data.put("criteria", criteria);
        data.put("documents", getStudyYearStatistics(user, criteria, new PageRequest(0, Integer.MAX_VALUE)));
        return xlsService.generate("practicestudyyearstatistics.xls", data);
    }
	
	public EnterpriseImportResultDto importCsv(byte[] fileData, HoisUserDetails user) {
        CsvSchema schema = csvMapper.schemaFor(PracticeEnterpriseCsvRow.class).withHeader().withColumnSeparator(';');
        List<Classifier> oppeClassifiers = classifierService.findAllByMainClassCode(MainClassCode.OPPEKEEL);
		List<String> oppeClassifierCodes = oppeClassifiers.stream().map(p->p.getCode()).collect(Collectors.toList());
		List<Classifier> riikClassifiers = classifierService.findAllByMainClassCode(MainClassCode.RIIK);
		List<String> riikClassifierCodes = riikClassifiers.stream().map(p->p.getCode()).collect(Collectors.toList());
        EnterpriseImportResultDto dto = new EnterpriseImportResultDto();
        ClassifierCache classifiers = new ClassifierCache(classifierService);

        EntityUtil.setUsername(user.getUsername(), em);

        String fileContent = getContent(fileData);

        int rowNr = 1;
        try (MappingIterator<PracticeEnterpriseCsvRow> csvValues = csvMapper.readerFor(PracticeEnterpriseCsvRow.class).with(schema).readValues(fileContent)) {
            while (csvValues.hasNext()) {
            	PracticeEnterpriseCsvRow row = csvValues.next();
                try {
                      proccessRow(row, rowNr, dto, user, riikClassifierCodes, oppeClassifierCodes);
                  } catch (Exception e) {
                      dto.getFailed().add(new EnterpriseImportedRowMessageDto(rowNr, "Viga rea töötlemisel"));
                      log.error(e.getMessage(), e);
                  }
                rowNr ++;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            dto.getFailed().add(new EnterpriseImportedRowMessageDto(rowNr, "Tundmatu viga."));
        }

        return dto;
    }
	
	private static String getContent(byte[] fileData) {
        //ISO-8859-1 vs Windows-1252: ISO-8859-1 (also called Latin-1) is identical to Windows-1252 (also called CP1252) except for the code points 128-159 (0x80-0x9F)
        //ISO-8859-1 vs ISO-8859-15: These 2 encodings are identical except for 8 code points, which causes confusion between the two of them as well as with Windows-1252.
        //ISO-8859-1 vs us ASCII the first 127 characters are the same
        CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();
        try {
            decoder.decode(ByteBuffer.wrap(fileData));
        } catch (CharacterCodingException e) {
            return new String(fileData, StandardCharsets.ISO_8859_1);
        }
        return new String(fileData, StandardCharsets.UTF_8);
    }
	
	private static String missingMessage(String fieldName) {
		return String.format("Puudub välja %s väärtus.", fieldName);
	}
	
	private static String incorrectMessage(String fieldName) {
		return String.format("Välja %s väärtus ei ole korrektne.", fieldName);
	}
	
	private static String incorrectBooleanMessage(String fieldName) {
		return String.format("Välja %s väärtus ei ole korrektne, väärtus saab olla ainult 'J'(jah) või 'E'(ei).", fieldName);
	}
	
	private static String maxValueMessage(String fieldName, Integer maxValue) {
        return String.format("Välja %s maksimaalne pikkus on " + maxValue, fieldName);
    }
	
	private static Boolean getBooleanValue(String field) {
		if ("J".equalsIgnoreCase(field)) {
			return Boolean.TRUE;
		} else if ("E".equalsIgnoreCase(field)) {
			return Boolean.FALSE;
		} else {
			return null;
		}
	}
	
	private void proccessRow(PracticeEnterpriseCsvRow row, int rowNr, EnterpriseImportResultDto dto,
			HoisUserDetails user, List<String> riikClassifierCodes, List<String> oppeClassifierCodes) {
		List<EnterpriseImportedRowMessageDto> failed = new ArrayList<>();
		List<EnterpriseImportedRowMessageDto> succeeded = new ArrayList<>();
		//Kontaktisiku andmete olemasolu kontroll
		Boolean isPersonSaveNeeded = Boolean.FALSE;
		if(!StringUtils.isBlank(row.getContactPersonAddInfo())
		|| !StringUtils.isBlank(row.getContactPersonEmail())
		|| !StringUtils.isBlank(row.getContactPersonFirstname())
		|| !StringUtils.isBlank(row.getContactPersonIdCode())
		|| !StringUtils.isBlank(row.getContactPersonIdCodeCountry())
		|| !StringUtils.isBlank(row.getContactPersonIsContact())
		|| !StringUtils.isBlank(row.getContactPersonIsSupervisor())
		|| !StringUtils.isBlank(row.getContactPersonLastname())
		|| !StringUtils.isBlank(row.getContactPersonPhone())
		|| !StringUtils.isBlank(row.getContactPersonProfession())) {
			isPersonSaveNeeded = Boolean.TRUE;
			if (StringUtils.isBlank(row.getContactPersonFirstname())) {
				failed.add(new EnterpriseImportedRowMessageDto(rowNr, missingMessage("SeotudIsikuEesnimi")));
			} else {
			    if (row.getContactPersonFirstname().length() > 100) {
			        failed.add(new EnterpriseImportedRowMessageDto(rowNr, maxValueMessage("SeotudIsikuEesnimi", new Integer(100))));
			    }
			}
			if (StringUtils.isBlank(row.getContactPersonLastname())) {
				failed.add(new EnterpriseImportedRowMessageDto(rowNr, missingMessage("SeotudIsikuPerekonnanimi")));
			} else {
                if (row.getContactPersonLastname().length() > 100) {
                    failed.add(new EnterpriseImportedRowMessageDto(rowNr, maxValueMessage("SeotudIsikuPerekonnanimi", new Integer(100))));
                }
            }
			if (StringUtils.isBlank(row.getContactPersonPhone())) {
				failed.add(new EnterpriseImportedRowMessageDto(rowNr, missingMessage("SeotudIsikuKontakttelefon")));
			} else {
                if (row.getContactPersonPhone().length() > 100) {
                    failed.add(new EnterpriseImportedRowMessageDto(rowNr, maxValueMessage("SeotudIsikuKontakttelefon", new Integer(100))));
                }
            }
			if (!StringUtils.isBlank(row.getContactPersonEmail()) && row.getContactPersonEmail().length() > 100) {
                failed.add(new EnterpriseImportedRowMessageDto(rowNr, maxValueMessage("SeotudIsikuEpost", new Integer(100))));
            }
			if (!StringUtils.isBlank(row.getContactPersonIdCode()) && !riikClassifierCodes.contains("RIIK_" + row.getContactPersonIdCodeCountry())) {
				failed.add(new EnterpriseImportedRowMessageDto(rowNr, incorrectMessage("SeotudIsikuIsikukoodRiik")));
			}
            if (!StringUtils.isBlank(row.getContactPersonIdCode()) && row.getContactPersonIdCode().length() > 50) {
                failed.add(new EnterpriseImportedRowMessageDto(rowNr, maxValueMessage("SeotudIsikuIsikukood", new Integer(50))));
            }
			if (StringUtils.isBlank(row.getContactPersonIsSupervisor())) {
				failed.add(new EnterpriseImportedRowMessageDto(rowNr, missingMessage("SeotudIsikOnJuhendaja")));
			}
			if (StringUtils.isBlank(row.getContactPersonIsContact())) {
				failed.add(new EnterpriseImportedRowMessageDto(rowNr, missingMessage("SeotudIsikOnKontaktisik")));
			}
		}
		
		//Üldiste andmete olemasolu kontroll
		
		if(StringUtils.isBlank(row.getName())) {
			failed.add(new EnterpriseImportedRowMessageDto(rowNr, missingMessage("Nimi")));
		} else {
            if (row.getName().length() > 100) {
                failed.add(new EnterpriseImportedRowMessageDto(rowNr, maxValueMessage("Nimi", new Integer(100))));
            }
        }
		if(StringUtils.isBlank(row.getCountry())) {
			failed.add(new EnterpriseImportedRowMessageDto(rowNr, missingMessage("Riik")));
		} else { 
		    if (!riikClassifierCodes.contains("RIIK_" + row.getCountry())) {
		        failed.add(new EnterpriseImportedRowMessageDto(rowNr, incorrectMessage("Riik")));
		    }
		}
		if(StringUtils.isBlank(row.getPerson())) {
			failed.add(new EnterpriseImportedRowMessageDto(rowNr, missingMessage("Eraisik")));
		}
		if(StringUtils.isBlank(row.getCollectiveLanguage())) {
			failed.add(new EnterpriseImportedRowMessageDto(rowNr, missingMessage("KollektiiviKeel")));
		} else {
		    if (!oppeClassifierCodes.contains("OPPEKEEL_" + row.getCollectiveLanguage())) {
			failed.add(new EnterpriseImportedRowMessageDto(rowNr, incorrectMessage("KollektiiviKeel")));
		    }
		}
		if(StringUtils.isBlank(row.getStudentCanApply())) {
			failed.add(new EnterpriseImportedRowMessageDto(rowNr, missingMessage("ÕppijaSaabTaotleda")));
		}
		if(!StringUtils.isBlank(row.getCountry())
		        && !StringUtils.isBlank(row.getPerson())
		        && !"J".equals(row.getPerson())
				&& "EST".equals(row.getCountry())
				&& StringUtils.isBlank(row.getRegCode())) {
			failed.add(new EnterpriseImportedRowMessageDto(rowNr, missingMessage("Registrikood")));
		} else {
		    if (row.getRegCode() != null && row.getRegCode().length() > 20) {
                failed.add(new EnterpriseImportedRowMessageDto(rowNr, maxValueMessage("Registrikood", new Integer(20))));
            }
		}
		
		//Andmete valideerimine
		//Boolean tüüpi väljad
		
		if (!StringUtils.isBlank(row.getPerson()) && !"J".equalsIgnoreCase(row.getPerson()) && !"E".equalsIgnoreCase(row.getPerson())) {
			failed.add(new EnterpriseImportedRowMessageDto(rowNr, incorrectBooleanMessage("Eraisik")));
		}
		if (!StringUtils.isBlank(row.getStudentCanApply()) && !"J".equalsIgnoreCase(row.getStudentCanApply()) && !"E".equalsIgnoreCase(row.getStudentCanApply())) {
			failed.add(new EnterpriseImportedRowMessageDto(rowNr, incorrectBooleanMessage("ÕppijaSaabTaotleda")));
		}
		if (!StringUtils.isBlank(row.getContactPersonIsSupervisor()) && !"J".equalsIgnoreCase(row.getContactPersonIsSupervisor()) && !"E".equalsIgnoreCase(row.getContactPersonIsSupervisor())) {
			failed.add(new EnterpriseImportedRowMessageDto(rowNr, incorrectBooleanMessage("SeotudIsikOnJuhendaja")));
		}
		if (!StringUtils.isBlank(row.getContactPersonIsContact()) && !"J".equalsIgnoreCase(row.getContactPersonIsContact()) && !"E".equalsIgnoreCase(row.getContactPersonIsContact())) {
			failed.add(new EnterpriseImportedRowMessageDto(rowNr, incorrectBooleanMessage("SeotudIsikOnKontaktisik")));
		}
		
		//Eesti isikukoodi kontroll
		
		if (!StringUtils.isBlank(row.getContactPersonIdCode()) 
				&& "EST".equals(row.getContactPersonIdCodeCountry())
				&& !idCodeValidator.isValid(row.getContactPersonIdCode(), null)) {
			failed.add(new EnterpriseImportedRowMessageDto(rowNr, incorrectMessage("SeotudIsikuIsikukood")));
		}
		
		//Eesti registrikoodi kontroll - numbrid ja 8 märki, kui pole
		
		if (!StringUtils.isBlank(row.getCountry()) 
				&& !StringUtils.isBlank(row.getPerson())
				&& "E".equalsIgnoreCase(row.getPerson())
				&& "EST".equals(row.getCountry())
				&& (row.getRegCode().length() != 8
				|| !StringUtils.isNumeric(row.getRegCode()))) {
			failed.add(new EnterpriseImportedRowMessageDto(rowNr, "Eesti registrikood peab olema 8-kohaline täisarv."));
		}
		
		if (!StringUtils.isBlank(row.getCountry())
				&& !"EST".equals(row.getCountry())
				&& !StringUtils.isBlank(row.getAddressAds())) {
			failed.add(new EnterpriseImportedRowMessageDto(rowNr, "Kui riik pole Eesti(EST), siis AadressADSKoond ei tohi olla täidetud."));
		}
		
		if (!StringUtils.isBlank(row.getCountry())	
				&& !"EST".equals(row.getCountry())
				&& !StringUtils.isBlank(row.getAddressOid())) {
			failed.add(new EnterpriseImportedRowMessageDto(rowNr, "Kui riik pole Eesti(EST), siis AadressADSOID ei tohi olla täidetud."));
		}
		
		if (failed.isEmpty()) {
			if (row.getCountry().equals("EST") && "E".equalsIgnoreCase(row.getPerson())) {
				EnterpriseRegCodeCheckDto regCodeCheckDto = new EnterpriseRegCodeCheckDto();
				regCodeCheckDto.setRegCode(row.getRegCode());
				regCodeCheckDto.setCountry(row.getCountry());
				EnterpriseRegCodeResponseDto response = regCodeWithoutCheck(user, regCodeCheckDto);
				if (!StringUtils.isBlank(response.getStatus())) {
					failed.add(new EnterpriseImportedRowMessageDto(rowNr, response.getStatus()));
					dto.getFailed().addAll(failed);
					return;
				}
                if (StringUtils.isBlank(row.getAddress())) {
                	row.setAddress(response.getAddress());
                }
                if (StringUtils.isBlank(row.getAddressAds())) {
                	row.setAddressAds(response.getAddressAds());
                }
                if (StringUtils.isBlank(row.getAddressOid())) {
                	row.setAddressOid(response.getAddressOid());
                }
			}
			Enterprise enterprise = null;
			EnterpriseSchool school = null;
			try {
				enterprise = em.createQuery("select e from Enterprise e "
		        		+ ("J".equalsIgnoreCase(row.getPerson()) ? "where lower(e.name) = '" + row.getName().toLowerCase() + "' ": "where e.regCode = '" + row.getRegCode() + "' ")
		        		+ "and e.country.code = ?1", Enterprise.class)
		        		.setParameter(1, "RIIK_" + row.getCountry())
		        		.getSingleResult();
			} catch (NoResultException | NonUniqueResultException t) {
				if (t instanceof NonUniqueResultException) {
					failed.add(new EnterpriseImportedRowMessageDto(rowNr, "Süsteemis on mitu sarnast ettevõtet."));
					dto.getFailed().addAll(failed);
					return;
				}
			}
			try {
				school = em.createQuery("select s from EnterpriseSchool s "
		        		+ "where s.school.id = ?1 "
		        		+ ("J".equalsIgnoreCase(row.getPerson()) ? "and lower(s.enterprise.name) = '" + row.getName().toLowerCase() + "' " : "and s.enterprise.regCode = '" + row.getRegCode() + "' ")
		        		+ "and s.enterprise.country.code = ?2", EnterpriseSchool.class)
		        		.setParameter(1, user.getSchoolId())
		        		.setParameter(2, "RIIK_" + row.getCountry())
		        		.getSingleResult();
			} catch (NoResultException | NonUniqueResultException t) {
				if (t instanceof NonUniqueResultException) {
					failed.add(new EnterpriseImportedRowMessageDto(rowNr, "Süsteemis on mitu sarnast ettevõttega seotud kooli."));
					dto.getFailed().addAll(failed);
					return;
				}
			}
			if (enterprise != null) {
				if (enterprise.getPerson() == null) {
					enterprise.setPerson(getBooleanValue(row.getPerson()));
				}
				if (enterprise.getCountry() == null) {
					enterprise.setCountry(em.getReference(Classifier.class, "RIIK_" + row.getCountry()));
				}
				if (StringUtils.isBlank(enterprise.getRegCode()) && "E".equalsIgnoreCase(row.getPerson())) {
					enterprise.setRegCode(row.getRegCode());
				}
				if (enterprise.getPerson().booleanValue()) {
				    enterprise.setRegCode(null);
				}
				if (StringUtils.isBlank(enterprise.getName())) {
					enterprise.setName(row.getName());
				}
				succeeded.add(new EnterpriseImportedRowMessageDto(rowNr, "Muudetud ettevõte.", enterprise.getRegCode(), enterprise.getName()));
			} else {
				enterprise = new Enterprise();
				enterprise.setName(row.getName());
				if ("E".equalsIgnoreCase(row.getPerson())) {
				    enterprise.setRegCode(row.getRegCode());
				}
				enterprise.setPerson(getBooleanValue(row.getPerson()));
				enterprise.setCountry(em.getReference(Classifier.class, "RIIK_" + row.getCountry()));
				succeeded.add(new EnterpriseImportedRowMessageDto(rowNr, "Lisatud ettevõte.", enterprise.getRegCode(), enterprise.getName()));
			}
			if (school != null) {
				school.setActive(Boolean.TRUE);
				if (school.getApplication() == null) {
					school.setApplication(getBooleanValue(row.getStudentCanApply()));
				}
				if (StringUtils.isBlank(school.getAddInfo())) {
					school.setAddInfo(row.getNotes());
				}
				if (school.getPlaces() == null) {
				    try {
                        if (row.getPlaces().length() < 0 || row.getPlaces().length() > 4) {
                            failed.add(new EnterpriseImportedRowMessageDto(rowNr, "PraktikakohtadeArv peab olema 4-kohaline positiivne täisarv."));
                            dto.getFailed().addAll(failed);
                            return;
                        }
                        Integer places = new Integer(row.getPlaces());
                        if (places.intValue() < 0) {
                            failed.add(new EnterpriseImportedRowMessageDto(rowNr, "PraktikakohtadeArv peab olema 4-kohaline positiivne täisarv."));
                            dto.getFailed().addAll(failed);
                            return;
                        }
                        school.setPlaces(places);
                    } catch(NumberFormatException e) {
                        failed.add(new EnterpriseImportedRowMessageDto(rowNr, incorrectMessage("PraktikakohtadeArv")));
                        dto.getFailed().addAll(failed);
                        return;
                    }
				}
				if (StringUtils.isBlank(school.getPlacesDescr())) {
					school.setPlacesDescr(row.getInternshipAddInfo());
				}
				if (StringUtils.isBlank(school.getAddress())) {
					school.setAddress(row.getAddress());
				}
				if (StringUtils.isBlank(school.getAddressAds())) {
					school.setAddressAds(row.getAddressAds());
				}
				if (StringUtils.isBlank(school.getAddressOid())) {
					school.setAddressOid(row.getAddressOid());
				}
				if (StringUtils.isBlank(school.getEmail())) {
					school.setEmail(row.getEmail());
				}
				if (StringUtils.isBlank(school.getPostcode())) {
					school.setPostcode(row.getZipCode());
				}
				school.setLanguage(em.getReference(Classifier.class, "OPPEKEEL_" + row.getCollectiveLanguage()));
				if (StringUtils.isBlank(school.getPhone())) {
					school.setPhone(row.getContactPhone());
				}
				succeeded.add(new EnterpriseImportedRowMessageDto(rowNr, "Muudetud kool.", enterprise.getRegCode(), enterprise.getName()));
			} else {
				school = new EnterpriseSchool();
				school.setActive(Boolean.TRUE);
				school.setApplication(getBooleanValue(row.getStudentCanApply()));
				school.setAddInfo(row.getNotes());
				if (row.getPlaces() != null) {
				    try {
                        if (row.getPlaces().length() < 0 || row.getPlaces().length() > 4) {
                            failed.add(new EnterpriseImportedRowMessageDto(rowNr, "PraktikakohtadeArv peab olema 4-kohaline positiivne täisarv."));
                            dto.getFailed().addAll(failed);
                            return;
                        }
                        Integer places = new Integer(row.getPlaces());
                        if (places.intValue() < 0) {
                            failed.add(new EnterpriseImportedRowMessageDto(rowNr, "PraktikakohtadeArv peab olema 4-kohaline positiivne täisarv."));
                            dto.getFailed().addAll(failed);
                            return;
                        }
                        school.setPlaces(places);
                    } catch(NumberFormatException e) {
                        failed.add(new EnterpriseImportedRowMessageDto(rowNr, incorrectMessage("PraktikakohtadeArv")));
                        dto.getFailed().addAll(failed);
                        return;
                    }
				}
				school.setPlacesDescr(row.getInternshipAddInfo());
				school.setAddress(row.getAddress());
				school.setAddressAds(row.getAddressAds());
				school.setAddressOid(row.getAddressOid());
				school.setEmail(row.getEmail());
				school.setPostcode(row.getZipCode());
				school.setLanguage(em.getReference(Classifier.class, "OPPEKEEL_" + row.getCollectiveLanguage()));
				school.setPhone(row.getContactPhone());
				school.setEnterprise(enterprise);
				school.setSchool(em.getReference(School.class, user.getSchoolId()));
				succeeded.add(new EnterpriseImportedRowMessageDto(rowNr, "Lisatud kool.", enterprise.getRegCode(), enterprise.getName()));
			}
			EnterpriseSchoolPerson person = null;
			if (isPersonSaveNeeded.booleanValue()) {
				for (EnterpriseSchoolPerson personFromSchool : school.getEnterpriseSchoolPersons()) {
					if(personFromSchool.getFirstname() != null && personFromSchool.getLastname() != null 
							&& personFromSchool.getFirstname().equalsIgnoreCase(row.getContactPersonFirstname())
					&& personFromSchool.getLastname().equalsIgnoreCase(row.getContactPersonLastname())) {
						if (personFromSchool.getIdcode() == null || personFromSchool.getIdcodeCountry() == null ||
								(("RIIK_" + row.getContactPersonIdCodeCountry()).equalsIgnoreCase(personFromSchool.getIdcodeCountry().getCode()))
								&& row.getContactPersonIdCode().equalsIgnoreCase(personFromSchool.getIdcode())) {
							person = personFromSchool;
							person.setContact(getBooleanValue(row.getContactPersonIsContact()));
							person.setSupervisor(getBooleanValue(row.getContactPersonIsSupervisor()));
							person.setEmail(row.getEmail());
							if (StringUtils.isBlank(person.getIdcode())) {
								person.setIdcode(row.getContactPersonIdCode());
							}
							if (person.getIdcodeCountry() == null && !StringUtils.isBlank(row.getContactPersonIdCodeCountry()) && riikClassifierCodes.contains("RIIK_" + row.getContactPersonIdCodeCountry())) {
								person.setIdcodeCountry(em.getReference(Classifier.class, "RIIK_" + row.getContactPersonIdCodeCountry()));
							}
							person.setPhone(row.getContactPersonPhone());
							person.setPosition(row.getContactPersonProfession());
							succeeded.add(new EnterpriseImportedRowMessageDto(rowNr, "Muudetud kontaktisik " + 
							person.getFirstname() + " " + person.getLastname() + ".", enterprise.getRegCode(), enterprise.getName()));
						}
					}
				}
				if (person == null) {
					person = new EnterpriseSchoolPerson();
					person.setFirstname(row.getContactPersonFirstname());
					person.setLastname(row.getContactPersonLastname());
					person.setContact(getBooleanValue(row.getContactPersonIsContact()));
					person.setSupervisor(getBooleanValue(row.getContactPersonIsSupervisor()));
					person.setEmail(row.getEmail());
					person.setIdcode(row.getContactPersonIdCode());
					if (!StringUtils.isBlank(row.getContactPersonIdCodeCountry()) && riikClassifierCodes.contains("RIIK_" + row.getContactPersonIdCodeCountry())) {
					    person.setIdcodeCountry(em.getReference(Classifier.class, "RIIK_" + row.getContactPersonIdCodeCountry()));
					}
					person.setPhone(row.getContactPersonPhone());
					person.setPosition(row.getContactPersonProfession());
					person.setEnterpriseSchool(school);
					succeeded.add(new EnterpriseImportedRowMessageDto(rowNr, "Lisatud kontaktisik " + 
							person.getFirstname() + " " + person.getLastname() + ".", enterprise.getRegCode(), enterprise.getName()));
				}
			}
			
			if (failed.isEmpty()) {
				EntityUtil.save(enterprise, em);
				EntityUtil.save(school, em);
				if (person != null) {
					EntityUtil.save(person, em);
				}
				dto.getSuccessful().addAll(succeeded);
			}
		} else {
			dto.getFailed().addAll(failed);
		}
    }
	
	public String sampleCsvFile() {
        return "Nimi;Registrikood;Riik;Eraisik;Aadress;AadressADSKoond;AadressADSOID;Sihtnumber;Epost;Kontakttelefon;KollektiiviKeel;PraktikakohtadeArv;PraktikakohtadeSelgitus;Märkused;ÕppijaSaabTaotleda;SeotudIsikuEesnimi;SeotudIsikuPerekonnanimi;SeotudIsikuKontakttelefon;SeotudIsikuEpost;SeotudIsikuIsikukood;SeotudIsikuIsikukoodiRiik;SeotudIsikuAmetinimetus;SeotudIsikuMärkused;SeotudIsikOnJuhendaja;SeotudIsikOnKontaktisik\r\n"
        		+ "AS Practika & Co;10333451;EST;E;Saare maakond, Saaremaa vald, Kuressaare linn, Marientali tee 19;74714365500000RN80000ASBE00000000;EE00802404;10222;practica@mingiemail.com;552323232;E;3;Saada koka praktikakohti;Hea koostöö;E;Mart;Ilves;52323232;mart.ilves.teine@gmail.fi.edu;39044220321;EST;Tegevjuht;Ei ole usaldusväärne;J;E\r\n"
        		+ "AS Practika & Co;10333451;EST;E;Saare maakond, Saaremaa vald, Kuressaare linn, Marientali tee 19;74714365500000RN80000ASBE00000000;EE00802404;;practica@mingiemail.com;552323232;E;3;Saada koka praktikakohti;Hea koostöö;E;Toomas;Tamm-Ivanovs;55434423;;;;Assistent;;J;J";

    }

	public Page<ContractStatisticsDto> getContractStatistics(HoisUserDetails user,
			ContractStatisticsCommand command, Pageable pageable) {
		String searchString = "from contract c  "
				+ "join student stu on stu.id = c.student_id "
				+ "join person p on stu.person_id = p.id "
				+ "join school sch on sch.id = stu.school_id "
				+ "join enterprise e on e.id = c.enterprise_id "
				+ "join student_group sg on stu.student_group_id = sg.id ";
		JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(searchString).sort(pageable);
		qb.requiredCriteria("sch.id = :schoolId", "schoolId", user.getSchoolId());
		qb.optionalCriteria("e.id = :enterpriseId", "enterpriseId", command.getEnterprise());
		qb.optionalCriteria("stu.id = :studentId", "studentId", command.getStudent());
		qb.optionalCriteria("stu.student_group_id = :studentGroupId", "studentGroupId", command.getStudentGroup());
		qb.optionalCriteria("c.canceled >= :startDate", "startDate", command.getStartDate());
	    qb.optionalCriteria("c.canceled <= :endDate", "endDate", command.getEndDate());
	    qb.optionalCriteria("c.cancel_reason_code = :cancelReason", "cancelReason", command.getCancelReason());
	    qb.filter("c.status_code = 'LEPING_STAATUS_T'");
		String selectString = "c.id, c.contract_nr, concat(p.firstname, ' ', p.lastname), sg.code, c.start_date, c.end_date, e.name, c.canceled, c.cancel_reason_code, c.cancel_desc";
        return JpaQueryUtil.pagingResult(qb, selectString, em, pageable).map(r -> {
        	ContractStatisticsDto dto = new ContractStatisticsDto();
            dto.setId(resultAsLong(r, 0));
            dto.setContractNr(resultAsString(r, 1));
            dto.setStudent(resultAsString(r, 2));
            dto.setStudentGroup(resultAsString(r, 3));
            dto.setStartDate(JpaQueryUtil.resultAsLocalDate(r, 4));
            dto.setEndDate(JpaQueryUtil.resultAsLocalDate(r, 5));
            dto.setEnterprise(resultAsString(r, 6));
            dto.setCancelDate(JpaQueryUtil.resultAsLocalDate(r, 7));
            dto.setCancelCode(resultAsString(r, 8));
            dto.setCancelReason(resultAsString(r, 9));
            return dto;
        });
	}

	public Page<StudyYearStatisticsDto> getStudyYearStatistics(HoisUserDetails user, StudyYearStatisticsCommand command,
			Pageable pageable) {
	    String groupByFields = "sg.code, cu.name_et, cu.name_en, cu.code, cl.name_et, cl.name_en, sg.course";
	    JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from practice_journal pj "
	            + "join student stu on stu.id = pj.student_id "
	            + "join study_year sy on sy.id = pj.study_year_id "
	            + "join student_group sg on stu.student_group_id = sg.id "
	            + "join person pe on pe.id = stu.person_id "
	            + "join curriculum_version cv on cv.id = stu.curriculum_version_id "
	            + "join curriculum cu on cu.id = cv.curriculum_id "
	            + "join classifier cl on cl.code = cu.isced_class_code "
	            + "left join contract co on co.id = pj.contract_id "
	            + "left join enterprise e on e.id = co.enterprise_id").sort(pageable).groupBy(groupByFields);
	    qb.optionalCriteria("sy.year_code = :yearCode" , "yearCode", command.getStudyYear());
	    qb.requiredCriteria("sy.school_id = :schoolId" , "schoolId", user.getSchoolId());
	    String select = "sg.code, cu.name_et as curriculumNameEt, cu.name_en as curriculumNameEn, cu.code as curriculumCode, cl.name_et, cl.name_en, sg.course,"
                + " string_agg(concat(pe.firstname, ' ', pe.lastname, '(', pj.teacher_opinion, ')'), '; ') as personNames,"
                + " string_agg(pj.grade_code, ';') as gradeCodes, string_agg(distinct e.name, '; ') as enterpriseNames";
	    return JpaQueryUtil.pagingResult(qb, select, em, pageable).map(r -> {
	        StudyYearStatisticsDto dto = new StudyYearStatisticsDto();
	        String nameString = resultAsString(r, 7);
	        List<String> nameArray = Arrays.asList(nameString.split("; "));
	        String gradeString = resultAsString(r, 8);
	        double totalGrade = 0;
	        int passedStudents = 0;
	        if (gradeString != null) {
    	        List<String> gradeArray = Arrays.asList(gradeString.split(";"));
    	        // Kutsehindamine 
    	        List<String> positiveGrades = OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE;
    	        // Kõrghindamine
    	        positiveGrades.addAll(HigherAssessment.GRADE_POSITIVE);
    	        for (String grade : gradeArray) {
                    if (positiveGrades.contains(grade)) {
    	                passedStudents ++;
    	            }
                    if (OccupationalGrade.KUTSEHINDAMINE_5.name().equals(grade) || HigherAssessment.KORGHINDAMINE_5.name().equals(grade)) {
                        totalGrade += HigherAssessment.KORGHINDAMINE_5.getMark();
                    } else if (OccupationalGrade.KUTSEHINDAMINE_4.name().equals(grade) || HigherAssessment.KORGHINDAMINE_4.name().equals(grade)) {
                        totalGrade += HigherAssessment.KORGHINDAMINE_4.getMark();
                    } else if (OccupationalGrade.KUTSEHINDAMINE_3.name().equals(grade) || HigherAssessment.KORGHINDAMINE_3.name().equals(grade)) {
                        totalGrade += HigherAssessment.KORGHINDAMINE_3.getMark();
                    } else if (HigherAssessment.KORGHINDAMINE_2.name().equals(grade)) {
                        totalGrade += HigherAssessment.KORGHINDAMINE_2.getMark();
                    } else if (HigherAssessment.KORGHINDAMINE_1.name().equals(grade)) {
                        totalGrade += HigherAssessment.KORGHINDAMINE_1.getMark();
                    }
    	        }
    	        dto.setTotalFailed(nameArray.size() - passedStudents);
    	        if (totalGrade != 0.00) {
    	            dto.setGrade(new BigDecimal(totalGrade / passedStudents).setScale(2, RoundingMode.HALF_UP).doubleValue());
    	        }
	        } else {
	            dto.setTotalFailed(nameArray.size());
	        }
	        dto.setTotalCompleted(passedStudents);
	        dto.setStudentGroup(resultAsString(r, 0));
	        dto.setTotalWent(nameArray.size());
	        dto.setCurriculumName(new AutocompleteResult(null, resultAsString(r, 1), resultAsString(r, 2)));
	        dto.setCurriculumCode(resultAsString(r, 3));
	        dto.setCurriculumGroup(new AutocompleteResult(null, resultAsString(r, 4), resultAsString(r, 5)));
	        dto.setCourse(resultAsLong(r, 6));
	        Set<String> nameSet = new HashSet<>(nameArray.stream()
	                .map(name -> name.endsWith("()") ? name.substring(0, name.length() - 2) : name)
	                .collect(Collectors.toList())); 
	        dto.setNameAndReason(String.join("; ", nameSet));
	        dto.setEnterprises(resultAsString(r, 9)); 
            return dto;
        });
	}

    public void delete(HoisUserDetails user, EnterpriseSchool enterpriseSchool) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from enterprise_school es join enterprise e on e.id = es.enterprise_id "
                + "join contract c on c.enterprise_id = e.id "
                + "join student s on s.id = c.student_id "
                + "left join enterprise_school_person esp on esp.enterprise_school_id = es.id ");
        qb.requiredCriteria("es.id = :enterpriseSchoolId" , "enterpriseSchoolId", EntityUtil.getId(enterpriseSchool));
        qb.requiredCriteria("s.school_id = :schoolId" , "schoolId", EntityUtil.getId(enterpriseSchool.getSchool()));
        List<?> data = qb.select("c.id", em).getResultList();
        List<Long> contractIds = StreamUtil.toMappedList(r -> {
            return resultAsLong(r, 0);
        }, data);
        if (!contractIds.isEmpty()) {
            throw new HoisException("Ei saa kustutada, ettevõte on seotud praktikalepingutega");
        }
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(enterpriseSchool, em);
    }
    
    public List<ContactDto> enterpriseContacts(HoisUserDetails user, Enterprise enterprise) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from enterprise e "
                + "join enterprise_school es on es.enterprise_id = e.id "
                + "left join enterprise_school_person esp on esp.enterprise_school_id = es.id ").sort("esp.firstname, esp.lastname");
        qb.requiredCriteria("e.id = :esId", "esId", EntityUtil.getId(enterprise));
        qb.requiredCriteria("es.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.filter("esp.is_contact = true");
        List<?> data = qb.select("concat(esp.firstname, ' ', esp.lastname), esp.email, esp.phone", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            ContactDto contact = new ContactDto();
            contact.setContactPersonName(resultAsString(r, 0));
            contact.setContactPersonEmail(resultAsString(r, 1));
            contact.setContactPersonPhone(resultAsString(r, 2));
            return contact;
        }, data);
    }

}
