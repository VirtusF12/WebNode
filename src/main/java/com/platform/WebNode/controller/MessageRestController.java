package com.platform.WebNode.controller;

import com.platform.WebNode.dto.MessageDTO;
import com.platform.WebNode.service.MessageService;
import com.platform.WebNode.service.UserAndGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class MessageRestController {

    @Autowired
    MessageService messageService;
    @Autowired
    UserAndGroupService userAndGroupService;

    @MessageMapping("/chat/{to}")
    public void sendMessagePersonal(@DestinationVariable String to,
                                    MessageDTO message) {

        messageService.sendMessage(to, message);
    }

    @GetMapping("/auth/listmessage/{from}/{to}")
    public List<Map<String,Object>> getListMessageChat(@PathVariable("from") Integer from,
                                                       @PathVariable("to") Integer to) {

        return messageService.getListMessage(from, to);
    }

    @GetMapping("/auth/fetchAllUsers/{myId}")
    public List<Map<String,Object>> fetchAll(@PathVariable("myId") String myId) {

        return userAndGroupService.fetchAll(myId);
    }


    /* group */
    /*
    @GetMapping("/fetchAllGroups/{groupid}")
    public List<Map<String,Object>> fetchAllGroup(@PathVariable("groupid") String groupId) {
        return  userAndGroupService.fetchAllGroup(groupId);
    }

    @GetMapping("/listmessage/group/{groupid}")
    public List<Map<String,Object>> getListMessageGroupChat(@PathVariable("groupid") Integer groupid){
        return messageService.getListMessageGroups(groupid);
    }

    @MessageMapping("/chat/group/{to}")
    public void sendMessageToGroup(@DestinationVariable Integer to, MessageGroupDTO message) {
        messageService.sendMessageGroup(to, message);
    }*/
}
