package com.freesia.imyourfreesia.service.emoticon;

import com.freesia.imyourfreesia.domain.challenge.Challenge;
import com.freesia.imyourfreesia.domain.emoticon.Emoticon;
import com.freesia.imyourfreesia.domain.emoticon.EmoticonRepository;
import com.freesia.imyourfreesia.domain.emoticon.EmoticonType;
import com.freesia.imyourfreesia.domain.user.User;
import com.freesia.imyourfreesia.dto.emoticon.EmoticonCountResponseDto;
import com.freesia.imyourfreesia.dto.emoticon.EmoticonRequestDto;
import com.freesia.imyourfreesia.service.challenge.ChallengeService;
import com.freesia.imyourfreesia.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmoticonServiceImpl implements EmoticonService {
    private final EmoticonRepository emoticonRepository;

    private final UserService userService;
    private final ChallengeService challengeService;

    @Override
    public EmoticonCountResponseDto saveEmotion(EmoticonRequestDto requestDto) {
        User user = userService.findUserByEmail(requestDto.getEmail());
        Challenge challenge = challengeService.findChallengeById(requestDto.getChallengeId());
        Emoticon emoticon = requestDto.toEntity();
        emoticon.setUser(user);
        emoticon.setChallenge(challenge);
        emoticonRepository.save(emoticon);
        return getEmoticonByChallengeAndUserAndName(challenge, user);
    }

    @Override
    public EmoticonCountResponseDto deleteEmotion(EmoticonRequestDto requestDto) {
        User user = userService.findUserByEmail(requestDto.getEmail());
        Challenge challenge = challengeService.findChallengeById(requestDto.getChallengeId());
        emoticonRepository.deleteByUserAndChallengeAndName(user, challenge, requestDto.getEmoticonName());
        return getEmoticonByChallengeAndUserAndName(challenge, user);
    }

    @Override
    public EmoticonCountResponseDto getEmoticonByChallengeAndUser(Long challengeId, String email) {
        Challenge challenge = challengeService.findChallengeById(challengeId);
        User user = userService.findUserByEmail(email);
        return getEmoticonByChallengeAndUserAndName(challenge, user);
    }

    @Override
    public EmoticonCountResponseDto countByChallenge(Long challengeId) {
        Challenge challenge = challengeService.findChallengeById(challengeId);
        return getEmoticonByChallengeAndName(challenge);
    }

    private EmoticonCountResponseDto getEmoticonByChallengeAndUserAndName(Challenge challenge, User user) {
        return new EmoticonCountResponseDto(
                countByChallengeAndUserAndName(challenge, user, EmoticonType.EMOTICON1),
                countByChallengeAndUserAndName(challenge, user, EmoticonType.EMOTICON2),
                countByChallengeAndUserAndName(challenge, user, EmoticonType.EMOTICON3),
                countByChallengeAndUserAndName(challenge, user, EmoticonType.EMOTICON4),
                countByChallengeAndUserAndName(challenge, user, EmoticonType.EMOTICON5));
    }

    private Long countByChallengeAndUserAndName(Challenge challenge, User user, EmoticonType emoticonType) {
        return emoticonRepository.countByChallengeAndUserAndName(challenge, user, emoticonType.getEmoticonName());
    }

    private EmoticonCountResponseDto getEmoticonByChallengeAndName(Challenge challenge) {
        return new EmoticonCountResponseDto(
                countByChallengeAndName(challenge, EmoticonType.EMOTICON1),
                countByChallengeAndName(challenge, EmoticonType.EMOTICON2),
                countByChallengeAndName(challenge, EmoticonType.EMOTICON3),
                countByChallengeAndName(challenge, EmoticonType.EMOTICON4),
                countByChallengeAndName(challenge, EmoticonType.EMOTICON5));
    }

    private Long countByChallengeAndName(Challenge challenge, EmoticonType emoticonType) {
        return emoticonRepository.countByChallengeAndName(challenge, emoticonType.getEmoticonName());
    }
}