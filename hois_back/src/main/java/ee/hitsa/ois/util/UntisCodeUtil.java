package ee.hitsa.ois.util;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.web.commandobject.teacher.TeacherForm.TeacherPersonForm;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanJournalForm;

public class UntisCodeUtil {
	
	private final int GENERATED_CODE_MAX_LENGTH = 12;
	private String generatedFirstName;
	private String generatedCode;
	private String generatedLastName;

	public String generateTeacherCode(TeacherPersonForm teacher, List<Teacher> teachers) {
		int subStringIndex = 1;
		Boolean brokeCycle = false;
		
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
		while (teachers.stream().anyMatch(p->p.getPerson().getLastname().toLowerCase().startsWith(generatedLastName.toLowerCase())) &&
				teachers.stream().anyMatch(p->p.getPerson().getFirstname().toLowerCase().startsWith(generatedFirstName.toLowerCase())) &&
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

	public String generateJournalCode(LessonPlanJournalForm form, List<Journal> journals, List<StudentGroup> studentGroups, Journal journal) {
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
		List<Journal> filtered = journals.stream().filter(p->generatedCode.equals(p.getUntisCode()) && !p.getId().equals(journal.getId())).collect(Collectors.toList());
		if (!filtered.isEmpty()) {
			generatedCode += "/";
			for (StudentGroup studentgroup : studentGroups) {
				if (filtered.isEmpty() || generatedCode.length() >= 12) break;
				if (studentgroup.getCode().length() >= 12 - generatedCode.length()) {
					generatedCode += studentgroup.getCode().substring(0, 12 - generatedCode.length());
				} else {
					if (studentgroup.getCode().length() >= 12 - generatedCode.length()) {
						generatedCode += studentgroup.getCode().substring(0, 12 - generatedCode.length());
					} else {
						generatedCode += studentgroup.getCode();
					}
				}
				filtered = journals.stream().filter(p->generatedCode.equals(p.getUntisCode())).collect(Collectors.toList());
			}
		}
		filtered = journals.stream().filter(p->generatedCode.equals(p.getUntisCode()) && !p.getId().equals(journal.getId())).collect(Collectors.toList());
		if (!filtered.isEmpty()) {
			generatedCode += "_" + journal.getId();
		}
		return generatedCode;
	}
	
}
