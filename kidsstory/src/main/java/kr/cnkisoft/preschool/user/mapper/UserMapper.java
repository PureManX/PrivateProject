package kr.cnkisoft.preschool.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import kr.cnkisoft.preschool.user.domain.ParentVo;
import kr.cnkisoft.preschool.user.domain.PreschoolClassDto;
import kr.cnkisoft.preschool.user.domain.PreschoolVo;
import kr.cnkisoft.preschool.user.domain.ReqMediDto;
import kr.cnkisoft.preschool.user.domain.ReqMediVo;
import kr.cnkisoft.preschool.user.domain.StudentVo;
import kr.cnkisoft.preschool.user.domain.UserDto;
import kr.cnkisoft.preschool.user.domain.UserMsgDto;
import kr.cnkisoft.preschool.user.domain.UserVo;

@Mapper
public interface UserMapper {

//	@Select("SELECT a.*, b.FILE_TYPE, b.FILE_NM FROM USER_INFO a left join FILE_INFO b on a.PROF_IMG_ID = b.FILE_ID WHERE a.CONTACT = #{contact}")
	public UserVo selectUserbyContact(@Param("contact")String contact);

	@Select("SELECT sch.* FROM PRESCH_CLASS cls JOIN PRESCH_INFO sch ON cls.SCH_CD = sch.SCH_CD WHERE cls.CLS_ID = #{clsId}")
	public PreschoolVo selectPreschoolbyClsId(@Param("clsId")Integer clsId);

	@Select("SELECT * FROM PRESCH_CLASS WHERE SCH_CD = #{schCd}")
	public List<PreschoolClassDto> selectListPreschoolClassbyPreshcoolCode(@Param("schCd")String schCd);

//	@Select("SELECT * FROM VIEW_USER_BASE WHERE PAR_USER_ID = #{parentId}")
	public List<StudentVo> selectListStudentByParentId(@Param("parentId")Integer parentId);
	
	@Select("SELECT a.* FROM ("
			+ "SELECT * FROM USER_MSG WHERE SRC_ID = #{srcId} AND DST_ID = #{dstId} "
			+ "UNION "
			+ "SELECT * FROM USER_MSG WHERE SRC_ID = #{dstId} AND DST_ID = #{srcId}"
			+ ") a ORDER BY a.CREATED_DT")
	public List<UserMsgDto> selectListMsg(@Param("srcId")int srcId, @Param("dstId")int dstId);
	
	@Insert("INSERT INTO USER_MSG (SRC_ID, DST_ID, MSG_CONTENT, RECV_FLG, CREATED_BY, CREATED_DT) VALUES(#{srcId},#{dstId},#{msgContent},'N',#{srcId},now())")
	public int insertMsg(UserMsgDto param);
	
	// 학생 정보 조회
	public List<StudentVo> selectListStudentByTeacherContact(@Param("contact")String contact);
	public List<StudentVo> selectListStudentByBoardLineDetailId(@Param("lineDetailId")Integer lineDetailId);
	
	@Select("SELECT * FROM MEDI_REQ_INFO a, VIEW_USER_BASE b WHERE a.USER_ID = b.USER_ID"
			+ " AND a.USER_ID in (SELECT USER_ID FROM USER_INFO WHERE USER_TYPE='STU' AND CLS_ID IN (SELECT CLS_ID FROM PRESCH_CLASS WHERE HR_TEACHER_ID = #{teacherId}))")
	public List<ReqMediVo> selectListReqMediByTeacherId(@Param("teacherId")int teacherId);
	
	@Insert("INSERT INTO MEDI_REQ_INFO (USER_ID, SYMPTOM, ADMIN_DT, ADMIN_TM, ADMIN_CAPA, ADMIN_TYPE, ADMIN_CNT, SYMP_DESC, KEEP_TYPE, CREATED_BY, CREATED_DT)"
			+ " VALUES (#{userId}, #{symptom}, #{adminDt}, #{adminTm}, #{adminCapa}, #{adminType}, #{adminCnt}, #{sympDesc}, #{keepType}, 0, now() )")
	public int insertReqMediInfo(ReqMediDto param);


	@Insert("INSERT INTO USER_INFO (USER_NM, USER_TYPE, PAR_USER_ID, CONTACT, EMAIL, PROF_IMG_ID, STTUS_CD, SCH_CD, CLS_ID, CREATED_DT, CREATED_BY)" +
			" VALUES (#{userNm}, #{userType}, #{parUserId}, #{contact}, #{email}, #{profImgId}, #{sttusCd}, #{schCd}, #{clsId}, NOW(), #{createdBy})")
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
}
