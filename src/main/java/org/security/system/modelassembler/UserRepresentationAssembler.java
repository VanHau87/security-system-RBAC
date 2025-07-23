package org.security.system.modelassembler;

import org.security.system.controller.UserController;
import org.security.system.dto.UserDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.afford;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.List;


@Component
public class UserRepresentationAssembler {
	public EntityModel<UserModel> toModel(UserDto dto) {
		UserModel model = new UserModel(dto);
        EntityModel<UserModel> resource = EntityModel.of(model,
                linkTo(methodOn(UserController.class).getUser(model.getId()))
                        .withSelfRel()
                        .andAffordance(afford(methodOn(UserController.class).createUser(dto)))
                        .andAffordance(afford(methodOn(UserController.class).updateUser(dto)))
                        .andAffordance(afford(methodOn(UserController.class).deleteSoft(model.getId())))
                        .andAffordance(afford(methodOn(UserController.class).deleteHard(model.getId())))
        );

        // Add link get all, create (với affordance cho _templates nếu muốn)
        /*
        resource.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("all-users"));
        resource.add(linkTo(methodOn(UserController.class).createUser(null))
                     .withRel("create")
                     .andAffordance(afford(methodOn(UserController.class).createUser(dto))));*/

        return resource;
    }
	// Map list UserModel -> CollectionModel<EntityModel<UserModel>>
    public CollectionModel<EntityModel<UserModel>> toCollectionModel(List<UserDto> dtos) {
        List<EntityModel<UserModel>> resources = dtos.stream()
                .map(this::toModel)
                .toList();

        CollectionModel<EntityModel<UserModel>> collectionModel = CollectionModel.of(resources);
        collectionModel.add(linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
        collectionModel.add(linkTo(methodOn(UserController.class).createUser(null))
                       .withRel("create")
                       .andAffordance(afford(methodOn(UserController.class).createUser(null))));
        return collectionModel;
    }
}
