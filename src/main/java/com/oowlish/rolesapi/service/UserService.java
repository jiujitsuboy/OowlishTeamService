package com.oowlish.rolesapi.service;

import net.minidev.json.JSONObject;

/**
 * Player available operations
 */
public interface UserService {

  /**
   * Return the player identified by the Id
   * @param userId user's id
   * @return @link{PlayerEntity}
   */
  JSONObject getUser(String userId);

  /**
   * Return all the players in the system.
   * @return @link{PlayerEntity}
   */
  JSONObject[] getUsers();

}
