package com.driver;


import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WhatsappService {

    WhatsappRepository whatsappRepository=new WhatsappRepository();
    public String createUser(String name, String mobile) throws Exception {
        whatsappRepository.createUser(name,mobile);
        return "SUCCESS";
    }

    public Group createGroup(List<User> users) {
        Group group=whatsappRepository.createGroup(users);
        return group;
    }

    public int createMessage(String content) {
        int message=whatsappRepository.createMessage(content);
        return message;
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception {
        /*  Send a message by providing the message, sender, and group.
          If the mentioned group does not exist, the application will throw an exception.
          If the sender is not a member of the group, the application will throw an exception.
          If the message is sent successfully, the application will return the final number of messages in that group.
      */
        int messages= whatsappRepository.sendMessage(message,sender,group);
        return messages;
    }


    public String changeAdmin(User approver, User user, Group group) {
        return null;
    }

    public int removeUser(User user) {
        return 0;

    }

    public String findMessage(Date start, Date end, int k) {
        return null;
    }
}