package com.isep.appli.models;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class FormattedDiscussion {
    private Long id;
    private String conversationType;
    private String displayDestination;
    private Long destination;
    private String image;
    private String lastMessageDate;
    private boolean canDelete;
}
