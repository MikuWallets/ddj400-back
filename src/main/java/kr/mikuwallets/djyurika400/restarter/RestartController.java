package kr.mikuwallets.djyurika400.restarter;

import kr.mikuwallets.djyurika400.exception.ShellCommandException;
import kr.mikuwallets.djyurika400.shared.SharedModule;
import org.apache.logging.log4j.util.StringBuilders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@RestController
@RequestMapping({"/api/v1/restart"})
public class RestartController {

    // it runs on ubuntu
//    boolean isWindows = System.getProperty("os.name")
//            .toLowerCase().startsWith("windows");

    @RequestMapping(value = {""}, method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> restart(HttpServletRequest request) {
        SharedModule.validateAccessToken(request);

        String out = runShellCommand();

        return new ResponseEntity<>(out, HttpStatus.OK);
    }

    private String runShellCommand() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
//        if (isWindows) {
//            builder.command("cmd.exe", "/c", "dir");
//        } else {
            processBuilder.command("sh", "-c", "service djyurika restart");
//        }
            processBuilder.directory(new File(System.getProperty("user.home")));
            Process process = processBuilder.start();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ( (line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(System.getProperty("line.separator"));
            }
            String result = stringBuilder.toString();

            int exitCode = process.waitFor();
            assert exitCode == 0;

            return result;
        }
        catch (IOException | InterruptedException e) {
            throw new ShellCommandException("Error occurred when run shell command");
        }

    }
}

