package kr.msp.board;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.msp.dto.Board;
import kr.msp.dto.BoardRequest;
import kr.msp.dto.User;

@Mapper
public interface BoardMapper {

	List<Map<String, Object>> getBoardList(@Param("offset") int offset, @Param("size") int size, @Param("searchType") int searchType, @Param("searchKeyword") String searchKeyword);

	int getBoardTotalCount(@Param("searchType") int searchType, @Param("searchKeyword") String searchKeyword);

	int writeBoard(Board board);

	int editBoard(Board board);


}
