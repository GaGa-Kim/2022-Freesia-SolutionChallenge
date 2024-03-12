package com.freesia.imyourfreesia.service.emoticon;

import com.freesia.imyourfreesia.dto.emoticon.EmoticonCountResponseDto;
import com.freesia.imyourfreesia.dto.emoticon.EmoticonRequestDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface EmoticonService {
    /**
     * 이모티콘을 저장한다.
     *
     * @param requestDto (이모티콘 저장 정보를 담은 DTO)
     * @return EmoticonCountResponseDto (이모티콘 별 개수를 담은 DTO)
     */
    EmoticonCountResponseDto saveEmotion(EmoticonRequestDto requestDto);

    /**
     * 이모티콘을 삭제한다.
     *
     * @param requestDto (이모티콘 삭제 정보를 담은 DTO)
     * @return EmoticonCountResponseDto (이모티콘 별 개수를 담은 DTO)
     */
    EmoticonCountResponseDto deleteEmotion(EmoticonRequestDto requestDto);

    /**
     * 챌린지 아이디와 회원에 따른 이모티콘 개수를 조회한다.
     *
     * @param challengeId (챌린지 아이디)
     * @param email       (회원 이메일)
     * @return EmoticonCountResponseDto (이모티콘 별 개수를 담은 DTO)
     */
    @Transactional(readOnly = true)
    EmoticonCountResponseDto findByChallengeIdAndUidAndEmoticonName(Long challengeId, String email);

    /**
     * 챌린지 아이디에 따른 이모티콘 개수를 조회한다.
     *
     * @param challengeId (챌린지 아이디)
     * @return EmoticonCountResponseDto (이모티콘 별 개수를 담은 DTO)
     */
    @Transactional(readOnly = true)
    EmoticonCountResponseDto count(Long challengeId);
}
