package com.freesia.imyourfreesia.service;

import com.freesia.imyourfreesia.domain.challenge.Challenge;
import com.freesia.imyourfreesia.domain.challenge.ChallengeRepository;
import com.freesia.imyourfreesia.domain.emoticon.Emoticon;
import com.freesia.imyourfreesia.domain.emoticon.EmoticonRepository;
import com.freesia.imyourfreesia.domain.user.User;
import com.freesia.imyourfreesia.domain.user.UserRepository;
import com.freesia.imyourfreesia.dto.emoticon.EmoticonRequestDto;
import com.freesia.imyourfreesia.dto.emoticon.EmoticonResponseDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class EmoticonService {

    private final UserRepository userRepository;
    private final ChallengeRepository challengeRepository;
    private final EmoticonRepository emoticonRepository;

    // 이모티콘 저장
    @Transactional
    public String save(EmoticonRequestDto emoticonRequestDto) {

        User user = userRepository.findByEmail(emoticonRequestDto.getEmail());
        Challenge challenge = challengeRepository.findById(emoticonRequestDto.getChallengeId()).orElseThrow(IllegalArgumentException::new);

        if (emoticonRepository.findByUserAndChallengeAndName(user, challenge, emoticonRequestDto.getEmoticonName()).isEmpty()) {
            Emoticon emoticons = emoticonRequestDto.toEntity();
            emoticons.setUser(user);
            emoticons.setChallenge(challenge);

            List<Emoticon> emoticonList = new ArrayList<>();
            emoticonList.add(emoticons);

            if (!emoticonList.isEmpty()) {
                for (Emoticon emoticon : emoticonList) {
                    challenge.addEmoticon(emoticonRepository.save(emoticon));
                }
            }

            return "이모티콘 등록 완료. 이모티콘 아이디 : " + emoticonRepository.save(emoticons).getId();

        } else {
            return "이모티콘 등록 실패 (이미 존재합니다.)";
        }
    }

    // 이모티콘 삭제
    @Transactional
    public String delete(EmoticonRequestDto emoticonRequestDto) {

        User user = userRepository.findByEmail(emoticonRequestDto.getEmail());
        Challenge challenge = challengeRepository.findById(emoticonRequestDto.getChallengeId()).orElseThrow(IllegalArgumentException::new);

        if (emoticonRepository.findByUserAndChallengeAndName(user, challenge, emoticonRequestDto.getEmoticonName()).isEmpty()) {
            return "삭제 실패 (존재하지 않습니다.)";

        } else {
            emoticonRepository.deleteByUserAndChallengeAndName(user, challenge, emoticonRequestDto.getEmoticonName());
            return "삭제 완료";
        }
    }

    // 글 아이디와 사용자에 따른 이모티콘 조회
    @Transactional
    public EmoticonResponseDto findByChallengeIdAndUidAndEmoticonName(Long challengeId, String email) {

        Challenge challenge = challengeRepository.findById(challengeId).orElseThrow(IllegalArgumentException::new);
        User user = userRepository.findByEmail(email);

        List<Emoticon> emoticon1List = emoticonRepository.findByChallengeAndUserAndName(challenge, user, "emoticon1");
        List<Emoticon> emoticon2List = emoticonRepository.findByChallengeAndUserAndName(challenge, user, "emoticon2");
        List<Emoticon> emoticon3List = emoticonRepository.findByChallengeAndUserAndName(challenge, user, "emoticon3");
        List<Emoticon> emoticon4List = emoticonRepository.findByChallengeAndUserAndName(challenge, user, "emoticon4");
        List<Emoticon> emoticon5List = emoticonRepository.findByChallengeAndUserAndName(challenge, user, "emoticon5");

        return new EmoticonResponseDto(emoticon1List.size(), emoticon2List.size(), emoticon3List.size(), emoticon4List.size(), emoticon5List.size());
    }

    // 글에 따른 이모티콘 갯수 조회
    @Transactional
    public EmoticonResponseDto count(Long challengeId) {

        EmoticonResponseDto emoticonResponseDto;

        Challenge challenge = challengeRepository.findById(challengeId).orElseThrow(IllegalArgumentException::new);

        // 이모티콘 이름 확정 시 변경 필요
        List<Emoticon> emoticon1List = emoticonRepository.findByChallengeAndName(challenge, "emoticon1");
        List<Emoticon> emoticon2List = emoticonRepository.findByChallengeAndName(challenge, "emoticon2");
        List<Emoticon> emoticon3List = emoticonRepository.findByChallengeAndName(challenge, "emoticon3");
        List<Emoticon> emoticon4List = emoticonRepository.findByChallengeAndName(challenge, "emoticon4");
        List<Emoticon> emoticon5List = emoticonRepository.findByChallengeAndName(challenge, "emoticon5");

        return new EmoticonResponseDto(emoticon1List.size(), emoticon2List.size(), emoticon3List.size(), emoticon4List.size(), emoticon5List.size());
    }
}
