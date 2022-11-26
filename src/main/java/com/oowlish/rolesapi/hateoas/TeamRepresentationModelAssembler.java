package com.oowlish.rolesapi.hateoas;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.oowlish.rolesapi.controller.TeamController;
import com.oowlish.rolesapi.controller.UserController;
import com.oowlish.rolesapi.model.Team;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class TeamRepresentationModelAssembler extends RepresentationModelAssemblerSupport<Team, Team> {

  public TeamRepresentationModelAssembler() {
    super(TeamController.class, Team.class);
  }

  @Override
  public Team toModel(Team team) {

    team.setMembers(team.getMembers().stream().map(member -> {
      member.add(linkTo(methodOn(UserController.class).getUser(member.getId())).withSelfRel());
      return member;
    }).collect(Collectors.toList()));

    return team;
  }

  public List<Team> toListModel(Iterable<Team> entities) {
    if (Objects.isNull(entities)) {
      return Collections.emptyList();
    }
    return StreamSupport.stream(entities.spliterator(), false).map(e -> toModel(e))
        .collect(toList());
  }

}
