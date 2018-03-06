package kr.cnkisoft.kidsstory.preschool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.cnkisoft.kidsstory.common.domain.CommonResultVo;
import kr.cnkisoft.kidsstory.preschool.domain.PreschoolClassDto;
import kr.cnkisoft.kidsstory.preschool.service.PreschoolService;

@RestController
public class PreshcoolRestController {
	
	@Autowired
	PreschoolService preschoolService;
	
	@GetMapping({"/rest/preschool/class/list", "/rest/admin/preschool/class/list"})
	public CommonResultVo getClassListByPreshcoolCode() {
		List<PreschoolClassDto> classList = preschoolService.getClassListByCurrentLoginPreshcoolCode();

		CommonResultVo result = new CommonResultVo();
		result.setData(classList);

		return result;
	}
}
