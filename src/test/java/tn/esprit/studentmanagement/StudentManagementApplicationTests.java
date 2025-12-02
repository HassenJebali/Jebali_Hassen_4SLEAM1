package tn.esprit.studentmanagement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import tn.esprit.studentmanagement.services.StudentService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class StudentManagementApplicationTests {

    @MockBean
    private StudentService studentService;  // Mock du service pour ne pas toucher à la DB

    @Test
    void contextLoads() {
    }
}

