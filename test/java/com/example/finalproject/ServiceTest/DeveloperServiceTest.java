package com.example.finalproject.ServiceTest;

import com.example.finalproject.Api.ApiException;
import com.example.finalproject.DTO.DeveloperIDTO;
import com.example.finalproject.DTO.DeveloperODTO;
import com.example.finalproject.Model.Developer;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Model.Request;
import com.example.finalproject.Repository.AuthRepository;
import com.example.finalproject.Repository.DeveloperRepository;
import com.example.finalproject.Repository.RequestRepository;
import com.example.finalproject.Service.DeveloperService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class DeveloperServiceTest {

    @Mock
    private AuthRepository authRepository;

    @Mock
    private DeveloperRepository developerRepository;

    @Mock
    private RequestRepository requestRepository;

    @InjectMocks
    private DeveloperService developerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMyDeveloper_Success() {
        MyUser mockUser = new MyUser(1, "user1", "password", "User One", "user1@example.com", "0557640001", "DEVELOPER", false, null, null, null);
        Developer mockDeveloper = new Developer(1, "Developer Bio", true, mockUser, null, null, null, null);
        mockUser.setDeveloper(mockDeveloper);

        when(authRepository.findMyUserById(1)).thenReturn(mockUser);

        DeveloperODTO result = developerService.getMyDeveloper(1);

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("user1");
        assertThat(result.getBio()).isEqualTo("Developer Bio");

        verify(authRepository, times(1)).findMyUserById(1);
    }

    @Test
    void testRegister_Success() {
        DeveloperIDTO developerIDTO = new DeveloperIDTO("user1", "password", "User One", "user1@example.com", "0557640001", "url","Developer Bio");

        MyUser myUser = new MyUser(null, developerIDTO.getUsername(), "encodedPassword", developerIDTO.getName(), developerIDTO.getEmail(), developerIDTO.getPhoneNumber(), "DEVELOPER", false, null, null, null);

        Developer developer = new Developer();
        developer.setMyUser(myUser);
        developer.setBio(developerIDTO.getBio());

        Request request = new Request(null, developer.getId(), 1, "VALIDATION", LocalDateTime.now(), "PENDING", developer, null, null, null, null);


        when(authRepository.save(any(MyUser.class))).thenReturn(myUser);
        when(developerRepository.save(any(Developer.class))).thenReturn(developer);
        when(requestRepository.save(any(Request.class))).thenReturn(request);

        developerService.register(developerIDTO);

        verify(authRepository, times(1)).save(any(MyUser.class));
        verify(developerRepository, times(1)).save(any(Developer.class));
        verify(requestRepository, times(1)).save(any(Request.class));
    }


    @Test
    void testGetMyDeveloper_UserNotFound() {
        when(authRepository.findMyUserById(99)).thenReturn(null);

        assertThatThrownBy(() -> developerService.getMyDeveloper(99))
                .isInstanceOf(ApiException.class)
                .hasMessage("user not found");

        verify(authRepository, times(1)).findMyUserById(99);
    }
}
