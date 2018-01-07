package kr.cnkisoft.preschool.common.file.domain;

import org.springframework.util.StringUtils;

import kr.cnkisoft.preschool.common.domain.CommonCreateDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class FileInfoDto extends CommonCreateDto{
	private Integer fileId;
	private String fileNm;
	private String fileType;
	private Integer clsId;
	private String imgSrc;
	
	public FileInfoDto(Integer fileId) {
		super();
		this.fileId = fileId;
	}
	
	public String getImgSrcThumbNail() {
		if (!StringUtils.isEmpty(imgSrc)) {
			int dotIndex = imgSrc.lastIndexOf(".");
			
			String name = imgSrc.substring(0, dotIndex);
			String ext = imgSrc.substring(dotIndex);
			
			return name + "_thumb" + ext;
		} else {
			return "";
		}
	}
}
