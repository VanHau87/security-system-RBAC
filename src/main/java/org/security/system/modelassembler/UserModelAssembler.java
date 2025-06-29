package org.security.system.modelassembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.security.system.controller.UserController;
import org.security.system.dto.UserDto;
import org.springframework.hateoas.CollectionModel;

public class UserModelAssembler {

	public static Builder builder(UserDto dto) {
        return new Builder(dto);
    }

	public static class Builder {
		private final UserModel model;
		
		public Builder(UserDto dto) {
            this.model = new UserModel(dto);
        }
		public Builder withSelfLink() {
            model.add(linkTo(methodOn(UserController.class).getUser(model.id)).withSelfRel());
            return this;
        }

        public Builder withUpdateLink() {
            model.add(linkTo(methodOn(UserController.class).updateUser(model.id, null)).withRel("update"));
            return this;
        }

        public Builder withDeleteSoftLink() {
            model.add(linkTo(methodOn(UserController.class).deleteSoft(model.id)).withRel("delete_soft"));
            return this;
        }
        public Builder withDeleteHardLink() {
            model.add(linkTo(methodOn(UserController.class).deleteHard(model.id)).withRel("delete_hard"));
            return this;
        }

        public Builder withGetAllUsersLink() {
            model.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"));
            return this;
        }
        
        public UserModel build() {
            return model;
        }
	}
	public static CollectionModel<UserModel> buildCollection(List<UserDto> users) {
		List<UserModel> models = users.stream()
	            .map(user -> UserModelAssembler.builder(user)
	                .withSelfLink()
	                .withUpdateLink()
	                .withDeleteSoftLink()
	                .withDeleteHardLink()
	                .build()
	            )
	            .toList();
		CollectionModel<UserModel> collectionModel = CollectionModel.of(models);
		collectionModel.add(linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
		return collectionModel;
	}
	public static UserModel buildModel(UserDto user) {
		return UserModelAssembler.builder(user)
				.withSelfLink()
                .withUpdateLink()
                .withDeleteSoftLink()
                .withDeleteHardLink()
				.build();
	}
}
