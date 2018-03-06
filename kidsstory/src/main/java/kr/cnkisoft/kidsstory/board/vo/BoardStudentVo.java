package kr.cnkisoft.kidsstory.board.vo;

import org.springframework.util.StringUtils;

import lombok.Data;

@Data
public class BoardStudentVo {
	private Integer userId;
	private String userNm;
	private String imgSrc;
	private String clsNm;
	private String unbReason;
	private String boardDiv;
	
	protected BoardStudentVo(Integer userId) {
		super();
		this.userId = userId;
	}
	
	public String getProfileImageUri() {
		if (StringUtils.isEmpty(imgSrc)) {
			return "/images/common/profile_default.png";
		} else {
			return "/file/data" + imgSrc;
		}
	}
	
}
