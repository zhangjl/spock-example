import groovy.transform.ToString
import spock.lang.Specification

/**
 * @author zhangjl
 */
class StudentServiceSpec extends Specification {
    def studentDao = Mock(StudentDao)
    def tester = new StudentService(studentDao: studentDao)

    def 'test StudentService.getStudentById'(){
        given:
        studentDao.getStudentInfo() >> students

        when:
        def response = tester.getStudentById(id)

        then:
        response.postCode == postCodeResult
        response.abbreviation == abbreviationResult

        where:
        id | students                                               || postCodeResult | abbreviationResult
        1  | [new StudentDTO(id: 1, name: '张三', province: '北京')] || '100000'       | '京'
        2  | [new StudentDTO(id: 2, name: '李四', province: '上海')] || '200000'       | '沪'
    }

    def 'validate student info: #expectedMessage'(){
        when:
        tester.validateStudent(student)

        then:
        def exception = thrown(expectedException)
        exception.code == expectedCode
        exception.msg == expectedMessage

        where:
        student || expectedException | expectedCode | expectedMessage
        null    || BusinessException | '10001' | 'student is null'
        new StudentVo() || BusinessException | '10002' | 'student name is null'
        new StudentVo(name: '张三') || BusinessException | '10003' | 'student age is null'
        new StudentVo(name: '张三', age: 10) || BusinessException | '10004' | 'student telephone is null'
        new StudentVo(name: '张三', age: 10, telephone: '100000') || BusinessException | '10005' | 'student sex is null'
    }
}

class StudentService {
    private StudentDao studentDao

    StudentVo getStudentById(int id) {
        List<StudentDTO> students = studentDao.getStudentInfo()
        StudentDTO studentDTO = students.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null)
        StudentVo studentVO = new StudentVo()
        if (!studentDTO) {
            return studentVO
        }

        studentVO.setId(studentDTO.getId())
        studentVO.setName(studentDTO.getName())
        studentVO.setSex(studentDTO.getSex())
        studentVO.setAge(studentDTO.getAge())

        // 邮编
        if ('上海' == studentDTO.getProvince()) {
            studentVO.setAbbreviation("沪")
            studentVO.setPostCode("200000")
        }

        if ('北京' == studentDTO.getProvince()) {
            studentVO.setAbbreviation("京")
            studentVO.setPostCode("100000")
        }
        return studentVO
    }

    void validateStudent(StudentVo student) throws BusinessException {
        if(Objects.isNull(student)){
            throw new BusinessException(code: '10001', msg: 'student is null')
        }

        if(!student.getName()){
            throw new BusinessException(code: "10002", msg: "student name is null");
        }

        if(!student.getAge()){
            throw new BusinessException(code: "10003", msg: "student age is null");
        }

        if(!student.getTelephone()){
            throw new BusinessException(code: "10004", msg: "student telephone is null");
        }

        if(!student.getSex()){
            throw new BusinessException(code: "10005", msg: "student sex is null");
        }
    }
}

@ToString
class StudentDTO {
    def id
    def name
    def sex
    def age
    def province
}

@ToString
class StudentVo {
    def id
    def name
    def sex
    def age
    def telephone
    def abbreviation
    def postCode
}

@ToString
class BusinessException extends RuntimeException {
    def code
    def msg
}

interface StudentDao {
    List<StudentDTO> getStudentInfo();
}

