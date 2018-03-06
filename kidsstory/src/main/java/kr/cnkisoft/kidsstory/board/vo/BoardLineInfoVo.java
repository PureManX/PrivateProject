package kr.cnkisoft.kidsstory.board.vo;

import kr.cnkisoft.framework.enums.BoardLineServiceType;
import kr.cnkisoft.kidsstory.user.domain.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Created by PureMaN on 2017-08-31.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardLineInfoVo extends BoardLineDto{
	private PreschoolBusDto bus;
	private BoardLineServiceDto service;
	private UserDto teacher;

	public BoardLineInfoVo(Integer lineId) {
		super(lineId);
	}
	
	public boolean isBoardLineStarted() {
		return service != null && service.getServiceStartDt() != null;
	}
	
	public boolean isBoardLineEnded() {
		return getBoardLineStatus() == BoardLineServiceType.COMPETE;
	}
	
	public BoardLineServiceType getBoardLineStatus() {
		if (isBoardLineStarted()) {
			return service.getServiceEndDt() == null ? BoardLineServiceType.START : BoardLineServiceType.COMPETE;
		} else {
			return BoardLineServiceType.READY;
		}
	}
}
