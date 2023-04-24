package com.project.context;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.StudentaddressApplication;
@SpringBootTest
public class StudentaddressMainTest {
	@Test
    public void testMain() {
        StudentaddressApplication.main(new String[] {});
    }

}
