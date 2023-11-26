package com.pavila.TaskProyect.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest implements Serializable {

    @NotBlank
    @Size(min = 3, max = 50, message = " Debe tener entre {min} y {max} caracteres")
    private String title;
    @NotBlank
    @Size(min = 3, max = 50, message = " Debe tener entre {min} y {max} caracteres")
    private String description;
    @NotBlank
    @Size(min = 3, max = 50, message = " Debe tener entre {min} y {max} caracteres")
    private String finishedDate;
    @NotBlank
    @Size(min = 3, max = 50, message = " Debe tener entre {min} y {max} caracteres")
    private String priority;
    @NotBlank
    @Size(min = 3, max = 50, message = " Debe tener entre {min} y {max} caracteres")
    private String assignee;
    //private String createdByUser;
    private String category;

}
