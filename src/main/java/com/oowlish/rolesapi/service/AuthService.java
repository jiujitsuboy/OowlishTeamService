package com.oowlish.rolesapi.service;

import com.oowlish.rolesapi.entity.UserEntity;
import com.oowlish.rolesapi.model.RefreshToken;
import com.oowlish.rolesapi.model.SignedInUser;
import com.oowlish.rolesapi.model.SystemUser;
import java.util.Optional;

/**
 * Authentication available operations
 */
public interface AuthService {

  /**
   * Create a new user in the system
   * @param user user's data
   * @return @Link{UserEntity}
   */
  UserEntity signUp(SystemUser user);

  /**
   * Validate the credentials of an user to generate access tokens
   * @param username user's username
   * @param password user's password
   * @return @Link{SignedInUser}
   */
  SignedInUser signUser(String username, String password);

  /**
   * Generate a new pair of tokens's (accessToken and RefreshToken)
   * @param refreshToken
   * @return @Link{Optional} @Link{SignedInUser}
   */
  Optional<SignedInUser> getAccessToken(RefreshToken refreshToken);

  /**
   * Log out the user by delete the current refresh token from the system
   * @param refreshToken current user's refresh token
   */
  void removeRefreshToken(RefreshToken refreshToken);

  /**
   * Retrieve the user identified by username
   * @param username username to find
   * @return @Link{Optional} @Link{UserEntity}
   */
  Optional<UserEntity> findUserByUserName(String username);
}
