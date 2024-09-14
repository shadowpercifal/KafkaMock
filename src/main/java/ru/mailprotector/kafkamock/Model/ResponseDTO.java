package ru.mailprotector.kafkamock.Model;

import lombok.Data;

@Data
public class ResponseDTO {
    private String success;
    public ResponseDTO(Boolean Success){ success = Success?"true":"false";}
}
