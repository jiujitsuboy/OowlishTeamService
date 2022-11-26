package com.oowlish.rolesapi.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.oowlish.rolesapi.controller.AuthController;
import com.oowlish.rolesapi.controller.TeamController;
import com.oowlish.rolesapi.entity.UserEntity;
import com.oowlish.rolesapi.model.SignInReq;
import com.oowlish.rolesapi.model.SignedInUser;
import com.oowlish.rolesapi.model.User;
import com.oowlish.rolesapi.service.AuthService;
import com.oowlish.rolesapi.service.UserService;
import com.oowlish.rolesapi.service.Util;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class UserRepresentationModelAssembler extends
    RepresentationModelAssemblerSupport<UserEntity, User> {

  private AuthService authService;
  public UserRepresentationModelAssembler(AuthService authService) {
    super(AuthController.class, User.class);
    this.authService = authService;
  }

  @Override
  public User toModel(UserEntity entity) {
    User user = (User) Util.toModel(entity);
    SignInReq signInReq = new SignInReq(user.getUsername(),user.getPassword());
    user.setPassword("Ciphered...");
    user.add(linkTo(methodOn(AuthController.class).signIn(signInReq)).withRel("user-signin"));
    return user;
  }

  public SignedInUser toModel(SignedInUser model) {
    UserEntity userEntity =  authService.findUserByUserName(model.getUserName()).get();
    return model.add(linkTo(methodOn(TeamController.class).getTeam(userEntity.getId().toString())).withRel("user-team"));
  }


}
