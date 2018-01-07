package kr.cnkisoft.preschool.common.file.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyGalleryListVo {
	private String date;
	private List<FileInfoDto> images;
	
	public DailyGalleryListVo(String date) {
		super();
		this.date = date;
	}
}
