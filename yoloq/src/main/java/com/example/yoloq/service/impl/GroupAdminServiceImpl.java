package com.example.yoloq.service.impl;

import com.example.yoloq.models.Group;
import com.example.yoloq.models.GroupAdmin;
import com.example.yoloq.models.User;
import com.example.yoloq.models.dto.GroupAdminDTO;
import com.example.yoloq.models.dto.UserDTO;
import com.example.yoloq.repository.GroupAdminRepository;
import com.example.yoloq.service.GroupAdminService;
import com.example.yoloq.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class GroupAdminServiceImpl implements GroupAdminService {

    private final GroupAdminRepository groupAdminRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public GroupAdminServiceImpl(GroupAdminRepository groupAdminRepository, UserService userService, ModelMapper modelMapper) {
        this.groupAdminRepository = groupAdminRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public GroupAdmin createEntity(Group group) {
        GroupAdmin groupAdmin = new GroupAdmin();
        groupAdmin.setAdminAt(group);
        groupAdmin.setUser(this.userService.findLoggedUser());
        groupAdmin.setDeleted(false);
        groupAdmin = groupAdminRepository.save(groupAdmin);
        return groupAdmin;
    }

    @Override
    public GroupAdmin createEntity(Group group, User user) {
        GroupAdmin groupAdmin = new GroupAdmin();
        groupAdmin.setAdminAt(group);
        groupAdmin.setUser(user);
        groupAdmin.setDeleted(false);
        groupAdmin = groupAdminRepository.save(groupAdmin);
        return groupAdmin;
    }

    @Override
    public GroupAdmin delete(int id) {
        GroupAdmin groupAdmin = this.groupAdminRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        groupAdmin.setDeleted(true);
        this.groupAdminRepository.save(groupAdmin);
        return groupAdmin;
    }


    @Override
    public Set<GroupAdminDTO> findAllByGroupID(Integer id) {
        return groupAdminRepository.findAllByGroupID(id)
                .stream()
                .map(groupAdmin -> {
                    GroupAdminDTO dto = new GroupAdminDTO();
                    dto.setId(groupAdmin.getId());
                    dto.setUser(modelMapper.map(groupAdmin.getUser(), UserDTO.class));
                    dto.setGroupID(groupAdmin.getAdminAt().getId());
                    return dto;
                })
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isLoggedUserAdminInGroup(int groupID) {
        User user = this.userService.findLoggedUser();
        Optional<GroupAdmin> foundAdmin = this.groupAdminRepository.findByUserIDAndGroupID(groupID, user.getId());
        return foundAdmin.isPresent();
    }


}
