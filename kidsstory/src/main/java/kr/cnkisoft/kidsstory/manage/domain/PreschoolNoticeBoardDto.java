package kr.cnkisoft.kidsstory.manage.domain;

import kr.cnkisoft.kidsstory.common.domain.CommonDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PreschoolNoticeBoardDto extends CommonDto{
	Integer noticeId;
	String schCd;
	String noticeType;
	String noticeTitle;
	String noticeContent;
}
