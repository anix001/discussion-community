package com.DiscussionCommunity.service.impl;

import com.DiscussionCommunity.domain.Otp;
import com.DiscussionCommunity.domain.User;
import com.DiscussionCommunity.domain.dto.PasswordRequest;
import com.DiscussionCommunity.domain.enumeration.OtpStatus;
import com.DiscussionCommunity.domain.enumeration.OtpType;
import com.DiscussionCommunity.domain.enumeration.UserStatus;
import com.DiscussionCommunity.exception.custom.NotAcceptableException;
import com.DiscussionCommunity.exception.custom.NotFoundException;
import com.DiscussionCommunity.exception.custom.UnauthorizedException;
import com.DiscussionCommunity.repository.OtpRepository;
import com.DiscussionCommunity.repository.UserRepository;
import com.DiscussionCommunity.service.AuthService;
import com.DiscussionCommunity.service.EmailService;
import com.DiscussionCommunity.utils.EmailValidator;
import com.DiscussionCommunity.utils.JwtUtil;
import com.DiscussionCommunity.utils.StringGenerator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class AuthServiceImpl implements AuthService {
    private final EmailValidator emailValidator;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final OtpRepository otpRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private  final StringGenerator stringGenerator;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(EmailValidator emailValidator, UserRepository userRepository, EmailService emailService, OtpRepository otpRepository, BCryptPasswordEncoder passwordEncoder, StringGenerator stringGenerator, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.emailValidator = emailValidator;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.otpRepository = otpRepository;
        this.passwordEncoder = passwordEncoder;
        this.stringGenerator = stringGenerator;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    private Date getOtpExpireTime(){
        Calendar date = Calendar.getInstance();
        long timeInSecs = date.getTimeInMillis();
        return new Date(timeInSecs + (15 * 60 * 1000));
    }

    @Override
    @Transactional
    public void store(User user) {
        if(!emailValidator.isValidEmailAddress(user.getEmail())){
            throw new NotAcceptableException("Email Address is not valid");
        }
        UUID userId = UUID.randomUUID();
        user.setId(userId);
        user.setUsername(stringGenerator.UsernameGenerator(user.getFirstName()));
        String newOtp = stringGenerator.RandomOtpGenerator();
        List<Otp> otpList = new ArrayList<>();
        otpList.add(new Otp(passwordEncoder.encode(newOtp), getOtpExpireTime(), OtpType.VERIFY_USER, OtpStatus.ACTIVE));
        user.setOtp(otpList);
        userRepository.save(user);
        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            emailService.verifyAccount(user.getEmail(), user.getUsername(), newOtp);
        }
    }

    @Override
    public String verifyOtp(String email, String otp) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("User not Found."));
        Otp lastOtp = otpRepository.fetchLastValueFromTable().orElseThrow(()->new NotFoundException("No Otp Found."));
        if(passwordEncoder.matches(otp, lastOtp.getOtp())){
            if(lastOtp.getOtpStatus() == OtpStatus.ACTIVE){
                user.setIsUserActive(UserStatus.ACTIVE);
                userRepository.save(user);
                return "verified";
            }else{
                return "expired";
            }
        }else{
            throw new NotAcceptableException("Otp does not match.");
        }
    }

    @Override
    @Transactional
    public void regenerateOtp(String email, String type) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("User not Found."));
        List<Otp> oldOtpList = otpRepository.fetchAllByUserId(user.getId());
        Date otpExpireTime = getOtpExpireTime();
        String newOtp = stringGenerator.RandomOtpGenerator();
        Otp accountVerifyOtp = new Otp(passwordEncoder.encode(newOtp), otpExpireTime,OtpType.VERIFY_USER, OtpStatus.ACTIVE);;
        oldOtpList.add(accountVerifyOtp);
        user.setOtp(oldOtpList);
        userRepository.save(user);
        if(Objects.equals(type, "verify")){
            emailService.verifyAccount(user.getEmail(),user.getUsername(),newOtp);
        } else if (Objects.equals(type, "password")) {
            emailService.forgotPassword(user.getEmail(),user.getUsername(),newOtp);
        }else {
            throw new NotFoundException("Regenerate Otp Type does not Found.");
        }
    }

    @Override
    @Transactional
    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("User not Found."));
        List<Otp> oldOtpList = otpRepository.fetchAllByUserId(user.getId());
        Date otpExpireTime = getOtpExpireTime();
        String newOtp = stringGenerator.RandomOtpGenerator();
        Otp accountVerifyOtp = new Otp(passwordEncoder.encode(newOtp), otpExpireTime,OtpType.FORGOT_PASSWORD, OtpStatus.ACTIVE);;
        oldOtpList.add(accountVerifyOtp);
        user.setOtp(oldOtpList);
        userRepository.save(user);
        emailService.forgotPassword(user.getEmail(),user.getUsername(),newOtp);
    }

    @Override
    public void setPassword(PasswordRequest passwordRequest) {
        User user = userRepository.findByEmail(passwordRequest.getEmail()).orElseThrow(()-> new NotFoundException("User not Found."));
        if(user.getIsUserActive() == UserStatus.ACTIVE) {
            user.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
            userRepository.save(user);
        }else{
            throw new NotAcceptableException("User is not verified");
        }
    }

    @Override
    public String authenticate(PasswordRequest passwordRequest) {
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(passwordRequest.getEmail(), passwordRequest.getPassword()));
            if(authentication.isAuthenticated()){
                return jwtUtil.generateToken(passwordRequest.getEmail());
            }
        }catch(Exception e) {
            throw new UnauthorizedException("Invalid access !!");
        }
        return "Unauthorized";

    }
}
