package com.oowlish.rolesapi.service;

import com.oowlish.rolesapi.model.Team;
import net.minidev.json.JSONObject;

/**
 * Team available operations
 */
public interface TeamService {

  /**
   * Return the team identified by the user Id
   * @param userId User id owner of this team
   * @return
   */
  Team getTeam(String userId);

  /**
   * Return all the Teams in the system.
   * @return
   */
  JSONObject[] getTeams();


}
