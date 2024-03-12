package com.freesia.imyourfreesia.service.challenge;

import com.freesia.imyourfreesia.domain.challenge.Challenge;
import com.freesia.imyourfreesia.dto.challenge.ChallengeListResponseDto;
import com.freesia.imyourfreesia.dto.challenge.ChallengeResponseDto;
import com.freesia.imyourfreesia.dto.challenge.ChallengeSaveRequestDto;
import com.freesia.imyourfreesia.dto.challenge.ChallengeUpdateRequestDto;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
public interface ChallengeService {
    /**
     * 챌린지를 저장한다.
     *
     * @param requestDto (챌린지 저장 정보를 담은 DTO)
     * @param files      (챌린지 파일들)
     * @return ChallengeResponseDto (챌린지 정보를 담은 DTO)
     */
    ChallengeResponseDto saveChallenge(ChallengeSaveRequestDto requestDto, List<MultipartFile> files) throws Exception;

    /**
     * 챌린지 전체 목록을 조회한다.
     *
     * @return List<ChallengeListResponseDto> (챌린지 정보를 담은 DTO 목록)
     */
    @Transactional(readOnly = true)
    List<ChallengeListResponseDto> findAllChallengeDesc();

    /**
     * 챌린지 아이디에 따른 챌린지를 조회한다.
     *
     * @param challengeId (챌린지 아이디)
     * @return ChallengeResponseDto (챌린지 정보를 담은 DTO)
     */
    @Transactional(readOnly = true)
    Challenge findChallengeById(Long challengeId);

    /**
     * 챌린지 아이디에 따른 챌린지를 상세 조회한다.
     *
     * @param challengeId (챌린지 아이디)
     * @return ChallengeResponseDto (챌린지 정보를 담은 DTO)
     */
    @Transactional(readOnly = true)
    ChallengeResponseDto findChallengeDetailsById(Long challengeId);

    /**
     * 챌린지를 수정한다.
     *
     * @param challengeId (챌린지 아이디)
     * @param requestDto  (챌린지 수정 정보를 담은 DTO)
     * @param files       (챌린지 파일들)
     * @return ChallengeResponseDto (챌린지 정보를 담은 DTO)
     */
    ChallengeResponseDto updateChallenge(Long challengeId, ChallengeUpdateRequestDto requestDto, List<MultipartFile> files) throws Exception;

    /**
     * 챌린지를 삭제한다.
     *
     * @param challengeId (챌린지 아이디)
     */
    void deleteChallenge(Long challengeId);

    /**
     * 회원이 작성한 챌린지 목록을 조회한다.
     *
     * @param email (회원 이메일)
     * @return List<ChallengeListResponseDto> (챌린지 정보를 담은 DTO 목록)
     */
    @Transactional(readOnly = true)
    List<ChallengeListResponseDto> findChallengeByUser(String email);
}