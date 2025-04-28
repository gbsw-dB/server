package com.audion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocketMsgDto {
    String message;

    @Override
    public String toString(){
        return message;
    }
}
