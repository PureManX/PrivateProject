package kr.cnkisoft.preschool.common.file.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import kr.cnkisoft.preschool.common.file.domain.FileInfoDto;

@Mapper
public interface FileMapper {

	@Insert("INSERT INTO FILE_INFO (FILE_NM, FILE_TYPE, CREATED_BY, CREATED_DT) VALUES(#{fileNm}, #{fileType}, #{createdBy}, now())")
	public int insertFileInfo(FileInfoDto param);
	
	@Select("SELECT a.*, CONCAT('/', LOWER(a.FILE_TYPE), '/', a.FILE_NM) AS IMG_SRC  FROM FILE_INFO a WHERE FILE_TYPE = 'IMAGE' AND CREATED_BY = #{createdBy}")
	public List<FileInfoDto> selectListImageFileByTeacherId(@Param("createdBy")String createdBy);
}
