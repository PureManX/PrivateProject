package kr.cnkisoft.preschool.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import kr.cnkisoft.preschool.user.domain.LoginUserVo;
import kr.cnkisoft.preschool.user.domain.ParentVo;
import kr.cnkisoft.preschool.user.domain.PreschoolClassDto;
import kr.cnkisoft.preschool.user.domain.PreschoolVo;
import kr.cnkisoft.preschool.user.domain.ReqMediDto;
import kr.cnkisoft.preschool.user.domain.ReqMediVo;
import kr.cnkisoft.preschool.user.domain.UserDto;
import kr.cnkisoft.preschool.user.domain.UserMsgDto;

@Mapper
public interface UserMapper {
	
	@Select("SELECT a.*, b.FILE_TYPE, b.FILE_NM FROM USER_INFO a left join FILE_INFO b on a.PROF_IMG_ID = b.FILE_ID WHERE a.CONTACT = #{contact}")
	public LoginUserVo selectUserbyId(@Param("contact")String contact);

	@Select("SELECT a.*, b.FILE_TYPE, b.FILE_NM FROM USER_INFO a left join FILE_INFO b on a.PROF_IMG_ID = b.FILE_ID WHERE a.CONTACT = #{contact}")
	public UserDto selectUserbyContact(@Param("contact")String contact);

	@Select("SELECT sch.* FROM PRESCH_CLASS cls JOIN PRESCH_INFO sch ON cls.SCH_CD = sch.SCH_CD WHERE cls.CLS_ID = #{clsId}")
	public PreschoolVo selectPreschoolbyClsId(@Param("clsId")Integer clsId);

	@Select("SELECT * FROM PRESCH_CLASS WHERE SCH_CD = #{schCd}")
	public List<PreschoolClassDto> selectListPreschoolClassbyPreshcoolCode(@Param("schCd")String schCd);


	@Select("SELECT * FROM VIEW_STU_BASE WHERE PAR_USER_ID = #{parentId}")
	public List<UserDto> selectListStudentByParentId(@Param("parentId")Integer parentId);
	
	@Select("SELECT a.* FROM ("
			+ "SELECT * FROM USER_MSG WHERE SRC_ID = #{srcId} AND DST_ID = #{dstId} "
			+ "UNION "
			+ "SELECT * FROM USER_MSG WHERE SRC_ID = #{dstId} AND DST_ID = #{srcId}"
			+ ") a ORDER BY a.CREATED_DT")
	public List<UserMsgDto> selectListMsg(@Param("srcId")int srcId, @Param("dstId")int dstId);
	
	@Insert("INSERT INTO USER_MSG (SRC_ID, DST_ID, MSG_CONTENT, RECV_FLG, CREATED_BY, CREATED_DT) VALUES(#{srcId},#{dstId},#{msgContent},'N',#{srcId},now())")
	public int insertMsg(UserMsgDto param);
	
	@Select("SELECT * FROM VIEW_STU_BASE WHERE CLS_ID = (SELECT CLS_ID FROM PRESCH_CLASS WHERE HR_TEACHER_ID = "
			+ "(SELECT USER_ID FROM USER_INFO WHERE CONTACT = #{contact}))")
	public List<UserDto> selectListStudentByTeacherContact(@Param("contact")String contact);
	
	@Select("SELECT * FROM MEDI_REQ_INFO a, VIEW_STU_BASE b WHERE a.USER_ID = b.USER_ID"
			+ " AND a.USER_ID in (SELECT USER_ID FROM USER_INFO WHERE USER_TYPE='STU' AND CLS_ID IN (SELECT CLS_ID FROM PRESCH_CLASS WHERE HR_TEACHER_ID = #{teacherId}))")
	public List<ReqMediVo> selectListReqMediByTeacherId(@Param("teacherId")int teacherId);
	
	@Insert("INSERT INTO MEDI_REQ_INFO (USER_ID, SYMPTOM, ADMIN_DT, ADMIN_TM, ADMIN_CAPA, ADMIN_TYPE, ADMIN_CNT, SYMP_DESC, KEEP_TYPE)"
			+ " VALUES (#{userId}, #{symptom}, #{adminDt}, #{adminTm}, #{adminCapa}, #{adminType}, #{adminCnt}, #{sympDesc}, #{keepType})")
	public int insertReqMediInfo(ReqMediDto param);


	@Insert("INSERT INTO USER_INFO (USER_NM, USER_TYPE, PAR_USER_ID, CONTACT, EMAIL, PROF_IMG_ID, STTUS_CD, SCH_CD, CLS_ID, CREATED_DT, CREATED_BY)" +
			" VALUES (#{userNm}, #{userType}, #{parUserId}, #{contact}, #{email}, #{profImgId}, #{sttusCd}, #{schCd}, #{clsId}, NOW(), #{createdBy})")
	public int insertUser(UserDto user);


	@Select("SELECT * FROM USER_INFO WHERE SCH_CD = #{schCd} AND USER_TYPE = 'PAR'")
	public List<UserDto> selectListParentInPreschool(@Param("schCd")String preschoolCode);

	@Select("SELECT a.*, b.FILE_TYPE, b.FILE_NM, concat('/', lcase(b.FILE_TYPE), '/', b.FILE_NM) AS `IMG_SRC` FROM USER_INFO a left join FILE_INFO b on a.PROF_IMG_ID = b.FILE_ID WHERE a.user_id = #{userId}")
	public UserDto selectUserbyUserId(@Param("userId")Integer userId);

	@Select("SELECT * FROM VIEW_STU_BASE WHERE user_id = #{userId}")
	public UserDto selectStudentbyUserId(@Param("userId")Integer userId);

	@Select("SELECT a.* FROM VIEW_USER_BASE a, MAP_PARENT_CHILD b WHERE a.USER_ID = b.PARENT_ID AND b.CHILD_ID = #{childId}")
	public List<ParentVo> selectListParentsByChildId(@Param("childId")Integer childId);

}
