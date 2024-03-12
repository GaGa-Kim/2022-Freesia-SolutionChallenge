package com.freesia.imyourfreesia.service.user;

import com.freesia.imyourfreesia.domain.user.User;
import com.freesia.imyourfreesia.dto.user.GoalMsgUpdateRequestDto;
import com.freesia.imyourfreesia.dto.user.UserPasswordUpdateRequestDto;
import com.freesia.imyourfreesia.dto.user.UserResponseDto;
import com.freesia.imyourfreesia.dto.user.UserUpdateRequestDto;
import java.io.IOException;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional(readOnly = true)
public interface UserService {
    /**
     * 회원 이메일로 회원을 조회한다.
     *
     * @param email (회원 이메일)
     * @return User (회원)
     */
    User findUserByEmail(String email);

    /**
     * 회원 이메일로 회원을 상세 조회한다.
     *
     * @param email (회원 이메일)
     * @return UserResponseDto (회원 정보를 담은 DTO)
     */
    UserResponseDto findUserDetailsByEmail(String email);

    /**
     * 회원 아이디로 회원을 조회한다.
     *
     * @param userId (회원 아이디)
     * @return User (회원)
     */
    User findUserById(Long userId);

    /**
     * 회원 전체 목록을 조회한다.
     *
     * @return List<User> (회원 전체 목록)
     */
    List<User> findUserList();

    /**
     * 회원 프로필을 수정한다.
     *
     * @param email            (회원 이메일)
     * @param updateRequestDto (회원 수정 정보를 담은 DTO)
     * @param msgRequestDto    (회원 목표 정보를 담은 DTO)
     * @param file             (회원 프로필 이미지)
     * @return UserResponseDto (회원 정보를 담은 DTO)
     */
    @Transactional
    UserResponseDto update(String email, UserUpdateRequestDto updateRequestDto, GoalMsgUpdateRequestDto msgRequestDto, MultipartFile file) throws Exception;

    /**
     * 회원 비밀번호를 수정한다.
     *
     * @param email      (회원 이메일)
     * @param requestDto (회원 비밀번호 정보를 담은 DTO)
     * @return UserResponseDto (회원 정보를 담은 DTO)
     */
    @Transactional
    UserResponseDto updatePw(String email, UserPasswordUpdateRequestDto requestDto);

    /**
     * 회원 프로필 이미지를 ByteArray 형식으로 조회한다.
     *
     * @param email (회원 이메일)
     * @return String (프로필 이미지의 ByteArray 형식)
     */
    String getUserProfileByteArray(String email) throws IOException;
}
