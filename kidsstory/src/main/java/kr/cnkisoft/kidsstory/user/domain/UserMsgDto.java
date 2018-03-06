package kr.cnkisoft.kidsstory.user.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserMsgDto {
	private Integer msgId;
	private Integer srcId;
	private Integer dstId;
	private String msgContent;
	private String recvFlg;
	private String createdBy;
	private Timestamp createdDt;
}
