package kr.cnkisoft.kidsstory.admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.cnkisoft.kidsstory.admin.domain.AdminUserVo;

@Mapper
public interface AdminUserMapper {
	public AdminUserVo selectAdminUserByLoginId(@Param("loginId")String loginId);
	public int insertAdminUser(AdminUserVo adminUser);
	public int insertAdminUserRole(@Param("adminId")Integer adminId, @Param("adminRole")String adminRole);
}
