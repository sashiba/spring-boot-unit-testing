package com.luv2code.springmvc.repository;

import com.luv2code.springmvc.models.MathGrade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MathGradesDao extends CrudRepository<MathGrade, Integer> {
    Iterable<MathGrade> findGradeByStudentId(int studentId);

    void deleteByStudentId(int studentId);
}
