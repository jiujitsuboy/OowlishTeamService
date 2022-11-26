package com.oowlish.rolesapi.service;

import net.minidev.json.JSONObject;

/**
 * Player available operations
 */
public interface UserService {

  /**
   * Return the user identified by the Id
   * @param userId user's id
   * @return @link{PlayerEntity}
   */
  JSONObject getUser(String userId);

  /**
   * Return all the users in the api.
   * @return @link{PlayerEntity}
   */
  JSONObject[] getUsers();

}
