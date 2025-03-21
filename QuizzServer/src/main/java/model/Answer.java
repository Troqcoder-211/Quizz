package model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Answer implements Serializable {
    private static final long serialVersionUID = 1L;
    private int answerId;
    private int questionId;
    private String content;
    private boolean isCorrect;
}
