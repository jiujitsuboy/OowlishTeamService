package com.oowlish.rolesapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import com.oowlish.rolesapi.entity.RoleEnum;
import com.oowlish.rolesapi.entity.UserEntity;
import com.oowlish.rolesapi.entity.UserTokenEntity;
import com.oowlish.rolesapi.exception.GenericAlreadyExistsException;
import com.oowlish.rolesapi.exception.InvalidRefreshTokenException;
import com.oowlish.rolesapi.model.RefreshToken;
import com.oowlish.rolesapi.model.SignedInUser;
import com.oowlish.rolesapi.model.SystemUser;
import com.oowlish.rolesapi.repository.UserRepository;
import com.oowlish.rolesapi.repository.UserTokenRepository;
import com.oowlish.rolesapi.security.JwtManager;
import com.oowlish.rolesapi.service.impl.AuthServiceImpl;
import com.oowlish.rolesapi.service.impl.TeamServiceImpl;
import com.oowlish.rolesapi.TestConstants;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

  @Mock
  private TeamServiceImpl teamService;
  @Mock
  private UserRepository userRepository;
  @Mock
  private UserTokenRepository userTokenRepository;
  @Mock
  private PasswordEncoder bCryptPasswordEncoder;
  @Mock
  private JwtManager tokenManager;
  @InjectMocks
  private AuthServiceImpl classUnderTest;

  @Test
  public void signUpRoleUser() {
    final int ZERO_RECORD = 0;

    UUID userUUID = UUID.randomUUID();
    SystemUser user = TestConstants.getTestUser(userUUID,TestConstants.USER_NAME_A, TestConstants.USER_PASSWORD_A, TestConstants.USER_FIRST_NAME_A, TestConstants.USER_LAST_NAME_A, TestConstants.USER_EMAIL_A);
    user.setRole(null);
//    doNothing().when(teamService).createTeamForUser(any(UserEntity.class));
    when(userRepository.countByUsernameOrEmail(anyString(), anyString())).thenReturn(ZERO_RECORD);
    when(bCryptPasswordEncoder.encode(anyString())).thenReturn(TestConstants.CYPHERED_PASSWORD);
    when(userRepository.save(any(UserEntity.class))).thenReturn(null);

    UserEntity returnedUserEntity = classUnderTest.signUp(user);

    assertNotNull(returnedUserEntity);
    assertEquals(returnedUserEntity.getId(), userUUID);
    assertEquals(returnedUserEntity.getPassword(), TestConstants.CYPHERED_PASSWORD);
    assertEquals(returnedUserEntity.getUsername(), TestConstants.USER_NAME_A);
  }

  @Test
  public void signUpRoleAdmin() {
    final int ZERO_RECORD = 0;

    UUID userUUID = UUID.randomUUID();
    SystemUser user = TestConstants.getTestUser(userUUID,TestConstants.USER_NAME_A, TestConstants.USER_PASSWORD_A, TestConstants.USER_FIRST_NAME_A, TestConstants.USER_LAST_NAME_A, TestConstants.USER_EMAIL_A);
    user.setRole(RoleEnum.ADMIN);
//    doNothing().when(teamService).createTeamForUser(any(UserEntity.class));
    when(userRepository.countByUsernameOrEmail(anyString(), anyString())).thenReturn(ZERO_RECORD);
    when(bCryptPasswordEncoder.encode(anyString())).thenReturn(TestConstants.CYPHERED_PASSWORD);
    when(userRepository.save(any(UserEntity.class))).thenReturn(null);

    UserEntity returnedUserEntity = classUnderTest.signUp(user);

    assertNotNull(returnedUserEntity);
    assertEquals(returnedUserEntity.getId(), userUUID);
    assertEquals(returnedUserEntity.getPassword(), TestConstants.CYPHERED_PASSWORD);
    assertEquals(returnedUserEntity.getUsername(), TestConstants.USER_NAME_A);
  }

  @Test
  public void signUpGenericAlreadyExistsException() {
    final int ONE_RECORD = 1;

    SystemUser user = TestConstants.getTestUser(UUID.randomUUID(),TestConstants.USER_NAME_A, TestConstants.USER_PASSWORD_A, TestConstants.USER_FIRST_NAME_A, TestConstants.USER_LAST_NAME_A, TestConstants.USER_EMAIL_A);

    when(userRepository.countByUsernameOrEmail(anyString(), anyString())).thenReturn(ONE_RECORD);

    assertThrows(GenericAlreadyExistsException.class, () -> classUnderTest.signUp(user));

  }

  @Test
  public void signUser() {

    final boolean passwordsMatch = true;
    final String accessToken = TestConstants.ACCESS_TOKEN;

    UserEntity userEntity = TestConstants.getTestUserEntity(UUID.randomUUID(),TestConstants.USER_NAME_A, TestConstants.USER_PASSWORD_A, TestConstants.USER_FIRST_NAME_A, TestConstants.USER_LAST_NAME_A, TestConstants.USER_EMAIL_A);

    when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(userEntity));
    when(bCryptPasswordEncoder.matches(anyString(), anyString())).thenReturn(passwordsMatch);
    when(userTokenRepository.deleteByUserId(any(UUID.class))).thenReturn(null);
    when(tokenManager.create(any(org.springframework.security.core.userdetails.User.class))).thenReturn(accessToken);
    when(userTokenRepository.save(any(UserTokenEntity.class))).thenReturn(null);

    SignedInUser returnedSignedInUser = classUnderTest.signUser(userEntity.getUsername(), userEntity.getPassword());

    assertNotNull(returnedSignedInUser);
    assertNotNull(returnedSignedInUser.getRefreshToken());
    assertEquals(returnedSignedInUser.getUserName(), userEntity.getUsername());
    assertEquals(returnedSignedInUser.getAccessToken(), accessToken);

  }

  @Test
  public void signUserUsernameNotFoundException() {

    String userName = null;
    String password = TestConstants.USER_PASSWORD_A;

    assertThrows(UsernameNotFoundException.class, () -> classUnderTest.signUser(userName, password));
  }

  @Test
  public void signUserUserPasswordNotFoundException() {
    String userName = TestConstants.USER_NAME_A;
    String password = null;

    assertThrows(UsernameNotFoundException.class, () -> classUnderTest.signUser(userName, password));
  }

  @Test
  public void signUserNotExistsInsufficientAuthenticationException() {

    String userName = TestConstants.USER_NAME_A;
    String password = TestConstants.USER_PASSWORD_A;

    when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

    assertThrows(InsufficientAuthenticationException.class, () -> classUnderTest.signUser(userName, password));

  }

  @Test
  public void signUserPasswordDontMatchInsufficientAuthenticationException() {

    String userName = TestConstants.USER_NAME_A;
    String password = TestConstants.USER_PASSWORD_A;

    final boolean passwordsMatch = false;
    final String accessToken = TestConstants.ACCESS_TOKEN;

    UserEntity userEntity = TestConstants.getTestUserEntity(UUID.randomUUID(),TestConstants.USER_NAME_A, TestConstants.USER_PASSWORD_A, TestConstants.USER_FIRST_NAME_A, TestConstants.USER_LAST_NAME_A, TestConstants.USER_EMAIL_A);

    when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(userEntity));
    when(bCryptPasswordEncoder.matches(anyString(), anyString())).thenReturn(passwordsMatch);

    assertThrows(InsufficientAuthenticationException.class, () -> classUnderTest.signUser(userName, password));

  }

  @Test
  public void getAccessToken() {

    RefreshToken refreshTokenModel = new RefreshToken(TestConstants.REFRESH_TOKEN);

    UserEntity userEntity = TestConstants.getTestUserEntity(UUID.randomUUID(),TestConstants.USER_NAME_A, TestConstants.USER_PASSWORD_A, TestConstants.USER_FIRST_NAME_A, TestConstants.USER_LAST_NAME_A, TestConstants.USER_EMAIL_A);

    UserTokenEntity userTokenEntity = new UserTokenEntity();
    userTokenEntity.setId(UUID.randomUUID());
    userTokenEntity.setUser(userEntity);
    userTokenEntity.setRefreshToken(TestConstants.REFRESH_TOKEN);

    when(userTokenRepository.findByRefreshToken(anyString())).thenReturn(Optional.of(userTokenEntity));
    when(tokenManager.create(any(org.springframework.security.core.userdetails.User.class))).thenReturn(TestConstants.ACCESS_TOKEN);
    Optional<SignedInUser> returnedOptionalSignedInUser = classUnderTest.getAccessToken(refreshTokenModel);

    assertTrue(returnedOptionalSignedInUser.isPresent());
    assertEquals(returnedOptionalSignedInUser.get().getUserName(), TestConstants.USER_NAME_A);
    assertEquals(returnedOptionalSignedInUser.get().getAccessToken(), TestConstants.ACCESS_TOKEN);
    assertEquals(returnedOptionalSignedInUser.get().getRefreshToken(), TestConstants.REFRESH_TOKEN);
    assertNotNull(userTokenEntity.getId());
    assertEquals(returnedOptionalSignedInUser.get().getUserName(),userTokenEntity.getUser().getUsername());
    assertEquals(returnedOptionalSignedInUser.get().getRefreshToken(),userTokenEntity.getRefreshToken());
  }

  @Test
  public void getAccessTokenInvalidRefreshTokenException() {

    final String refreshToken = "26rvap1cr3maf85akd9jb27c7dlrlkfn82hn3rahrb9qhr0rcmtl82jjt75tapoqtqktkh6rgdicb7mm1i38qqhdpgb7v8q3omoffu7dj1o8is3h763o54l978tfp95v";

    RefreshToken refreshTokenModel = new RefreshToken(refreshToken);

    when(userTokenRepository.findByRefreshToken(anyString())).thenReturn(Optional.empty());

    assertThrows(InvalidRefreshTokenException.class, () -> classUnderTest.getAccessToken(refreshTokenModel));
  }

  @Test
  public void removeRefreshToken() {

    RefreshToken refreshTokenModel = new RefreshToken(TestConstants.REFRESH_TOKEN);

    UserEntity userEntity = TestConstants.getTestUserEntity(UUID.randomUUID(),TestConstants.USER_NAME_A, TestConstants.USER_PASSWORD_A, TestConstants.USER_FIRST_NAME_A, TestConstants.USER_LAST_NAME_A, TestConstants.USER_EMAIL_A);

    UserTokenEntity userTokenEntity = new UserTokenEntity();
    userTokenEntity.setId(UUID.randomUUID());
    userTokenEntity.setUser(userEntity);
    userTokenEntity.setRefreshToken(TestConstants.REFRESH_TOKEN);

    when(userTokenRepository.findByRefreshToken(anyString())).thenReturn(Optional.of(userTokenEntity));
    doNothing().when(userTokenRepository).delete(any(UserTokenEntity.class));

    classUnderTest.removeRefreshToken(refreshTokenModel);

    verify(userTokenRepository, times(1)).delete(any(UserTokenEntity.class));

  }

  @Test
  public void removeRefreshTokenInvalidRefreshTokenException() {

    RefreshToken refreshTokenModel = new RefreshToken(TestConstants.REFRESH_TOKEN);

    when(userTokenRepository.findByRefreshToken(anyString())).thenReturn(Optional.empty());
    assertThrows(InvalidRefreshTokenException.class, () -> classUnderTest.removeRefreshToken(refreshTokenModel));
  }
}