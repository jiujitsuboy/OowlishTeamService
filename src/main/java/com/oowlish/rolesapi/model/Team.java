package com.oowlish.rolesapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Team  extends RepresentationModel<Team> {

  public Team(@JsonProperty(value = "teamMemberIds", required = true) List<String> teamMemberIds){
      this.members = teamMemberIds.stream().map(teamMemberId->Member.builder().id(teamMemberId).build()).collect(
          Collectors.toList());
  }
  private String id;
  private String name;
  private String teamLeadId;
  private List<Member> members;
}
