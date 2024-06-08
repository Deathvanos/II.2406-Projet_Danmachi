package com.isep.appli.models;
import com.isep.appli.dbModels.Familia;
import com.isep.appli.dbModels.Message;
import com.isep.appli.dbModels.Personnage;
import com.isep.appli.dbModels.User;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
public class FormattedReport {
    private Long id;
    private User userFrom;
    private String objectReportedType;
    private Personnage personnageReported;
    private Familia familiaReported;
    private Personnage leaderFamiliaReported;
    private Personnage senderMessageReported;
    private String date;
    private String comment;
}