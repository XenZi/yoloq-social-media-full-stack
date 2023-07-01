package com.example.yoloq.models.dto.requests;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFriendRequestDTO {
    private Integer requestID;
    private boolean decision;
}
