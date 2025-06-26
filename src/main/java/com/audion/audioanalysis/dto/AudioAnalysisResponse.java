package com.audion.audioanalysis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AudioAnalysisResponse {

//    @JsonProperty("audio_type")
//    private String audioType;
//
//    @JsonProperty("danger_level")
//    private int dangerLevel;
//
//    @Override
//    public String toString() {
//        return "AudioAnalysisResponse{" +
//                "audioType='" + audioType + '\'' +
//                ", dangerLevel=" + dangerLevel +
//                '}';
//    }

    private Double probability;
    private String result;
}
