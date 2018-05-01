package kr.cnkisoft.kidsstory.admin.service;

import kr.cnkisoft.kidsstory.admin.domain.AdminUserVo;

public interface AdminUserService {
	public AdminUserVo getAdminUserByLoginId(String loginId);
}
