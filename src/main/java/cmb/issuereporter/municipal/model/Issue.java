package cmb.issuereporter.municipal.model;

import cmb.issuereporter.municipal.converter.StringArrayConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "issue")
public class Issue {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    private String description;

    @Column(name = "image_url")
    @Convert(converter = StringArrayConverter.class)
    private String[] imageUrl;

    private String status;

    private Double lat;

    private Double lon;

    @Column(name = "created_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Colombo")
    private Date createdDate;

    @Column(name = "updated_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Colombo")
    private Date updatedDate;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne
    @JoinColumn(name = "area_id")
    private Area area;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @OneToOne
    @JoinColumn(name = "assignby_id")
    private User assignBy;

}
