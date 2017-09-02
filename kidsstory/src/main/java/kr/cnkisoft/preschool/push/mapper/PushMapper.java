package kr.cnkisoft.preschool.push.mapper;

import kr.cnkisoft.preschool.push.domain.PreSchoolPushIdDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by PureMaN on 2017-08-27.
 */
@Mapper
public interface PushMapper {

	@Insert("INSERT INTO PRESCH_PUSH_ID (CONTACT, DEVICE_ID, OS_TYPE, CREATED_DT) VALUES (#{contact}, #{deviceId}, #{osType}, now())")
	public int insertPushId(PreSchoolPushIdDto preSchoolPushId);

	@Select("SELECT * FROM PRESCH_PUSH_ID WHERE CONTACT = (SELECT CONTACT FROM USER_INFO WHERE USER_ID = #{userId})")
	public List<PreSchoolPushIdDto> selectListPushInfoByUserId(@Param("userId")Integer userId);

	@Select("SELECT a.* FROM PRESCH_PUSH_ID a, USER_INFO b, PRESCH_LINE_DTL c, MAP_PARENT_CHILD d " +
			"WHERE a.CONTACT = b.CONTACT AND b.USER_ID = d.PARENT_ID AND d.CHILD_ID = c.STDU_ID AND c.LINE_DTL_ID = #{lineDtlId}")
	public List<PreSchoolPushIdDto> selectListPushInfoByLineDetailId(@Param("lineDtlId")Integer lineDtlId);

}
