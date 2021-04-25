package kr.mikuwallets.djyurika400.song;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@Table(name = "playlist")
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class Song {

    @Id
    @Column
    private Long regNo;

    @Column(name = "id", length = 50)
    private String id;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "reviewed")
    private boolean reviewed;

    @Column(name = "playcount", length = 11)
    private int playCount;

    @Column(name = "pickcount", length = 11)
    private int pickCount;

    @Column(name = "createdat")
    private LocalDateTime createdAt;

    @Column(name = "lastplayedat")
    private LocalDateTime lastPlayedAt;

    @Column(name = "url")
    private String url;

    @Column
    @Enumerated(EnumType.ORDINAL)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private SongSource source;

    @Column
    private String guild;

}
