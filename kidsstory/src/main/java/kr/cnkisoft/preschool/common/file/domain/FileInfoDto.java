package kr.cnkisoft.preschool.common.file.domain;

import kr.cnkisoft.preschool.common.domain.CommonCreateDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FileInfoDto extends CommonCreateDto{
	private Integer fileId;
	private String fileNm;
	private String fileType;
	private Integer clsId;
	private String imgSrc;
}
