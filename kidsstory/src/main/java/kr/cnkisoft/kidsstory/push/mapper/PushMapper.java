package kr.cnkisoft.kidsstory.push.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import kr.cnkisoft.kidsstory.push.domain.PreSchoolPushIdDto;

import java.util.List;

/**
 * Created by PureMaN on 2017-08-27.
 */
@Mapper
public interface PushMapper {

	@Insert("INSERT INTO PRESCH_PUSH_ID (CONTACT, DEVICE_ID, OS_TYPE, CREATED_DT, CREATED_BY) VALUES (#{contact}, #{deviceId}, #{osType}, now(), 0)")
	public int insertPushId(PreSchoolPushIdDto preSchoolPushId);

	@Insert("DELETE FROM PRESCH_PUSH_ID WHERE CONTACT = #{contact}")
	public int deletetPushId(@Param("contact")String contact);

	@Select("SELECT * FROM PRESCH_PUSH_ID WHERE CONTACT = (SELECT CONTACT FROM USER_INFO WHERE USER_ID = #{userId})")
	public List<PreSchoolPushIdDto> selectListPushInfoByUserId(@Param("userId")Integer userId);

	@Select("SELECT a.* FROM PRESCH_PUSH_ID a, USER_INFO b, PRESCH_LINE_DTL c, MAP_PARENT_CHILD d " +
			"WHERE a.CONTACT = b.CONTACT AND b.USER_ID = d.PARENT_ID AND d.CHILD_ID = c.STDU_ID AND c.LINE_DTL_ID = #{lineDtlId}")
	public List<PreSchoolPushIdDto> selectListPushInfoByLineDetailId(@Param("lineDtlId")Integer lineDtlId);

}
