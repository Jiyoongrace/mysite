package com.poscodx.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.BoardRepository;
import com.poscodx.mysite.vo.BoardVo;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardService {
    private static final int LIST_SIZE = 5; // 한 페이지에 보여줄 게시물 수
    private static final int PAGE_SIZE = 5; // 한 번에 보여줄 페이지 번호 수

    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public void addContents(BoardVo boardVo) {
        if(boardVo.getGroupNo() != null) {
            boardRepository.updateOrderNo(boardVo.getGroupNo(), boardVo.getOrderNo());
        }

        boardRepository.insert(boardVo);
    }

    public BoardVo getContents(Long no) {
        BoardVo boardVo = boardRepository.findByNo(no);

        if(boardVo != null) {
            boardRepository.updateHit(no);
        }

        return boardVo;
    }

    public BoardVo getContents(Long no, Long userNo) {
        BoardVo boardVo = boardRepository.findByNoAndUserNo(no, userNo);
        return boardVo;
    }

    public void modifyContents(BoardVo boardVo) {
        boardRepository.update(boardVo);
    }

    public void deleteContents(Long boardNo, Long userNo) {
        boardRepository.delete(boardNo, userNo);
    }

    public Map<String, Object> getContentsList(int currentPage, String keyword) {

        // 전체 게시물 수 계산
        int totalCount = boardRepository.getTotalCount(keyword);

        // 전체 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalCount / LIST_SIZE);

        // 현재 페이지 유효성 검사
        if (currentPage > totalPages) { // 현재 페이지 번호가 전체 페이지 수를 넘지 않도록 한다.
            currentPage = totalPages;
        }
        if (currentPage < 1) { // 최소 1페이지 이상이 되도록 한다.
            currentPage = 1;
        }

        // 시작 페이지와 끝 페이지 계산
        // 현재 페이지를 기준으로 페이징 목록의 시작 페이지와 끝 페이지를 계산한다.
        int startPage = ((currentPage - 1) / PAGE_SIZE) * PAGE_SIZE + 1;
        int endPage = startPage + PAGE_SIZE - 1;
        if (endPage > totalPages) {
            endPage = totalPages;
        }

        // 이전 페이지와 다음 페이지 계산
        int prevPage = (startPage > 1) ? startPage - 1 : 0; // 시작 페이지가 1보다 크면 이전 페이지가 있다.
        int nextPage = (endPage < totalPages) ? endPage + 1 : 0; // 끝 페이지가 전체 페이지 수보다 작으면 다음 페이지가 있다.

        // 현재 페이지의 게시물 목록을 키워드를 사용하여 가져온다.
        List<BoardVo> list = boardRepository.findAllByPageAndKeyword(keyword, currentPage, LIST_SIZE);

        // 결과 맵 준비
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("totalCount", totalCount);
        map.put("listSize", LIST_SIZE);
        map.put("currentPage", currentPage);
        map.put("beginPage", startPage);
        map.put("endPage", endPage);
        map.put("prevPage", prevPage);
        map.put("nextPage", nextPage);
        map.put("keyword", keyword);

        return map;
    }

}