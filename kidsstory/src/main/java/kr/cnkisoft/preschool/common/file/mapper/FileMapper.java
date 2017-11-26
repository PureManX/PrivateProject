package kr.cnkisoft.preschool.common.file.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import kr.cnkisoft.preschool.common.file.domain.FileInfoDto;

@Mapper
public interface FileMapper {

	public int insertFileInfo(FileInfoDto param);
	
	@Select("SELECT a.*, CONCAT('/file/data/', LOWER(a.FILE_TYPE), '/', a.FILE_NM) AS IMG_SRC  FROM FILE_INFO a WHERE FILE_TYPE = 'IMAGE' AND CREATED_BY = #{createdBy}")
	public List<FileInfoDto> selectListImageFileByCreatedBy(@Param("createdBy")Integer createdBy);
	
	@Select("SELECT a.*, CONCAT('/file/data/', LOWER(a.FILE_TYPE), '/', a.FILE_NM) AS IMG_SRC  FROM FILE_INFO a WHERE FILE_TYPE = 'IMAGE' AND CLS_ID = #{clsId}")
	public List<FileInfoDto> selectListImageFileByClassId(@Param("clsId")Integer clsId);
}
