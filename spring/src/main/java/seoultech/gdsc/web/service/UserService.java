package seoultech.gdsc.web.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import seoultech.gdsc.web.dto.UserDto;
import seoultech.gdsc.web.entity.User;
import seoultech.gdsc.web.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;


    public Optional<UserDto.Response> getUser(int id){
        Optional<User> findUser = userRepository.findById(id);
        if(findUser.isPresent()){
            UserDto.Response user = modelMapper.map(findUser.get(), UserDto.Response.class);
            return Optional.of(user);
        }else{
            return Optional.empty();
        }
    }

    public String updateNickname(int id, UserDto.UpdateRequest body ){
        if(userRepository.existsUserByNickname(body.getNickname())){
            return "닉네임이 중복되었습니다.";
        }
        Optional<User> findUser = userRepository.findById(id);
        if(findUser.isPresent()){
            findUser.get().setNickname(body.getNickname());
            userRepository.save(findUser.get());
            return "성공";
        }else{
            return "유저가 없습니다.";
        }
    }

    public Boolean deleteUser(int id){
        Optional<User> findUser = userRepository.findById(id);
        if(findUser.isPresent()){
            userRepository.delete(findUser.get());
            return true;
        }else{
            return false;
        }
    }


    public String signUp(UserDto.SignUpRequest user){
        if(userRepository.existsUserByUserId(user.getUserId())){
            return "아이디 중복";
        }
        if(userRepository.existsUserByEmail(user.getUserId())){
            return "이메일 중복";
        }
        if(userRepository.existsUserByNickname(user.getNickname())){
            return "닉네임 중복";
        }
        if(userRepository.existsUserByHp(user.getHp())){
            return "hp";
        }

        User newUser = modelMapper.map(user, User.class);
        String encodePassword = passwordEncoder.encode(user.getPassword());

        newUser.setPassword(encodePassword);
        userRepository.save(newUser);
        return "ok";
    }

    public Optional<User> login(UserDto.LoginRequest user){
        Optional<User> findUser = userRepository.findByUserId(user.getUserId());

        if(!findUser.isPresent()){
            return Optional.empty();
        }
        Boolean result = passwordEncoder.matches(user.getPassword(), findUser.get().getPassword());

        return result == true ? Optional.of(findUser.get()) : Optional.empty();
    }


}
