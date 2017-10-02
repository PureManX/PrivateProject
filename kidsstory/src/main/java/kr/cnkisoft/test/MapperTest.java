package kr.cnkisoft.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import kr.cnkisoft.KidsstoryApplication;
import kr.cnkisoft.framework.utils.DateUtils;
import kr.cnkisoft.preschool.manage.domain.DailyMenuVo;
import kr.cnkisoft.preschool.manage.mapper.MenuMapper;
import kr.cnkisoft.preschool.user.domain.StudentVo;
import kr.cnkisoft.preschool.user.mapper.UserMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = KidsstoryApplication.class)
@ActiveProfiles("local")
public class MapperTest {
	
	@Autowired
	UserMapper userMapper;
	
	@Autowired
	MenuMapper menuMapper;
	
	@Test
	public void test() {
		List<StudentVo> studentList = userMapper.selectListStudentByTeacherContact("01125709371");
		
		System.out.println(studentList);
	}
	
	@Test
	public void test2() {
		List<DailyMenuVo> studentList = menuMapper.selectListDailyMenu("TEST1", DateUtils.currentDateOfYear());
		
		System.out.println(studentList);
	}
}
