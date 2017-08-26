package kr.cnkisoft.preschool.common.file.domain;

import kr.cnkisoft.preschool.common.domain.CommonDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FileInfoDto extends CommonDto{
	private String fileId;
	private String fileNm;
	private String fileType;
	private String imgSrc;
}
