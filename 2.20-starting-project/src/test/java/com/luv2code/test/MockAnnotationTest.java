package com.luv2code.test;


import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.dao.ApplicationDao;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import com.luv2code.component.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
public class MockAnnotationTest {
    @Autowired
    ApplicationContext context;

    @Autowired
    CollegeStudent studentOne;

    @Autowired
    StudentGrades studentGrades;

    @MockitoBean
    private ApplicationDao applicationDao;

    @Autowired
    private ApplicationService applicationService;

    @BeforeEach
    public void beforeEach() {
        studentOne.setFirstname("Eric");
        studentOne.setLastname("Roby");
        studentOne.setEmailAddress("eric.roby@luv2code_school.com");
        studentOne.setStudentGrades(studentGrades);
    }

    @DisplayName("When and Verify")
    @Test
    void assertEqualsTestAndGrades() {
        when(applicationDao.addGradeResultsForSingleClass(studentGrades.getMathGradeResults())).thenReturn(100.00);

        assertEquals(100, applicationService.addGradeResultsForSingleClass(
                studentOne.getStudentGrades().getMathGradeResults()));

        verify(applicationDao).addGradeResultsForSingleClass(studentGrades.getMathGradeResults());
        verify(applicationDao, times(1)).addGradeResultsForSingleClass(studentGrades.getMathGradeResults());
    }

    @DisplayName("Find Gpa")
    @Test
    void assertEqualsTestFindGpa() {
        when(applicationDao.findGradePointAverage(studentGrades.getMathGradeResults())).thenReturn(88.31);

        assertEquals(88.31, applicationService.findGradePointAverage(studentOne
                .getStudentGrades().getMathGradeResults()));
    }

    @DisplayName("Not Null")
    @Test
    void testAssertNotNull() {
        when(applicationDao.checkNull(studentGrades.getMathGradeResults())).thenReturn(true);
        assertNotNull(applicationService.checkNull(studentOne.getStudentGrades()
                .getMathGradeResults()), "Object should not be null");
    }

    @DisplayName("Throw runtime error")
    @Test
    void throwRuntimeError() {
        CollegeStudent nullStudent = context.getBean("collegeStudent", CollegeStudent.class);

        when(applicationDao.checkNull(nullStudent)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> {
            applicationService.checkNull(nullStudent);
        });

        verify(applicationDao, times(1)).checkNull(nullStudent);
    }

    @DisplayName("Multiple Stubbing")
    @Test
    void stubbingConsecutiveCalls() {
        CollegeStudent nullStudent = context.getBean("collegeStudent", CollegeStudent.class);

        when(applicationDao.checkNull(nullStudent))
                .thenThrow(new RuntimeException())
                .thenReturn("Do not throw exception second time");


        assertThrows(RuntimeException.class, () -> {
            applicationService.checkNull(nullStudent);
        });

        assertEquals("Do not throw exception second time",
                applicationService.checkNull(nullStudent));

        assertEquals("Do not throw exception second time",
                applicationService.checkNull(nullStudent));

        verify(applicationDao, times(3)).checkNull(nullStudent);
    }
}
