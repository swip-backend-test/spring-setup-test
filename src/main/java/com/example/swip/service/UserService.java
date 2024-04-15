package com.example.swip.service;


import com.example.swip.dto.AddUserRequest;
import com.example.swip.dto.BoardSaveRequest;
import com.example.swip.entity.Board;
import com.example.swip.entity.User;
import com.example.swip.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private static final String EXISTING_EMAIL = "test@test.com";
    private static final String ANOTHER_EMAIL = "next@test.com";

    public Optional<User> findByEmail(String email) {
        // TODO : DB에 구현
        if (EXISTING_EMAIL.equalsIgnoreCase(email)) {
            var user = new User();
            user.setId(1L);
            user.setEmail(EXISTING_EMAIL);
            user.setPassword("$2a$12$kivpiZrLUW9.44c4P4KpgOrgvH.Y6UkWNP9/nxV5sZW2K5ztE78e6"); // pw: 'test' 로 생성된 암호문
            user.setRole("ROLE_ADMIN");
            user.setExtraInfo("My nice admin");
            return Optional.of(user);
        } else if (ANOTHER_EMAIL.equalsIgnoreCase(email)) {
            var user = new User();
            user.setId(99L);
            user.setEmail(ANOTHER_EMAIL);
            user.setPassword("$2a$12$kivpiZrLUW9.44c4P4KpgOrgvH.Y6UkWNP9/nxV5sZW2K5ztE78e6"); // pw: 'test' 로 생성된 암호문
            user.setRole("ROLE_USER");
            user.setExtraInfo("My nice user");
            return Optional.of(user);
        }

        return Optional.empty();
    }

    public boolean isDuplicatedUserName(String username) {
        var user = findByEmail(username);
        System.out.println(username);
        if(user.isEmpty())
            return false;
        else
            return true;
    }

    //조회
    public User findUserById(Long writerId) {
        User findUser = userRepository.findById(writerId).orElse(null);
        return findUser;
    }

    // 저장
    @Transactional
    public Long saveUser(AddUserRequest addUserRequest){
        User savedUser = userRepository.save(addUserRequest.toEntity());
        return savedUser.getId();
    }
}