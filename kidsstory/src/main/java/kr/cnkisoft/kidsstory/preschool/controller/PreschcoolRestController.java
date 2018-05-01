package kr.cnkisoft.kidsstory.preschool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.cnkisoft.kidsstory.common.domain.CommonResultVo;
import kr.cnkisoft.kidsstory.preschool.domain.PreschoolBusDto;
import kr.cnkisoft.kidsstory.preschool.domain.PreschoolClassDto;
import kr.cnkisoft.kidsstory.preschool.domain.PreschoolDto;
import kr.cnkisoft.kidsstory.preschool.service.PreschoolService;

@RestController
public class PreschcoolRestController {

	@Autowired
	PreschoolService preschoolService;

	@GetMapping({"/rest/admin/preschool/list"})
	public CommonResultVo getPreschiilList() {
		List<PreschoolDto> classList = preschoolService.getPreschoolList();

		CommonResultVo result = new CommonResultVo();
		result.setData(classList);

		return result;
	}

	@PostMapping({"/rest/admin/preschool"})
	public CommonResultVo createPreshcool(
			@RequestBody PreschoolDto preschool
			) {
		preschoolService.createPreschool(preschool);

		CommonResultVo result = new CommonResultVo();
		result.setData("success");

		return result;
	}

	@PutMapping({"/rest/admin/preschool"})
	public CommonResultVo modifyPreshcool(
			@RequestBody PreschoolDto preschool
			) {
		preschoolService.modifyPreschool(preschool);

		CommonResultVo result = new CommonResultVo();
		result.setData("success");

		return result;
	}

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

	@GetMapping({"/rest/preschool/bus/list", "/rest/admin/preschool/bus/list"})
	public CommonResultVo getBusListByPreshcoolCode() {
		List<PreschoolBusDto> classList = preschoolService.getBusListByCurrentLoginPreshcoolCode();

		CommonResultVo result = new CommonResultVo();
		result.setData(classList);

		return result;
	}
}
