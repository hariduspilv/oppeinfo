'use strict';

angular.module('hitsaOis')
  .factory('Menu', function($location, $rootScope, $route, ArrayUtils) {

    var sections = [];

    function getAdminSections(authenticatedUser) {
      sections.push({
        name: 'main.menu.academicCalendar.label',
        type: 'link',
        url: "/academicCalendar",
        icon: "date_range"
      });

      sections.push({
        name: 'main.menu.viewTimetable.label',
        type: 'link',
        url: "/timetable/generalTimetable/group",
        icon: "access_time"
      });

      sections.push({
        name: 'message.messages',
        type: 'link',
        url: "/messages/received",
        icon: "email"
      });

      sections.push({
        name: 'main.menu.curriculum.label', // todo curricula-vs-curriculums
        type: 'toggle',
        icon: "format_list_bulleted",
        pages: [
          {
            name: "main.menu.basemodule.label",
            url: "/basemodule?_menu",
            icon: "format_list_bulleted",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.curriculum.schoolCurriculums',
            url: "/curriculum?_menu",
            icon: "format_list_bulleted"
          },
          {
            name: 'main.menu.curriculum.subjects',
            url: "/subject?_menu",
            studyLevel: {
              higher: true
            },
            icon: "subject"
          },
          {
            name: 'main.menu.curriculum.stateCurriculums',
            url: "/stateCurriculum?_menu",
            icon: "playlist_add_check",
            studyLevel: {
              vocational: true
            }
          },
        ]
      });

      sections.push({
        name: 'main.menu.student.label',
        type: 'toggle',
        icon: 'person',
        pages: [
          {
            name: 'main.menu.student.studentInfo',
            url: '/students?_menu',
            icon: 'person'
          },
          {
            name: 'main.menu.student.studentGroups',
            url: '/studentgroups?_menu',
            icon: "group"
          },
          {
            name: 'main.menu.student.absences',
            url: '/absences?_menu',
            icon: "announcement",
            studyLevel: {
              vocational: true
            },
            absenceAllowed: true
          },
          {
            name: 'main.menu.study.groupAbsences',
            url: "/groupAbsences?_menu",
            icon: "announcement",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.study.remarks',
            url: "/remarks?_menu",
            icon: "warning",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.student.resultCard',
            url: "/studentResultCards?_menu",
            icon: "announcement",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.student.studentCard',
            url: "/studentCards?_menu",
            icon: "announcement"
          },
          {
            name: 'main.menu.student.boardingSchool',
            url: "/boardingSchools?_menu",
            icon: "home"
          },
          {
            name: 'main.menu.student.rrChanges',
            url: "/rr/changelogs?_menu",
            icon:"view_week"
          }
        ]
      });

      sections.push({
        nameHigher: 'main.menu.teachers.labelHigher',
        nameVocational: 'main.menu.teachers.labelVocational',
        type: 'link',
        url: "/teachers?_menu",
        icon: "supervisor_account"
      });

      sections.push({
        name: 'main.menu.documents.label',
        type: 'toggle',
        icon: "library_books",
        pages: [
          {
            name: 'main.menu.documents.applications',
            url: "/applications?_menu",
            icon:"font_download"
          },
          {
            name: 'main.menu.documents.directives',
            url: "/directives?_menu",
            icon:"description"
          },
          {
            name: 'main.menu.documents.certificates',
            url: '/certificate?_menu',
            icon:"folder_shared"
          },
          {
            name: 'main.menu.scholarships.grantApplicationsRanking',
            url: "/scholarships/applications/ranking/grants?_menu",
            icon:"euro_symbol",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.scholarships.scholarshipApplicationsRanking',
            url: "/scholarships/applications/ranking/scholarships?_menu",
            icon:"euro_symbol",
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.scholarships.drGrantApplicationsRanking',
            url: "/scholarships/applications/ranking/drGrants?_menu",
            icon:"functions",
            studyLevel: {
              doctoral: true
            }
          },
          {
            name: 'main.menu.documents.representativeApplications',
            url: "/studentrepresentatives/applications?_menu",
            icon:"explicit"
          }
        ]
      });

      sections.push({
        name: 'main.menu.practice.label',
        type: 'toggle',
        icon: "local_parking",
        pages: [
          {
            name: 'main.menu.practice.practiceEnterprises',
            url: "/practice/enterprise?_menu",
            icon:"search"
          },
          {
            name: 'main.menu.practice.practiceApplications',
            url: '/practice/applications?_menu',
            icon:"font_download"
          },
          {
            name: 'main.menu.practice.practiceEvaluations',
            url: '/practice/evaluation?_menu',
            icon:"rate_review"
          },
          {
            name: 'main.menu.practice.practiceContracts',
            url: "/contracts?_menu",
            icon:"assignment"
          },
          {
            name: 'main.menu.practice.statistics',
            url: "/practice/student/statistics?_menu",
            icon:"blur_circular"
          },
          {
            name: 'main.menu.practice.studentgroupContracts',
            url: "/practice/studentgroup/contracts?_menu",
            icon:"assignment"
          }
        ]
      });

      sections.push({
        name: 'main.menu.study.label',
        type: 'toggle',
        icon: "work",
        pages: [
          {
            name: 'main.menu.study.declarations',
            url: "/declarations?_menu",
            icon:"perm_contact_calendar",
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.study.journal.journalsVocational',
            url: "/journals?_menu",
            icon:"chrome_reader_mode",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.study.practiceJournal.label',
            url: "/practiceJournals?_menu",
            icon:"assignment_ind"
          },
          {
            name: 'main.menu.study.material.higher',
            url: "/studyMaterial/higher?_menu",
            icon:"attach_file",
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.study.material.vocational',
            url: "/studyMaterial/vocational?_menu",
            icon:"attach_file",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.study.examTimes',
            url: '/examTimes?_menu',
            icon:"watch",
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.study.moduleProtocol.label',
            url: "/moduleProtocols?_menu",
            icon:"border_color",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.study.protocol.search',
            url: "/higherProtocols?_menu",
            icon:"create",
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.study.apel',
            url: "/apelApplication?_menu",
            icon:"transfer_within_a_station"
          }
        ]
      });

      sections.push({
        name: 'main.menu.studyPreparation.label',
        type: 'toggle',
        icon: "settings",
        pages: [
          {
            name: 'main.menu.studyPreparation.subjectTeacher',
            url: '/subjectStudyPeriods?_menu',
            icon:"nature_people",
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.studyPreparation.studyYearSchedule.legend',
            url: "/studyYearScheduleLegend?_menu",
            icon:"event_note"
          },
          {
            name: 'main.menu.studyPreparation.studyYearSchedule.edit',
            url: "/studyYearSchedule?_menu",
            icon:"event_available"
          },
          {
            name: 'main.menu.studyPreparation.lessonTime',
            id: 'timetableLessonTimeSearch',
            url: "/timetable/lessonTime/search?_menu",
            icon:"notifications_active"
          },
          {
            name: 'main.menu.studyPreparation.lessonplans.vocational',
            id: 'timetableLessonTimeSearch',
            url: "/lessonplans/vocational?_menu",
            icon:"zoom_out_map",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.studyPreparation.subjectStudyPeriods.plans',
            url: "/subjectStudyPeriodPlans?_menu",
            icon:"fitness_center",
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.studyPreparation.subjectStudyPeriods.label',
            url: "/subjectStudyPeriods/studentGroups?_menu",
            icon:"open_with",
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.studyPreparation.timetableManagement.label',
            url: "/timetable/timetableManagement?_menu",
            icon:"update"
          },
          {
            name: 'main.menu.studyPreparation.events.label',
            url: "/lessonplans/events?_menu",
            icon:"star_border"
          }
        ]
      });

      sections.push({
        name: 'main.menu.poll.label',
        type: 'toggle',
        icon: "question_answer",
        pages: [
          {
            name: 'main.menu.poll.data',
            url: '/poll?_menu',
            icon:"search"
          },
          {
            name: 'main.menu.poll.answers',
            url: '/poll/answers?_menu',
            icon:"reply"
          },
          {
            name: 'main.menu.poll.questions',
            url: '/poll/questions?_menu',
            icon:"chat"
          },
          {
            name: 'main.menu.poll.statistics',
            url: '/poll/statistics?_menu',
            icon:"blur_circular"
          }
        ]
      });

      sections.push({
        name: 'main.menu.reports.label',
        type: 'toggle',
        icon: "search",
        pages: [
          {
            name: 'main.menu.reports.students',
            url: "/reports/students/students?_menu",
            icon:"person"
          },
          {
            name: 'main.menu.reports.studentcount',
            url: '/reports/students/count?_menu',
            icon: 'person'
          },
          {
            name: 'main.menu.reports.studentmovement',
            url: '/reports/students/movement?_menu',
            icon: 'transfer_within_a_station'
          },
          {
            name: 'main.menu.reports.custom',
            url: '/reports/students/data?_menu',
            icon: 'perm_data_setting'
          },
          {
            name: authenticatedUser.higher && authenticatedUser.vocational ?
                'main.menu.reports.educationalSuccessVocational'
              : 'main.menu.reports.educationalSuccess',
            url: '/reports/students/educationalSuccess?_menu',
            icon:'blur_circular',
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.reports.studentstatistics',
            url: '/reports/students/statistics?_menu',
            icon:'blur_circular'
          },
          {
            name: 'main.menu.reports.gueststudentstatistics',
            url: '/reports/guest/students/statistics?_menu',
            icon: 'blur_circular'
          },
          {
            name: 'main.menu.reports.foreignstudentstatistics',
            url: '/reports/foreign/students/statistics?_menu',
            icon: 'blur_circular'
          },
          {
            name: 'main.menu.reports.studentstatisticsbyperiod',
            url: "/reports/students/statistics/byperiod?_menu",
            icon:"equalizer"
          },
          {
            name: 'main.menu.reports.curriculumscompletion',
            url: "/reports/curriculums/completion?_menu",
            icon:"perm_contact_calendar"
          },
          {
            nameHigher: 'main.menu.reports.teachersLoadVocationalHigher',
            nameVocational: 'main.menu.reports.teachersLoadVocational',
            url: "/reports/teachers/load/vocational?_menu",
            icon:"grain",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.reports.teachersLoadHigher',
            url: "/reports/teachers/load/higher?_menu",
            icon:"gradient",
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.reports.teachersLoadDetailVocational',
            url: "/reports/teachers/detailload/vocational?_menu",
            icon:"grain",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.reports.teachersLoadDetailHigher',
            url: "/reports/teachers/detailload/higher?_menu",
            icon:"gradient",
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.reports.vota',
            url: "/reports/vota?_menu",
            icon:"transfer_within_a_station"
          },
          {
            name: 'main.menu.reports.individualCurriculumStatistics',
            url: "/reports/individualcurriculum/statistics?_menu",
            icon:"face",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.reports.studentGroupTeacher',
            url: "/reports/studentgroupteacher?_menu",
            icon:"accessibility_new",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.scholarshipStatistics.label',
            url: '/reports/scholarships?_menu',
            icon: 'euro_symbol'
          },
          {
            name: 'main.menu.reports.lunchSupport',
            url: "/reports/lunchSupport?_menu",
            icon:"local_dining",
            studyLevel: {
              vocational: true
            }
          }
        ]
      });

      sections.push({
        name: 'main.menu.graduation.label',
        type: 'toggle',
        icon: "school",
        pages: [
          {
            name: 'main.menu.graduation.committees',
            url: "/committees/KOMISJON_K?_menu",
            icon:"record_voice_over"
          },
          {
            name: 'main.menu.graduation.finalThesis',
            url: "/finalThesis?_menu",
            icon:"receipt"
          },
          {
            name: 'main.menu.graduation.forms',
            url: "/forms?_menu",
            icon:"format_bold"
          },
          {
            name: 'main.menu.graduation.documentsPrintHigher',
            url: "/documents/diplomas?_menu",
            icon:"print",
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.graduation.documentsPrintVocational',
            url: "/documents/diplomas/vocational?_menu",
            icon:"print",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.graduation.documents',
            url: "/documents?_menu",
            icon:"print"
          },
          {
            name: 'main.menu.graduation.finalProtocolsHigher',
            url: "/finalHigherProtocols?_menu",
            icon:"create",
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.graduation.finalProtocolsVocational',
            url: "/finalVocationalProtocols?_menu",
            icon:"border_color",
            studyLevel: {
              vocational: true
            }
          }
        ]
      });

      sections.push({
        name: 'main.menu.dataexchange.label',
        type: 'toggle',
        icon: "swap_vert",
        pages: [
          {
            name: 'main.menu.dataexchange.teacherinfoExportHigher',
            id: 'teacherinfoExport',
            url: "/ehis/teacher/export/higher?_menu",
            icon:"account_box",
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.dataexchange.teacherinfoExportVocational',
            id: 'teacherinfoExport',
            url: "/ehis/teacher/export/vocational?_menu",
            icon:"account_circle",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.dataexchange.studentinfoExport',
            url: "/ehis/student/export?_menu",
            icon:"person"
          },
          {
            name: 'main.menu.dataexchange.kutseregister',
            url: "/occupationcertificate/import?_menu",
            icon:"android"
          },
          {
            name: 'main.menu.dataexchange.rtip',
            url: "/rtip/sync?_menu",
            icon:"weekend"
          },
          {
            name: 'main.menu.dataexchange.ehisLogs',
            url: "/ehis/logs?_menu",
            icon:"view_list"
          },
          {
            name: 'main.menu.dataexchange.ekisLogs',
            url: "/ekis/logs?_menu",
            icon:"view_stream"
          },
          {
            name: 'main.menu.dataexchange.kutseregisterLogs',
            url: "/kutseregister/logs?_menu",
            icon:"view_day"
          },
          {
            name: 'main.menu.dataexchange.rtipLogs',
            url: "/rtip/logs?_menu",
            icon:"view_array"
          },
          {
            name: 'main.menu.dataexchange.saisLogs',
            url: "/sais/logs?_menu",
            icon:"view_module"
          },
          {
            name: 'main.menu.dataexchange.rrLogs',
            url: "/rr/logs?_menu",
            icon:"view_week"
          }
        ]
      });

      sections.push({
        name: 'main.menu.reception.label',
        type: 'toggle',
        icon: "group_add",
        pages: [
          {
            name: 'main.menu.reception.saisAdmission.label',
            id: 'receptionSaisAdmissionSearch',
            url: "/reception/saisAdmission/search?_menu",
            icon:"brightness_high"
          },
          {
            name: 'main.menu.reception.saisApplication.label',
            id: 'receptionSaisApplicationSearch',
            url: "/reception/saisApplication/search?_menu",
            icon:"spellcheck"
          },
          {
            name: 'main.menu.reception.saisAdmission.importSais',
            id: 'receptionSaisAdmissionImport',
            url: "/reception/saisAdmission/import?_menu",
            icon:"brightness_low"
          },
          {
            name: 'main.menu.reception.saisApplication.importSais',
            id: 'receptionSaisApplicationImport',
            url: "/reception/saisApplication/import?_menu",
            icon:"brightness_auto"
          },
        ]
      });

      sections.push({
        name: 'main.menu.settings.label',
        type: 'toggle',
        icon: "build",
        pages: [
          {
            name: 'main.menu.settings.studyYears',
            url: "/school/studyYears",
            icon:"format_align_center"
          },
          {
            name: 'main.menu.settings.studyLevels',
            url: "/school/studyLevels",
            icon:"layers"
          },
          {
            name: 'main.menu.settings.capacityTypes',
            url: "/school/capacityTypes",
            icon: "bubble_chart"
          },
          {
            name: 'main.menu.settings.gradingSchema',
            url: "/school/gradingSchema",
            icon: "forward_5"
          },
          {
            name: 'main.menu.settings.generalmessages',
            url: "/generalmessages?_menu",
            icon:"notifications_none"
          },
          {
            name: 'main.menu.scholarships.grants',
            url: "/scholarships/grants?_menu",
            icon:"euro_symbol",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.scholarships.scholarships',
            url: "/scholarships/scholarships?_menu",
            icon:"euro_symbol",
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.settings.scholarshipCommittees',
            url: "/committees/KOMISJON_T?_menu",
            icon:"record_voice_over"
          },
          {
            name: 'main.menu.settings.messageTemplates',
            url: "/messageTemplate?_menu",
            icon:"blur_linear"
          },
          {
            name: 'main.menu.settings.documentcoordinators',
            url: "/directives/coordinators?_menu",
            icon:"recent_actors"
          },
          {
            name: 'main.menu.settings.finaldocsigners',
            url: "/school/finaldocsigners?_menu",
            icon:"recent_actors"
          },
          {
            nameHigher: 'main.menu.settings.teacheroccupationsHigher',
            nameVocational: 'main.menu.settings.teacheroccupationsVocational',
            url: "/school/teacheroccupations?_menu",
            icon:"business_center"
          },
          {
            name: 'main.menu.settings.buildings',
            url: "/rooms/search?_menu",
            icon:"business"
          },
          {
            name: 'main.menu.settings.departments',
            url: "/school/departments?_menu",
            icon:"device_hub"
          },
          {
            name: 'main.menu.settings.users',
            id: 'users',
            url: "/persons?_menu",
            icon:"wc"
          },
          {
            name: 'main.menu.settings.userContract',
            url: "/userContract?_menu",
            icon:"sort"
          },
          {
            name: 'main.menu.settings.apelSchools',
            url: "/apelSchools?_menu",
            icon:"location_city"
          },
          {
            name: 'main.menu.settings.apelCommittees',
            url: "/committees/KOMISJON_V?_menu",
            icon:"record_voice_over"
          },
          {
            name: 'main.menu.settings.supportCommittees',
            url: '/committees/KOMISJON_A?_menu',
            icon: "record_voice_over",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.settings.studentGroupYearTransfer',
            url: "/studentGroupYearTransfer?_menu",
            icon:"sync"
          }
        ]
      });
    }

    function getTeacherSections(authenticatedUser) {
      sections.push({
        name: 'main.menu.curriculums.label',
        url: "/curriculum?_menu",
        type: 'link',
        icon: "format_list_bulleted"
      });

      sections.push({
        name: 'main.menu.subjects.label',
        url: "/subject?_menu",
        type: 'link',
        icon: "subject",
        studyLevel: {
          higher: true
        }
      });

      sections.push({
        name: 'main.menu.timetableAndEvents.label',
        type: 'toggle',
        icon: "access_time",
        pages: [
          {
            name: 'main.menu.timetableAndEvents.timetable',
            url: "/timetable/personalGeneralTimetable?_menu",
            icon: "access_time"
          },
          {
            name: 'main.menu.timetableAndEvents.events',
            url: "/lessonplans/events?_menu",
            icon: "star_border"
          }
        ]
      });

      sections.push({
        name: 'message.messages',
        type: 'link',
        url: "/messages/received",
        icon: "email"
      });

      sections.push({
        name: 'main.menu.myData.label',
        url: "/teachers/myData",
        type: 'link',
        icon: 'person_outline'
      });

      sections.push({
        name: 'main.menu.academicCalendar.label',
        type: 'link',
        url: "/academicCalendar",
        icon: "date_range"
      });

      sections.push({
        name: 'main.menu.studyPreparation.studyYearSchedule.edit',
        url: "/studyYearSchedule?_menu",
        type: 'link',
        icon:"event_available"
      });

      sections.push({
        name: 'main.menu.study.label',
        type: 'toggle',
        icon: "work",
        pages: [
          {
            name: 'main.menu.study.examTimes',
            url: "/examTimes?_menu",
            icon: "watch",
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.study.absences',
            url: "/absences?_menu",
            icon: "announcement",
            studyLevel: {
              vocational: true
            },
            absenceAllowed: true
          },
          {
            name: 'main.menu.study.groupAbsences',
            url: "/groupAbsences?_menu",
            icon: "announcement",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.study.remarks',
            url: "/remarks?_menu",
            icon: "warning",
            studyLevel: {
              vocational: true
            },
            studentGroupTeacher: true
          },
          {
            name: 'main.menu.study.journal.journalsVocational',
            url: "/journals?_menu",
            icon: "chrome_reader_mode",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.study.subjectTeacher',
            url: "/subjectStudyPeriods?_menu",
            icon: "nature_people",
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.subjectProgram.label',
            url: "/subjectProgram?_menu",
            icon: "description",
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.study.material.higher',
            url: "/studyMaterial/higher?_menu",
            icon: "attach_file",
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.study.material.vocational',
            url: "/studyMaterial/vocational?_menu",
            icon: "attach_file",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.study.loads',
            url: "/lessonplans/vocational/byteacher?_menu",
            icon:"zoom_out_map"
          },
          {
            name: 'main.menu.study.students',
            url: "/students?_menu",
            icon: 'person'
          },
          {
            name: 'main.menu.study.moduleProtocol.label',
            url: "/moduleProtocols?_menu",
            icon: "border_color",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.study.protocol.search',
            url: "/higherProtocols?_menu",
            icon: "create",
            studyLevel: {
              higher: true
            }
          }
        ]
      });

      sections.push({
        name: 'main.menu.poll.label',
        type: 'toggle',
        icon: "question_answer",
        pages: [
          {
            name: 'main.menu.poll.answers',
            url: '/poll/answers?_menu',
            icon:"reply"
          },
          {
            name: authenticatedUser.higher && authenticatedUser.vocational ? 'main.menu.poll.both' :
            (authenticatedUser.higher ? 'main.menu.poll.subjects' : 'main.menu.poll.journals'),
            url: '/poll/answers/subjects?_menu',
            icon:"assignment_return"
          }
        ]
      });

      sections.push({
        name: 'main.menu.scholarships.grantApplicationsRanking',
        url: "/scholarships/applications/ranking/grants?_menu",
        type: 'link',
        icon:"euro_symbol",
        studyLevel: {
          vocational: true
        }
      });

      sections.push({
        type: 'link',
        name: 'main.menu.documents.applications',
        url: "/applications?_menu",
        icon: "font_download",
      });

      sections.push({
        name: 'main.menu.scholarships.scholarshipApplicationsRanking',
        url: "/scholarships/applications/ranking/scholarships?_menu",
        type: 'link',
        icon:"euro_symbol",
        studyLevel: {
          higher: true
        }
      });
      sections.push({
        name: 'main.menu.scholarships.drGrantApplicationsRanking',
        url: "/scholarships/applications/ranking/drGrants?_menu",
        type: 'link',
        icon:"functions",
        studyLevel: {
          doctoral: true
        }
      });

      sections.push({
        name: 'main.menu.practiceAndGraduation.label',
        type: 'toggle',
        icon: "school",
        pages: [
          {
            name: 'main.menu.practiceAndGraduation.practiceJournals',
            url: '/practiceJournals?_menu',
            icon: "assignment_ind"
          },
          {
            name: 'main.menu.practiceAndGraduation.committees',
            url: '/committees/KOMISJON_K?_menu',
            icon: "record_voice_over"
          },
          {
            name: 'main.menu.practiceAndGraduation.finalThesis',
            url: '/finalThesis?_menu',
            icon: "receipt"
          },
          {
            name: 'main.menu.graduation.finalProtocolsHigher',
            url: "/finalHigherProtocols?_menu",
            icon: "create",
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.graduation.finalProtocolsVocational',
            url: "/finalVocationalProtocols?_menu",
            icon: "border_color",
            studyLevel: {
              vocational: true
            }
          }
        ]
      });

      sections.push({
        name: 'main.menu.apel.label',
        type: 'link',
        url: '/apelApplication?_menu',
        icon: 'transfer_within_a_station'
      });

      sections.push({
        name: 'main.menu.individualCurriculumStatistics.label',
        type: 'link',
        url: '/reports/individualcurriculum/statistics?_menu',
        icon: 'face',
        studyLevel: {
          vocational: true
        },
        studentGroupTeacher: true
      });

      sections.push({
        name: 'main.menu.studentGroupTeacher.label',
        type: 'link',
        url: '/reports/studentgroupteacher?_menu',
        icon: 'accessibility_new',
        studyLevel: {
          vocational: true
        },
        studentGroupTeacher: true
      });
    }

    function getMainAdminSections() {
      sections.push({
        name: 'main.menu.curriculum.label',
        type: 'toggle',
        icon: "format_list_bulleted",
        pages: [
          {
            name: 'main.menu.curriculum.stateCurriculums',
            url: "/stateCurriculum?_menu",
            icon: "playlist_add_check"
          },
        ]
      });

      sections.push({
        name: 'main.menu.mainData.label',
        type: 'toggle',
        icon: "build",
        pages: [
          {
            name: 'main.menu.mainData.schools',
            url: "/school?_menu",
            icon: "account_balance"
          },
          {
            name: 'main.menu.mainData.classifiers',
            url: "/classifier?_menu",
            icon: "format_list_numbered"
          },
          {
            name: 'main.menu.mainData.saisClassifiers',
            url: "/saisClassifier?_menu",
            icon: "brightness_medium"
          },
          {
            name: 'main.menu.mainData.occupationStandards',
            url: "/occupationstandard?_menu",
            icon: "android"
          },
          {
            name: 'main.menu.dataexchange.kutseregisterLogs',
            url: "/kutseregister/logs?_menu",
            icon: "view_module"
          },
          {
            name: 'main.menu.mainData.users',
            url: "/persons?_menu",
            icon: "wc"
          },
        ]
      });

      sections.push({
        name: 'main.menu.mainData.generalMessages',
        type: 'link',
        url: "/generalmessages?_menu",
        icon: "notifications_none"
      });

      sections.push({
        name: 'main.menu.timetableLink.label',
        type: 'link',
        url: '/timetables?_menu',
        icon: "access_time"
      });

      sections.push({
        name: 'message.messages',
        type: 'link',
        url: "/messages/received",
        icon: "email"
      });
    }

    function getLeadingTeacherSections(authenticatedUser) {
      sections.push({
        name: 'main.menu.academicCalendar.label',
        type: 'link',
        url: "/academicCalendar",
        icon: "date_range"
      });

      sections.push({
        name: 'main.menu.viewTimetable.label',
        type: 'link',
        url: "/timetable/generalTimetable/group",
        icon: "access_time"
      });

      sections.push({
        name: 'message.messages',
        type: 'link',
        url: "/messages/received",
        icon: "email"
      });

      sections.push({
        name: 'main.menu.curriculum.label',
        type: 'toggle',
        icon: "format_list_bulleted",
        pages: [
          {
            name: "main.menu.basemodule.label",
            url: "/basemodule?_menu",
            icon: "format_list_bulleted",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.curriculum.schoolCurriculums',
            url: "/curriculum?_menu",
            icon: "format_list_bulleted"
          },
          {
            name: 'main.menu.curriculum.stateCurriculums',
            url: "/stateCurriculum?_menu",
            icon: "playlist_add_check",
            studyLevel: {
              vocational: true
            }
          }
        ]
      });

      sections.push({
        name: 'main.menu.student.label',
        type: 'toggle',
        icon: 'person',
        pages: [
          {
            name: 'main.menu.student.studentInfo',
            url: '/students?_menu',
            icon: 'person'
          },
          {
            name: 'main.menu.student.studentGroups',
            url: '/studentgroups?_menu',
            icon: "group"
          },
          {
            name: 'main.menu.student.absences',
            url: '/absences?_menu',
            icon: "announcement",
            studyLevel: {
              vocational: true
            },
            absenceAllowed: true
          },
          {
            name: 'main.menu.study.groupAbsences',
            url: "/groupAbsences?_menu",
            icon: "announcement",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.student.resultCard',
            url: "/studentResultCards?_menu",
            icon: "announcement",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.student.studentCard',
            url: "/studentCards?_menu",
            icon: "announcement"
          },
          {
            name: 'main.menu.student.boardingSchool',
            url: "/boardingSchools?_menu",
            icon: "home"
          },
          {
            name: 'main.menu.student.rrChanges',
            url: "/rr/changelogs?_menu",
            icon:"view_week"
          }
        ]
      });

      sections.push({
        name: 'main.menu.documents.label',
        type: 'toggle',
        icon: "library_books",
        pages: [
          {
            name: 'main.menu.documents.applications',
            url: "/applications?_menu",
            icon:"font_download"
          },
          {
            name: 'main.menu.documents.directives',
            url: "/directives?_menu",
            icon:"description"
          },
          {
            name: 'main.menu.documents.certificates',
            url: '/certificate?_menu',
            icon:"folder_shared"
          },
          {
            name: 'main.menu.scholarships.grantApplicationsRanking',
            url: "/scholarships/applications/ranking/grants?_menu",
            icon:"euro_symbol",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.scholarships.scholarshipApplicationsRanking',
            url: "/scholarships/applications/ranking/scholarships?_menu",
            icon:"euro_symbol",
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.scholarships.drGrantApplicationsRanking',
            url: "/scholarships/applications/ranking/drGrants?_menu",
            icon:"functions",
            studyLevel: {
              doctoral: true
            }
          }
        ]
      });

      sections.push({
        name: 'main.menu.practice.label',
        type: 'toggle',
        icon: "local_parking",
        pages: [
          {
            name: 'main.menu.practice.practiceEnterprises',
            url: "/practice/enterprise?_menu",
            icon:"search"
          },
          {
            name: 'main.menu.practice.practiceApplications',
            url: '/practice/applications?_menu',
            icon:"font_download"
          },
          {
            name: 'main.menu.practice.practiceEvaluations',
            url: '/practice/evaluation?_menu',
            icon:"rate_review"
          },
          {
            name: 'main.menu.practice.practiceContracts',
            url: "/contracts?_menu",
            icon:"assignment"
          },
          {
            name: 'main.menu.practice.statistics',
            url: "/practice/student/statistics?_menu",
            icon:"blur_circular"
          },
          {
            name: 'main.menu.practice.studentgroupContracts',
            url: "/practice/studentgroup/contracts?_menu",
            icon:"assignment"
          }
        ]
      });

      sections.push({
        name: 'main.menu.study.label',
        type: 'toggle',
        icon: "work",
        pages: [
          {
            name: 'main.menu.study.journal.journalsVocational',
            url: "/journals?_menu",
            icon:"chrome_reader_mode",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.study.practiceJournal.label',
            url: "/practiceJournals?_menu",
            icon:"assignment_ind"
          },
          {
            name: 'main.menu.study.material.higher',
            url: "/studyMaterial/higher?_menu",
            icon:"attach_file",
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.study.material.vocational',
            url: "/studyMaterial/vocational?_menu",
            icon:"attach_file",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.study.examTimes',
            url: '/examTimes?_menu',
            icon:"watch",
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.study.moduleProtocol.label',
            url: "/moduleProtocols?_menu",
            icon:"border_color",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.study.protocol.search',
            url: "/higherProtocols?_menu",
            icon:"create",
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.study.apel',
            url: "/apelApplication?_menu",
            icon:"transfer_within_a_station"
          }
        ]
      });

      sections.push({
        name: 'main.menu.studyPreparation.label',
        type: 'toggle',
        icon: "settings",
        pages: [
          {
            name: 'main.menu.studyPreparation.studyYearSchedule.edit',
            url: "/studyYearSchedule?_menu",
            icon:"event_available"
          },
          {
            name: 'main.menu.studyPreparation.lessonplans.vocational',
            id: 'timetableLessonTimeSearch',
            url: "/lessonplans/vocational?_menu",
            icon:"zoom_out_map",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.studyPreparation.events.label',
            url: "/lessonplans/events?_menu",
            icon:"star_border"
          }
        ]
      });

      sections.push({
        name: 'main.menu.poll.label',
        type: 'toggle',
        icon: "question_answer",
        pages: [
          {
            name: 'main.menu.poll.data',
            url: '/poll?_menu',
            icon:"search"
          },
          {
            name: 'main.menu.poll.questions',
            url: '/poll/questions?_menu',
            icon:"chat"
          },
          {
            name: 'main.menu.poll.statistics',
            url: '/poll/statistics?_menu',
            icon:"blur_circular"
          }
        ]
      });

      sections.push({
        name: 'main.menu.reports.label',
        type: 'toggle',
        icon: "search",
        pages: [
          {
            name: 'main.menu.reports.students',
            url: '/reports/students/students?_menu',
            icon: 'person'
          },
          {
            name: 'main.menu.reports.studentcount',
            url: '/reports/students/count?_menu',
            icon: 'person'
          },
          {
            name: 'main.menu.reports.studentmovement',
            url: '/reports/students/movement?_menu',
            icon: 'transfer_within_a_station'
          },
          {
            name: 'main.menu.reports.custom',
            url: '/reports/students/data?_menu',
            icon: 'perm_data_setting'
          },
          {
            name: authenticatedUser.higher && authenticatedUser.vocational ?
                'main.menu.reports.educationalSuccessVocational'
              : 'main.menu.reports.educationalSuccess',
            url: '/reports/students/educationalSuccess?_menu',
            icon:'blur_circular',
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.reports.studentstatistics',
            url: '/reports/students/statistics?_menu',
            icon: 'blur_circular'
          },
          {
            name: 'main.menu.reports.gueststudentstatistics',
            url: '/reports/guest/students/statistics?_menu',
            icon: 'blur_on'
          },
          {
            name: 'main.menu.reports.studentstatisticsbyperiod',
            url: '/reports/students/statistics/byperiod?_menu',
            icon: 'equalizer'
          },
          {
            name: 'main.menu.reports.curriculumscompletion',
            url: '/reports/curriculums/completion?_menu',
            icon: 'perm_contact_calendar'
          },
          {
            name: 'main.menu.reports.vota',
            url: '/reports/vota?_menu',
            icon: 'transfer_within_a_station'
          },
          {
            name: 'main.menu.reports.individualCurriculumStatistics',
            url: '/reports/individualcurriculum/statistics?_menu',
            icon: 'face',
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.reports.studentGroupTeacher',
            url: '/reports/studentgroupteacher?_menu',
            icon: 'accessibility_new',
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.scholarshipStatistics.label',
            url: '/reports/scholarships?_menu',
            icon: 'euro_symbol'
          },
          {
            name: 'main.menu.reports.lunchSupport',
            url: "/reports/lunchSupport?_menu",
            icon:"local_dining",
            studyLevel: {
              vocational: true
            }
          }
        ]
      });

      sections.push({
        name: 'main.menu.graduation.label',
        type: 'toggle',
        icon: "school",
        pages: [
          {
            name: 'main.menu.graduation.committees',
            url: "/committees/KOMISJON_K?_menu",
            icon:"record_voice_over"
          },
          {
            name: 'main.menu.graduation.finalThesis',
            url: "/finalThesis?_menu",
            icon:"receipt"
          },
          {
            name: 'main.menu.graduation.finalProtocolsHigher',
            url: "/finalHigherProtocols?_menu",
            icon:"create",
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.graduation.finalProtocolsVocational',
            url: "/finalVocationalProtocols?_menu",
            icon:"border_color",
            studyLevel: {
              vocational: true
            }
          }
        ]
      });

      sections.push({
        name: 'main.menu.dataexchange.label',
        type: 'toggle',
        icon: "swap_vert",
        pages: [
          {
            name: 'main.menu.dataexchange.ehisLogs',
            url: "/ehis/logs?_menu",
            icon:"view_list"
          },
          {
            name: 'main.menu.dataexchange.ekisLogs',
            url: "/ekis/logs?_menu",
            icon:"view_stream"
          },
          {
            name: 'main.menu.dataexchange.kutseregisterLogs',
            url: "/kutseregister/logs?_menu",
            icon:"view_day"
          },
          {
            name: 'main.menu.dataexchange.rrLogs',
            url: "/rr/logs?_menu",
            icon:"view_week"
          }
        ]
      });

      sections.push({
        name: 'main.menu.settings.label',
        type: 'toggle',
        icon: "build",
        pages: [
          {
            name: 'main.menu.settings.studyYears',
            url: "/school/studyYears",
            icon:"format_align_center"
          },
          {
            name: 'main.menu.settings.generalmessages',
            url: "/generalmessages?_menu",
            icon:"notifications_none"
          },
          {
            name: 'main.menu.settings.scholarshipCommittees',
            url: "/committees/KOMISJON_T?_menu",
            icon:"record_voice_over"
          },
          {
            name: 'main.menu.settings.apelSchools',
            url: "/apelSchools?_menu",
            icon:"location_city"
          },
          {
            name: 'main.menu.settings.apelCommittees',
            url: "/committees/KOMISJON_V?_menu",
            icon:"record_voice_over"
          },
          {
            name: 'main.menu.settings.supportCommittees',
            url: '/committees/KOMISJON_A?_menu',
            icon: "record_voice_over",
            studyLevel: {
              vocational: true
            }
          }
        ]
      });

    }

    function getStudentSections(authenticatedUser) {
      sections.push({
        name: 'main.menu.curriculums.label',
        url: "/curriculum?_menu",
        type: 'link',
        icon: "format_list_bulleted"
      });

      sections.push({
        name: 'main.menu.subjects.label',
        url: "/subject?_menu",
        type: 'link',
        studyLevel: {
          higher: true
        },
        icon: "subject"
      });

      sections.push({
        name: 'main.menu.timetableLink.label',
        type: 'link',
        url: '/timetable/personalGeneralTimetable?_menu',
        icon: "access_time"
      });

      sections.push({
        name: 'message.messages',
        type: 'link',
        url: "/messages/received",
        icon: "email"
      });

      sections.push({
        name: 'main.menu.myData.label',
        url: "/students/myData",
        type: 'link',
        icon:"person_outline"
      });

      sections.push({
        name: 'main.menu.academicCalendar.label',
        type: 'link',
        url: "/academicCalendar",
        icon: "date_range"
      });

      sections.push({
        name: 'main.menu.studyPreparation.studyYearSchedule.edit',
        url: "/studyYearSchedule?_menu",
        type: 'link',
        icon:"event_available"
      });

      sections.push({
        name: 'main.menu.myStudyInformation.label',
        type: 'toggle',
        icon:"work",
        pages: [
          {
            name: 'main.menu.myStudyInformation.journal',
            url: '/students/journals',
            icon:"chrome_reader_mode",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.myStudyInformation.practiceApplications',
            url: '/practice/application/myData?_menu',
            icon:"font_download"
          },
          {
            name: 'main.menu.myStudyInformation.practiceJournals',
            url: '/practiceJournals?_menu',
            icon:"assignment_ind"
          },
          {
            name: 'main.menu.myStudyInformation.results',
            url: '/students/myResults',
            icon:"timeline"
          },
          {
            name: 'main.menu.myStudyInformation.declaration',
            url: '/declaration/current/view?_menu',
            icon:"perm_contact_calendar",
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.myStudyInformation.midtermResult',
            url: '/midtermResult',
            icon: "chrome_reader_mode",
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.myStudyInformation.examRegistration',
            url: '/examRegistration?_menu',
            icon:"border_color",
            studyLevel: {
              higher: true
            }
          },
          {
            name: 'main.menu.myStudyInformation.finalThesisInput',
            url: '/finalThesis?_menu',
            icon:"receipt"
          },
          {
            name: 'main.menu.myStudyInformation.otherStudents',
            url: '/students?_menu',
            icon:"person"
          }
        ]
      });

      sections.push({
        name: 'main.menu.poll.label',
        type: 'link',
        icon: "question_answer",
        url: '/poll/answers?_menu'
      });

      sections = sections.concat(getScholarshipSectionForStudent(authenticatedUser));

      sections.push({
        name: 'main.menu.documents.label',
        type: 'toggle',
        icon:"library_books",
        pages: [
          {
            name: 'main.menu.documents.applications',
            url: "/applications/student?_menu",
            icon:"font_download"
          },
          {
            name: 'main.menu.documents.certificates',
            url: '/certificate?_menu',
            icon:"folder_shared"
          },
          {
            name: 'main.menu.documents.practiceContracts',
            url: "/contracts?_menu",
            icon:"assignment"
          },
          {
            name: 'main.menu.documents.apel',
            url: "/apelApplication?_menu",
            icon:"transfer_within_a_station"
          }
        ]
      });
    }

    function getExternalExpertSections() {
      sections.push({
        name: 'main.menu.curriculum.label', // todo curricula-vs-curriculums
        type: 'toggle',
        icon: "format_list_bulleted",
        pages: [
          {
            name: 'main.menu.curriculum.schoolCurriculums',
            url: "/curriculum?_menu",
            icon: "format_list_bulleted"
          },
          {
            name: 'main.menu.curriculum.stateCurriculums',
            url: "/stateCurriculum?_menu",
            icon: "playlist_add_check"
          },
        ]
      });
      sections.push({
        name: 'main.menu.teachers.labelVocational',
        type: 'link',
        url: "/teachers?_menu",
        icon: "supervisor_account"
      });

      sections.push({
        name: 'main.menu.timetableLink.label',
        type: 'link',
        url: '/timetables?_menu',
        icon: "access_time"
      });

      sections.push({
        name: 'message.messages',
        type: 'link',
        url: "/messages/received",
        icon: "email"
      });
    }

    function getParentSections() {
      sections.push({
        name: 'main.menu.curriculums.label',
        url: "/curriculum?_menu",
        type: 'link',
        icon: "format_list_bulleted"
      });

      sections.push({
        name: 'main.menu.subjects.label',
        url: "/subject?_menu",
        type: 'link',
        studyLevel: {
          higher: true
        },
        icon:"subject"
      });

      sections.push({
        name: 'main.menu.timetableLink.label',
        type: 'link',
        url: '/timetable/personalGeneralTimetable?_menu',
        icon: "access_time"
      });

      sections.push({
        name: 'message.messages',
        type: 'link',
        url: "/messages/received",
        icon: "email"
      });

      sections.push({
        name: 'main.menu.studentRepresentative.label',
        type: 'link',
        url: '/students/myData',
        icon:"person_outline"
      });

      sections.push({
        name: 'main.menu.academicCalendar.label',
        type: 'link',
        url: "/academicCalendar",
        icon: "date_range"
      });

      sections.push({
        name: 'main.menu.studyPreparation.studyYearSchedule.edit',
        url: "/studyYearSchedule?_menu",
        type: 'link',
        icon:"event_available"
      });

      sections.push({
        name: 'main.menu.studentStudyInformation.label',
        type: 'toggle',
        icon:"work",
        pages: [
          {
            name: 'main.menu.studentStudyInformation.journal',
            url: '/students/journals',
            icon:"chrome_reader_mode",
            studyLevel: {
              vocational: true
            }
          },
          {
            name: 'main.menu.studentStudyInformation.practiceJournal',
            url: "/practiceJournals?_menu",
            icon:"assignment_ind"
          },
          {
            name: 'main.menu.studentStudyInformation.results',
            url: '/students/myResults',
            icon:"timeline"
          }
        ]
      });

      sections.push({
        name: 'main.menu.poll.label',
        type: 'link',
        icon: "question_answer",
        url: '/poll/answers?_menu'
      });

      sections.push({
        name: 'main.menu.documents.label',
        type: 'toggle',
        icon:"library_books",
        pages: [
          {
            name: 'main.menu.documents.applications',
            url: "/applications/student?_menu",
            icon:"font_download"
          },
          {
            name: 'main.menu.documents.certificates',
            url: '/certificate?_menu',
            icon:"folder_shared"
          }
        ]
      });
    }

    function getPublicUserSections() {
      sections.push({
        name: 'main.menu.academicCalendar.label',
        type: 'link',
        url: "/academicCalendars",
        icon: "date_range"
      });

      sections.push({
        name: 'main.menu.timetableLink.label',
        type: 'link',
        url: "/timetables",
        icon: "access_time"
      });

      sections.push({
        name: 'main.menu.curriculum.schoolCurriculums',
        type: 'link',
        url: "/curriculums?_menu",
        icon: "list"
      });
      sections.push({
        name: 'main.menu.curriculum.stateCurriculums',
        type: 'link',
        url: "/stateCurriculum/public?_menu",
        icon: "playlist_add_check",
      });

      sections.push({
        name: 'main.menu.subjects.label',
        url: "/subject/public?_menu",
        type: 'link',
        icon: "subject"
      });
    }

    var self;
    var menu = [];

    function getScholarshipSectionForStudent(authenticatedUser) {
      var result = [];
      if (authenticatedUser.higher) {
        result.push({
          name: 'main.menu.scholarships.scholarships',
          type: 'link',
          url: "/scholarships/myData/scholarships?_menu",
          icon:"euro_symbol"
        });
        result.push({
          name: 'main.menu.scholarships.drGrants',
          type: 'link',
          url: "/scholarships/myData/drGrants?_menu",
          icon:"functions",
          studyLevel: {
            doctoral: true
          }
        });
      } else {
        result.push({
          name: 'main.menu.scholarships.grants',
          type: 'link',
          icon:"euro_symbol",
          url: "/scholarships/myData/grants?_menu"
        });
      }
      return result;
    }


    function onLocationChange() {

		var dataLayer = window.dataLayer || [];
dataLayer.push({
        'event': 'Pageview',
        'pagePath': $location.path()
});
      var path = $location.path();

      if (path === '/') {
        self.selectSection(null);
        self.selectPage(null, null);
        return;
      }
      if (path === '/messages/received') {
          var page = {name: 'message.messages', type: 'link', url: path};
          self.selectSection(page);
          self.selectPage(page, page);
          return;
      }
      if (path === '/studentrepresentatives/applications/new') {
          var page = {name: 'student.representative.title', type: 'link', url: path};
          self.selectSection(page);
          self.selectPage(page, page);
          return;
      }
      var matchPage = function(section, page) {
        if (page.url.replace(/(\?_menu)$/, '') === path) {
          //console.log("matched path ", path, " to page url ", page.url);
          self.selectSection(section);
          self.selectPage(section, page);
          return true;
        }
        return false;
      };

      //sections.forEach(function(section) {
      var match = false;
      for(var i = 0; i < menu.length && !match; i++) {
        var section = menu[i];
        if (section.children) {
          // matches nested section toggles, such as API or Customization
          for (var childIndex = 0; childIndex < section.children.length && !match; childIndex++) {
            var childSection = section.children[childIndex];
            if(childSection.pages) {
              for (var childSectionPageIndex = 0; childSectionPageIndex < childSection.pages.length  && !match; childSectionPageIndex++) {
                match = matchPage(childSection, childSection.pages[childSectionPageIndex]);
              }
            }
          }
        }
        else if (section.pages) {
          // matches top-level section toggles, such as Demos
          for (var sectionPageIndex = 0; sectionPageIndex < section.pages.length && !match; sectionPageIndex++) {
            match = matchPage(section, section.pages[sectionPageIndex]);
          }
        }
        else if (section.type === 'link') {
          // matches top-level links, such as "Getting Started"
          match = matchPage(section, section);
        }
      }


    }
    $rootScope.$on('$locationChangeSuccess', onLocationChange);

    self = {
      sections: {},

      setMenu: function (authenticatedUser) {
        buildMenu(authenticatedUser);
      },
      selectSection: function(section) {
        self.openedSection = section;
      },
      toggleSelectSection: function(section) {
        self.openedSection = (self.openedSection === section ? null : section);
      },
      isSectionSelected: function(section) {
        return self.openedSection === section;
      },

      selectPage: function(section, page) {
        self.currentSection = section;
        self.currentPage = page;
      },
      isPageSelected: function(page) {
        return self.currentPage === page;
      }
    };
    return self;

    function _canAccess(section, roles, authenticatedUser) {
      var hasAccess = false;
      var uri = section.url.replace(/(\?_menu)$/, '');
      var routes = $route.routes;
      var rights = null;
      for (var key in routes) {
        if (routes.hasOwnProperty(key) && routes[key].regexp) {
          if (routes[key].regexp.test(uri)) {
            rights = routes[key];
            break;
          }
        }
      }
      if (rights && rights.hasOwnProperty('data')) {
        var guestStudentForbidden = rights.data.guestStudentForbidden;
        var externalStudentForbidden = rights.data.externalStudentForbidden;
        var studentCurriculumRequired = rights.data.studentCurriculumRequired;
        rights = rights.data.authorizedRoles;
        // forbidding guest student
        if (guestStudentForbidden && angular.isDefined(authenticatedUser) && authenticatedUser.type === 'OPPUR_K') {
          return false;
        }
        // forbidding external student
        if (externalStudentForbidden && angular.isDefined(authenticatedUser) && authenticatedUser.type === 'OPPUR_E') {
          return false;
        }
        // forbidding external student without curriculum
        if (studentCurriculumRequired && angular.isDefined(authenticatedUser) && authenticatedUser.type === 'OPPUR_E' && authenticatedUser.curriculumVersion === null) {
          return false;
        }
        if(angular.isFunction(rights)) {
          hasAccess = rights(authenticatedUser, roles, ArrayUtils);
        } else if (rights.lastIndexOf('') !== -1) {
          hasAccess = true;
        } else {
          for (var i = 0; i < rights.length; i++) {
            if (roles.indexOf(rights[i]) !== -1) {
              hasAccess = true;
              break;
            }
          }
        }
      } else {
        // all can see if no right
        hasAccess = true;
      }
      return hasAccess;
    }

    function studyLevelMatch(section, authenticatedUser) {
        if(angular.isDefined(section.studyLevel) && angular.isDefined(authenticatedUser.school) && authenticatedUser.school !== null) {
            return authenticatedUser.higher && section.studyLevel.higher ||
              authenticatedUser.vocational && section.studyLevel.vocational ||
              authenticatedUser.doctoral && section.studyLevel.doctoral;
        }
        return true;
    }

    function isSchoolAbsenceDisabled(section, authenticatedUser) {
      return section.absenceAllowed && authenticatedUser.school.notAbsence;
    }

    function isStudentGroupTeacher(section, authenticatedUser) {
      return section.studentGroupTeacher && authenticatedUser.teacherGroupIds.length > 0;
    }

    function addToggleItem(pages, section, roles, authenticatedUser) {
      if (section.pages.length > 0) {
        for (var j = 0; j < section.pages.length; j++) {
          addSubmenuItem(pages, section.pages[j], roles, authenticatedUser);
        }
      }
      if (pages.length > 0) {
        menu.push({
          name: getSectionName(section, authenticatedUser),
          type: 'toggle',
          pages: pages,
          icon: section.icon
        });
      }
    }

    function addLinkItem(section, roles, authenticatedUser) {
      if (!_canAccess(section, roles, authenticatedUser) || !section.url) {
        return;
      }
      if (!studyLevelMatch(section, authenticatedUser)) {
        return;
      }

      if (angular.isDefined(section.studentGroupTeacher) && !isStudentGroupTeacher(section, authenticatedUser)) {
        return;
      }

      if (angular.isDefined(section.absenceAllowed) && isSchoolAbsenceDisabled(section, authenticatedUser)) {
        return;
      }

      menu.push({
        name: getSectionName(section, authenticatedUser),
        type: section.type,
        url: section.url,
        icon: section.icon
      });
    }

    function addSubmenuItem(pages, section, roles, authenticatedUser) {
      if (!_canAccess(section, roles, authenticatedUser) || !section.url) {
        return;
      }
      if (!studyLevelMatch(section, authenticatedUser)) {
        return;
      }

      if (angular.isDefined(section.studentGroupTeacher) && !isStudentGroupTeacher(section, authenticatedUser)) {
        return;
      }

      if (angular.isDefined(section.absenceAllowed) && isSchoolAbsenceDisabled(section, authenticatedUser)) {
        return;
      }

      pages.push({
        name: getSectionName(section, authenticatedUser),
        id: section.id,
        url: section.url,
        icon: section.icon
      });
    }

    function getSectionName(section, authenticatedUser) {
      if (authenticatedUser.higher && section.nameHigher) {
        return section.nameHigher;
      } else if (authenticatedUser.vocational && section.nameVocational) {
        return section.nameVocational;
      }
      return section.name;
    }

    function getUserSections(authenticatedUser) {
      sections = [];

      switch(authenticatedUser.roleCode) {
        case "ROLL_A":
          getAdminSections(authenticatedUser);
          break;
        case "ROLL_P":
          getMainAdminSections(authenticatedUser);
          break;
        case "ROLL_J":
          getLeadingTeacherSections(authenticatedUser);
          break;
        case "ROLL_O":
          getTeacherSections(authenticatedUser);
          break;
        case "ROLL_T":
          getStudentSections(authenticatedUser);
          break;
        case "ROLL_L":
          getParentSections();
          break;
        case "ROLL_V":
          getExternalExpertSections();
          break;
        default:
          getPublicUserSections();
          break;
      }
    }

    // todo: generalize
    function buildMenu(authenticatedUser) {
      var roles = authenticatedUser.authorizedRoles || [''];
      menu = [];

      getUserSections(authenticatedUser);

      for (var i = 0; i < sections.length; i++) {
        var pages = [];

        if (sections[i].type === 'toggle') {
          addToggleItem(pages, sections[i], roles, authenticatedUser);
        } else if (sections[i].type === 'link') {
          addLinkItem(sections[i], roles, authenticatedUser);
        }
      }
      self.sections = menu;
      onLocationChange();
    }
  });
