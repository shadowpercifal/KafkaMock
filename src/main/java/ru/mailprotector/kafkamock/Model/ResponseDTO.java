package ru.mailprotector.kafkamock.Model;

import lombok.Data;

@Data
public class ResponseDTO {
    private Boolean success;
    public ResponseDTO(Boolean Success){ success = Success;}
}
