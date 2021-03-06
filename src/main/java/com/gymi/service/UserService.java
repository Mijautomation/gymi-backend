package com.gymi.service;

import com.gymi.model.*;
import com.gymi.repository.FriendRepository;
import com.gymi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthService authService;

    @Autowired
    FriendRepository friendRepository;

    public User getUserById(long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            return user.get();
        }
        else {
            return null;
        }
    }

    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }

    public Friend saveFriend(long id1, long id2)
    {
        Friend friend = new Friend();

        if(friendRepository.findByUserId1AndUserId2(id1, id2) == null
                && friendRepository.findByUserId1AndUserId2(id2, id1) == null) {
            friend.setUserId1(id1);
            friend.setUserId2(id2);
            friend.setHasAccepted(true);

            try {
                return friendRepository.save(friend);
            } catch (Exception e) {
                throw e;
            }
        } else {
            return null;
        }
    }

    public void deleteFriendship(Long id1, Long id2)
    {
        Friend friendShip = friendRepository.findFriendshipByUserIds(id1, id2);

        friendRepository.delete(friendShip);

    }

    public List<FriendResponse> getFriendsForUser(long userId) {
        List<Friend> friendList = friendRepository.findByUserId1OrUserId2(userId, userId);
        List<FriendResponse> friendResponseList = new ArrayList<>();

        for(Friend friend: friendList) {
            if(friend.getHasAccepted()) {
                FriendResponse friendResponse = new FriendResponse();
                if(friend.getUserId1() == userId) {
                    friendResponse.setUser(getUserById(friend.getUserId2()));
                }
                else {
                    friendResponse.setUser(getUserById(friend.getUserId1()));
                }
                friendResponse.setFriendsSince(friend.getCreatedDate());
                friendResponseList.add(friendResponse);
            }
        }
        return friendResponseList;
    }
}
