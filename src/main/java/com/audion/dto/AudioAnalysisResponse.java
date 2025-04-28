package com.audion.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AudioAnalysisResponse {

    @JsonProperty("audio_type")
    private String audioType;

    @JsonProperty("danger_level")
    private int dangerLevel;

    @Override
    public String toString() {
        return "AudioAnalysisResponse{" +
                "audioType='" + audioType + '\'' +
                ", dangerLevel=" + dangerLevel +
                '}';
    }
}
