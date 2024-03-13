package com.freesia.imyourfreesia.service.cheering;

import com.freesia.imyourfreesia.domain.cheering.Cheering;
import com.freesia.imyourfreesia.dto.cheering.CheeringSaveRequestDto;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CheeringService {
    /**
     * 응원을 저장한다.
     *
     * @param requestDto (응원 저장 정보를 담은 DTO)
     * @return Cheering (응원)
     */
    @Transactional
    Cheering saveCheering(CheeringSaveRequestDto requestDto);

    /**
     * 응원을 삭제한다.
     *
     * @param cheeringId (응원 아이디)
     */
    void deleteCheering(Long cheeringId);

    /**
     * 이메일에 따른 응원 전체 개수를 조회한다.
     *
     * @param email (회원 이메일)
     * @return Long (응원 전체 개수)
     */
    Long countByRecipientEmail(String email);

    /**
     * 응원 랭킹 상위 10명의 회원를 조회한다.
     *
     * @return List<Map < String, Object>> (응원 랭킹 상위 10명의 이메일, 닉네임, 개수 정보를 담은 목록)
     */
    List<Map<String, Object>> cheeringRankList();

    /**
     * 이메일에 따른 일주일 응원 개수를 조회한다.
     *
     * @param email (회원 이메일)
     * @return Long (일주일 응원 개수)
     */
    Long countByCreatedDateBetweenAndRecipientEmail(String email);

    /**
     * 상대방 이메일과 내 이메일을 가지고 상대방 응원 여부를 조회한다.
     *
     * @param senderEmail    (내 이메일)
     * @param recipientEmail (상대방 이메일)
     * @return boolean (상대방 응원 여부)
     */
    boolean exitsBySenderEmailAndRecipientEmail(String senderEmail, String recipientEmail);

    /**
     * 이메일에 따른 응원한 회원 목록을 조회한다.
     *
     * @param email (회원 이메일)
     * @return List<Cheering> (응원한 회원 목록)
     */
    List<Cheering> findCheeringListByUser(String email);
}
