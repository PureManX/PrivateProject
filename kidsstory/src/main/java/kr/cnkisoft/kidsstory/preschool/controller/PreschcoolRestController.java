package kr.cnkisoft.kidsstory.preschool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.cnkisoft.kidsstory.common.domain.CommonResultVo;
import kr.cnkisoft.kidsstory.preschool.domain.PreschoolClassDto;
import kr.cnkisoft.kidsstory.preschool.service.PreschoolService;

@RestController
public class PreschcoolRestController {
	
	@Autowired
	PreschoolService preschoolService;
	
	@GetMapping({"/rest/preschool/class/list", "/rest/admin/preschool/class/list"})
	public CommonResultVo getClassListByPreshcoolCode() {
		List<PreschoolClassDto> classList = preschoolService.getClassListByCurrentLoginPreshcoolCode();

		CommonResultVo result = new CommonResultVo();
		result.setData(classList);

		return result;
	}
	
	@PostMapping({"/rest/preschool/class", "/rest/admin/preschool/class"})
	public CommonResultVo createClassByPreshcoolCode(
			@RequestBody PreschoolClassDto preschoolClass
			) {
		preschoolService.createPreschoolClass(preschoolClass);

		CommonResultVo result = new CommonResultVo();
		result.setData("success");

		return result;
	}
	
	@PutMapping({"/rest/preschool/class", "/rest/admin/preschool/class"})
	public CommonResultVo modifyClassByPreshcoolCode(
			@RequestBody PreschoolClassDto preschoolClass
			) {
		preschoolService.modifyPreschoolClass(preschoolClass);

		CommonResultVo result = new CommonResultVo();
		result.setData("success");

		return result;
	}
}