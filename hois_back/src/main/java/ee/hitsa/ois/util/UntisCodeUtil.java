package ee.hitsa.ois.util;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.teacher.TeacherForm.TeacherPersonForm;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanJournalForm;

import javax.persistence.EntityManager;

public abstract class UntisCodeUtil {

	private static final int GENERATED_CODE_MAX_LENGTH = 12;
	private static String generatedFirstName;
	private static String generatedLastName;

	public static String generateTeacherCode(TeacherPersonForm teacher, List<Teacher> teachers) {
		String generatedCode;
		int subStringIndex = 1;
		boolean brokeCycle = false;
		
		//Last name could be under 4 letters
		if (teacher.getLastname().length() < 4) {
			generatedLastName = teacher.getLastname();
			generatedFirstName =  teacher.getFirstname().substring(0, subStringIndex);
			generatedCode = generatedLastName + generatedFirstName;
		} else {
			generatedLastName = teacher.getLastname().substring(0, 4);
			generatedFirstName =  teacher.getFirstname().substring(0, subStringIndex);
			generatedCode = generatedLastName + generatedFirstName;
		}
		
		//Make the list contain only the same last name prefix persons
		teachers = teachers.stream().filter(p->p.getPerson().getLastname().toLowerCase().startsWith(generatedLastName.toLowerCase())).collect(Collectors.toList());
		
		//Make generated code longer if exists another person with same last name prefix
		while (teachers.stream().anyMatch(p -> p.getPerson().getLastname().toLowerCase().startsWith(generatedLastName.toLowerCase()) &&
				p.getPerson().getFirstname().toLowerCase().startsWith(generatedFirstName.toLowerCase())) &&
				generatedCode.length() <= GENERATED_CODE_MAX_LENGTH) {
			if (teacher.getFirstname().length() >= subStringIndex) {
				generatedFirstName =  teacher.getFirstname().substring(0, subStringIndex);
				generatedCode = generatedLastName + generatedFirstName;
				subStringIndex ++;
			} else {
				brokeCycle = true;
				break;
			}
		}
		
		//Deal with cycle breaks or while cycle not breaking before the code is 12 letters long
		if (brokeCycle || generatedCode.length() == 12) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMM");
			generatedCode = generatedCode.substring(0, 5) + teacher.getBirthdate().format(formatter);
		}
		
		return generatedCode;
	}

	public static String generateJournalCode(Journal journal, LessonPlanJournalForm form, EntityManager em) {
		String generatedCode;
		if (form.getNameEt().length() < 4) {
			generatedCode = form.getNameEt();
		} else if (form.getNameEt().trim().split(" ").length == 1) {
			generatedCode = form.getNameEt().substring(0, 4);
		} else {
			String[] splitted = form.getNameEt().trim().split(" ");
			generatedCode = "";
			for (int index = 0; index < splitted.length; index++) {
				String word = splitted[index];
				if (word.length() < 2) {
					generatedCode += word;
				} else {
					generatedCode += word.substring(0, 2);
				}
				if(generatedCode.length() > 11) {
					break;
				}
			}
		}

		List<Journal> journals = journalsWithUntisCode(journal, generatedCode, em);
		if (journals.size() > 1) {
			generatedCode += "/";
			List<StudentGroup> studentGroups = journalStudentGroups(journal, em);
			for (StudentGroup studentgroup : studentGroups) {
				if (journals.isEmpty() || generatedCode.length() >= 12) break;
				if (studentgroup.getCode().length() >= 12 - generatedCode.length()) {
					generatedCode += studentgroup.getCode().substring(0, 12 - generatedCode.length());
				} else {
					if (studentgroup.getCode().length() >= 12 - generatedCode.length()) {
						generatedCode += studentgroup.getCode().substring(0, 12 - generatedCode.length());
					} else {
						generatedCode += studentgroup.getCode();
					}
				}
				journals = journalsWithUntisCode(journal, generatedCode, em);
			}
		}
		if (journals.size() > 1) {
			generatedCode += "_" + journal.getId();

			journals = journalsWithUntisCode(journal, generatedCode, em);
			if (journals.size() > 1) {
				throw new ValidationFailedException("lessonplan.journal.untisCodeNotUnique",
						Collections.singletonMap("generatedCode", generatedCode));
			}
		}
		return generatedCode;
	}

	public static List<Journal> journalsWithUntisCode(Journal journal, String untisCode, EntityManager em) {
		List<Journal> journals = em.createQuery("select j from Journal j "
				+ "where j.school.id = ?1 and j.studyYear.id = ?2 and j.untisCode = ?3", Journal.class)
				.setParameter(1, EntityUtil.getId(journal.getSchool()))
				.setParameter(2, EntityUtil.getId(journal.getStudyYear()))
				.setParameter(3, untisCode)
				.getResultList();
		if (journals.stream().noneMatch(j -> j.getId().equals(journal.getId()))) {
			journals.add(journal);
		}
		return journals;
	}

	private static List<StudentGroup> journalStudentGroups(Journal journal, EntityManager em) {
		List<?> data = em.createNativeQuery("select lp.student_group_id from journal j "
				+ "join journal_omodule_theme jot on jot.journal_id = j.id "
				+ "join lesson_plan_module lpm on lpm.id = jot.lesson_plan_module_id "
				+ "join lesson_plan lp on lp.id = lpm.lesson_plan_id "
				+ "where j.id = ?1")
				.setParameter(1, journal.getId())
				.getResultList();

		List<Long> studentGroupIds = StreamUtil.toMappedList(r -> resultAsLong(r, 0), data);
		return em.createQuery("select sg from StudentGroup sg where sg.id in ?1", StudentGroup.class)
				.setParameter(1, studentGroupIds)
				.getResultList();
	}
	
}
