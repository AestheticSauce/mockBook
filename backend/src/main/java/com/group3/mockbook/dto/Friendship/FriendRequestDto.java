package com.group3.mockbook.dto.Friendship;

import java.sql.Timestamp;

public class FriendRequestDto {
    private int targetId;

    public FriendRequestDto() {
    }

    public FriendRequestDto(Integer targetId) {
        this.targetId = targetId;
    }

    public int getTargetId() {
        return targetId;
    }
}