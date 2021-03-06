package kr.cnkisoft.kidsstory.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import kr.cnkisoft.kidsstory.user.domain.ParentVo;
import kr.cnkisoft.kidsstory.user.domain.ReqMediDto;
import kr.cnkisoft.kidsstory.user.domain.ReqMediVo;
import kr.cnkisoft.kidsstory.user.domain.StudentVo;
import kr.cnkisoft.kidsstory.user.domain.UserDto;
import kr.cnkisoft.kidsstory.user.domain.UserMsgDto;
import kr.cnkisoft.kidsstory.user.domain.UserVo;

@Mapper
public interface UserMapper {

	// SELECT SQL
//	@Select("SELECT a.*, b.FILE_TYPE, b.FILE_NM FROM USER_INFO a left join FILE_INFO b on a.PROF_IMG_ID = b.FILE_ID WHERE a.CONTACT = #{contact}")
	public UserVo selectUserbyContact(@Param("contact")String contact);


	// 학생 정보 조회
	public List<StudentVo> selectListStudentByParentId(@Param("parentId")Integer parentId);
	public List<StudentVo> selectListStudentByPreschoolCode(@Param("schCd")String schCd);
	public List<StudentVo> selectListStudentByTeacherContact(@Param("contact")String contact);
	public List<StudentVo> selectListStudentByBoardLineDetailId(@Param("lineDetailId")Integer lineDetailId);

	@Select("SELECT a.* FROM ("
			+ "SELECT * FROM USER_MSG WHERE SRC_ID = #{srcId} AND DST_ID = #{dstId} "
			+ "UNION "
			+ "SELECT * FROM USER_MSG WHERE SRC_ID = #{dstId} AND DST_ID = #{srcId}"
			+ ") a ORDER BY a.CREATED_DT")
	public List<UserMsgDto> selectListMsg(@Param("srcId")int srcId, @Param("dstId")int dstId);
	
	@Insert("INSERT INTO USER_MSG (SRC_ID, DST_ID, MSG_CONTENT, RECV_FLG, CREATED_BY, CREATED_DT) VALUES(#{srcId},#{dstId},#{msgContent},'N',#{srcId},now())")
	public int insertMsg(UserMsgDto param);
	
	
	@Select("SELECT * FROM MEDI_REQ_INFO a, VIEW_USER_BASE b WHERE a.USER_ID = b.USER_ID"
			+ " AND a.USER_ID in (SELECT USER_ID FROM USER_INFO WHERE USER_TYPE='STU' AND CLS_ID IN (SELECT CLS_ID FROM PRESCH_CLASS WHERE HR_TEACHER_ID = #{teacherId}))")
	public List<ReqMediVo> selectListReqMediByTeacherId(@Param("teacherId")int teacherId);
	
	@Insert("INSERT INTO MEDI_REQ_INFO (USER_ID, SYMPTOM, ADMIN_DT, ADMIN_TM, ADMIN_CAPA, ADMIN_TYPE, ADMIN_CNT, SYMP_DESC, KEEP_TYPE, CREATED_BY, CREATED_DT)"
			+ " VALUES (#{userId}, #{symptom}, #{adminDt}, #{adminTm}, #{adminCapa}, #{adminType}, #{adminCnt}, #{sympDesc}, #{keepType}, 0, now() )")
	public int insertReqMediInfo(ReqMediDto param);

	public int insertUser(UserDto user);

	@Select("SELECT * FROM USER_INFO WHERE SCH_CD = #{schCd} AND USER_TYPE = 'PAR'")
	public List<UserDto> selectListParentInPreschool(@Param("schCd")String preschoolCode);

	@Select("SELECT * FROM VIEW_USER_BASE WHERE user_id = #{userId}")
	public UserVo selectUserbyUserId(@Param("userId")Integer userId);

	@Select("SELECT * FROM VIEW_USER_BASE WHERE user_id = #{userId}")
	public UserDto selectStudentbyUserId(@Param("userId")Integer userId);

	@Select("SELECT a.* FROM VIEW_USER_BASE a, MAP_PARENT_CHILD b WHERE a.USER_ID = b.PARENT_ID AND b.CHILD_ID = #{childId}")
	public List<ParentVo> selectListParentsByChildId(@Param("childId")Integer childId);
	
	public int updateUserProfileImageId(UserDto user);
	
	public int insertMapParentChild(@Param("parentId")Integer parentId, @Param("childId")Integer childId, @Param("createdBy")Integer createdBy);
	public int updateStudent(UserDto user);
	public int deleteMapParentChildByChildId(@Param("childId")Integer childId);
	public int updateParent(UserDto user);
	
}
