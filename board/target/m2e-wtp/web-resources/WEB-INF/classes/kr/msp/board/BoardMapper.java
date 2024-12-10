package kr.msp.board;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.msp.dto.DeleteBoardListRequest;
import kr.msp.dto.DeleteBoardRequest;
import kr.msp.dto.EditBoardRequest;
import kr.msp.dto.WriteBoardRequest;

@Mapper
public interface BoardMapper {

	List<Map<String, Object>> getBoardList(@Param("offset") int offset, @Param("size") int size, @Param("searchType") int searchType, @Param("searchKeyword") String searchKeyword);

	int getBoardTotalCount(@Param("searchType") int searchType, @Param("searchKeyword") String searchKeyword);

	int writeBoard(WriteBoardRequest writeBoardRequest);

	int editBoard(EditBoardRequest editBoardRequest);

	int deleteBoard(DeleteBoardRequest deleteBoardRequest);

	int deleteBoardList(DeleteBoardListRequest deleteBoardListRequest);


}
