package com.driver;

import java.util.*;

public class WhatsappRepository {
    int numberOfGroups=0;
    int numberOfMessages=0;
    Map<String,User> userMap=new HashMap<>();
    Map<Group, List<User>> groupMap=new HashMap<>();
    List<Message> messageList=new ArrayList<>();

    Map<User,List<Message>> userMessageList=new HashMap<>();

    Map<Group,List<Message>> groupMessagesList=new HashMap<>();
    public String createUser(String name, String mobile) throws Exception {
        if(userMap.containsKey(mobile)){
            throw new Exception("User already exists");
        }
        User user=new User(name,mobile);
        userMap.put(mobile,user);
        return "SUCCESS";
    }


    public Group createGroup(List<User> users) {
        if(users.size()==2){
            Group group=new Group(users.get(1).getName(),users.size());
            groupMap.put(group,users);
            return group;
        }else {
            Group group=new Group("Group "+ ++numberOfGroups,users.size());
            groupMap.put(group,users);
            return group;
        }
    }


    public int createMessage(String content) {
        Message message=new Message(++numberOfMessages,content);
        message.setTimestamp(new Date());
        messageList.add(message);
        return message.getId();
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception {
        if(!groupMap.containsKey(group)){
            throw new Exception("Group does not exist");
        }
        boolean present=false;
        for(User user:groupMap.get(group)){
            if(user.equals(sender)){
                present=true;
            }
        }

        if(!present){
            throw new Exception("You are not allowed to send message");
        }

        if(groupMessagesList.containsKey(group)){
            groupMessagesList.get(group).add(message);
        }else{
            List<Message> list=new ArrayList<>();
            list.add(message);
            groupMessagesList.put(group,list);
        }

        if (userMessageList.containsKey(sender)){
            userMessageList.get(sender).add(message);
        }else{
            List<Message> list=new ArrayList<>();
            list.add(message);
            userMessageList.put(sender,list);
        }

        return groupMessagesList.get(group).size();
    }


    public String changeAdmin(User approver, User user, Group group) throws Exception {
        if(!groupMap.containsKey(group)){
            throw new Exception("Group does not exist");
        }
        if(!groupMap.get(0).equals(approver)){
            throw new Exception("Approver does not have rights");
        }
        boolean isUser=false;
        for(User user1:groupMap.get(group)){
            if (user1.equals(user1)){
                isUser=true;
            }
        }

        if(!isUser){
            throw new Exception("User is not a participant");
        }
        List<User> users=groupMap.get(group);
        for( User user1: users){
            if(user1.equals(user)){
                Collections.swap(users,users.indexOf(user),users.indexOf(user1));
            }
        }

        return "SUCCESS";
    }

    public int removeUser(User user) throws Exception {
           boolean isGroupPerson=false;
        Group group1=null;
        for(Group group:groupMap.keySet()){
            for(User user1:groupMap.get(group)){
                if(user1.equals(user)){
                    isGroupPerson=true;
                    group1=group;
                }
            }
        }
        if(!isGroupPerson){
            throw new Exception("User not found");
        }

        if(groupMap.get(group1).get(0).equals(user)){
            throw new Exception("Cannot remove admin");
        }
//
//        deleting msgs from list of groupMessages
        for(Group group:groupMessagesList.keySet()){
            for(Message message:groupMessagesList.get(group)){
                if(userMessageList.get(user).contains(message)){
                    groupMessagesList.remove(message);
                }
            }
        }
//        deleting msgs from list of created msgs;
        for(Message message:messageList){
            if(userMessageList.get(user).contains(message)){
                messageList.remove(message);
            }
        }
        userMessageList.remove(user);
        groupMap.remove(user);
        return groupMap.get(group1).size()+messageList.size()+groupMessagesList.get(group1).size();
    }


}