package com.example.todoApi.record.task;


import jakarta.validation.Valid;
import lombok.*;

import javax.validation.constraints.NotNull;


public record TaskRecord(Long id,
                         String title) {


}

