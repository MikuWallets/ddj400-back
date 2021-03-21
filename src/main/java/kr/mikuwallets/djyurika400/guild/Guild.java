package kr.mikuwallets.djyurika400.guild;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = "config")
@Entity
public class Guild {
    @Id
    private String server;
    private String name;
    private Integer volume;
    private String commandChannel;
    private String developerRole;
    private String moderatorRole;
}
