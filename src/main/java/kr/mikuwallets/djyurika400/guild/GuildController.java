package kr.mikuwallets.djyurika400.guild;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({"api/v1/guild"})
public class GuildController {
    private final GuildService guildService;

    public GuildController(GuildService guildService) {
        this.guildService = guildService;
    }

    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public ResponseEntity<List<Guild>> getAllGuilds() {

        return new ResponseEntity<>(guildService.getGuilds(), HttpStatus.OK);
    }
}
