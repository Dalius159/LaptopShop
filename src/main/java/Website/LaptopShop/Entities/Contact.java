package Website.LaptopShop.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;

import java.util.Date;
@Entity()
@Data
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String contactEmail;

    @DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", timezone="GMT+7")
    private Date contactDate;

    @DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", timezone="GMT+7")
    private Date respondDate;

    private String contactMessage;
    private String respondMessage;
    private String status;

    @ManyToOne
    @JoinColumn(name = "respondent_id")
    private Users respondent;
}
