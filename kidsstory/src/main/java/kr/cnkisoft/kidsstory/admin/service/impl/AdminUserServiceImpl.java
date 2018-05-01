package kr.cnkisoft.kidsstory.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.cnkisoft.kidsstory.admin.domain.AdminUserVo;
import kr.cnkisoft.kidsstory.admin.mapper.AdminUserMapper;
import kr.cnkisoft.kidsstory.admin.service.AdminUserService;

@Service
public class AdminUserServiceImpl implements AdminUserService {

	@Autowired
	AdminUserMapper adminUserMapper;

	@Override
	public AdminUserVo getAdminUserByLoginId(String loginId) {
		return adminUserMapper.selectAdminUserByLoginId(loginId);
	}

}
